package br.com.anthonini.feira.mail;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.anthonini.feira.config.MailConfig;
import br.com.anthonini.feira.dto.ListaCompras;
import br.com.anthonini.feira.model.Feira;
import br.com.anthonini.feira.model.ItemFeira;
import br.com.anthonini.feira.model.Produto;
import br.com.anthonini.feira.storage.FotoStorage;

@Component
public class Mailer {
	
	private static Logger logger = LoggerFactory.getLogger(Mailer.class);

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@Autowired
	private MailConfig mailConfig;
	
	public void send(Feira feira) {
		ListaCompras listaCompras = new ListaCompras(feira);
		Context context = new Context(new Locale("pt", "BR"));
		context.setVariable("feira", feira);
		context.setVariable("listaCompras", listaCompras);
		
		Map<String, String> fotos = new HashMap<>();
		boolean adicionarProdutoMock = false;
		
		for(ItemFeira itemFeira : feira.getItens()) {
			Produto produto = itemFeira.getProduto();
			if(produto.isTemFoto()) {
				String cid = "foto-" + produto.getId();
				context.setVariable(cid, cid);
				produto.setFotoBase64("data:"+produto.getContentType()+";base64,"+Base64.encodeBase64String(fotoStorage.recuperar(FotoStorage.THUMBNAIL_PREFIX+produto.getFoto())));
				
				fotos.put(cid, produto.getFoto() + "|" + produto.getContentType());
			} else {
				adicionarProdutoMock = true;
				context.setVariable("produtoMock", "produtoMock");
			}
		}
		
		try {
			String email = thymeleaf.process("mail/lista-compras", context);
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom(mailConfig.getFromEmail());
			helper.setTo(mailConfig.getToEmail());
			helper.setSubject(String.format("%s - %s", feira.getNome(), feira.getDataCompra().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
			helper.setText(email, true);
			
			String arquivoLista = thymeleaf.process("mail/lista-compras-arquivo", context);
			helper.addAttachment("lista.html", new ByteArrayResource(arquivoLista.getBytes()));
			
			if (adicionarProdutoMock) {
				helper.addInline("produtoMock", new ClassPathResource("static/img/semFoto.png"));
			}
			
			for (String cid : fotos.keySet()) {
				String[] fotoContentType = fotos.get(cid).split("\\|");
				String foto = fotoContentType[0];
				String contentType = fotoContentType[1];
				byte[] fotoArray = fotoStorage.recuperar(foto);
				helper.addInline(cid, new ByteArrayResource(fotoArray), contentType);
			}
		
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error("Erro ao enviar e-mail", e);
		}
	}
}

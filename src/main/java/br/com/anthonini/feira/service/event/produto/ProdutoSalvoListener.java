package br.com.anthonini.feira.service.event.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.anthonini.feira.storage.FotoStorage;

@Component
public class ProdutoSalvoListener {
	
	@Autowired
	private FotoStorage fotoStorage;

	@EventListener(condition = "#event.temFoto()")
	public void produtoSalvo(ProdutoSalvoEvent event) {
		System.out.println(">>>>>>>> tem foto sim");
		fotoStorage.salvar(event.getProduto().getFoto());
	}
}

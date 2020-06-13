package br.com.anthonini.feira.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.anthonini.feira.storage.FotoStorage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {

	private static final Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	@Value("${feira.foto-storage-local.url-base}")
	private String urlBase;
	
	@Value("${feira.foto-storage-local.local}")
	private Path local;
	
	@Value("${feira.foto-storage-local.local}"+TEMP_SUFFIX)
	private Path localTemporario;
	
	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		if(files != null && files.length > 0) {
			MultipartFile foto = files[0];
			try {
				novoNome = renomearArquivo(foto.getOriginalFilename());
				foto.transferTo(new File(this.localTemporario.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome ));
			} catch (IllegalStateException | IOException e) {
				throw new RuntimeException("Erro ao salvar a foto na pasta temporária!", e);
			}
		}
		
		return novoNome;
	}

	@Override
	public byte[] recuperarFotoTemporaria(String nome) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao recuperar foto temporária", e);
		}
	}
	
	@Override
	public void salvar(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao mover foto para o loca final", e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao gerar o thumbnail para a foto", e);
		}
	}
	
	@Override
	public byte[] recuperar(String foto) {
		try {
			return Files.readAllBytes(this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao recuperar foto", e);
		}
	}
	
	@Override
	public void remover(String foto) {
		try {
			Files.delete(this.local.resolve(foto));
		} catch (IOException e) {
			LOGGER.error("Erro ao remover foto", e);
		}
		
		try {
			Files.delete(this.local.resolve(THUMBNAIL_PREFIX+foto));
		} catch (IOException e) {
			LOGGER.error("Erro ao remover thumnail da foto", e);
		}
	}
	
	@Override
	public String getUrlFoto(String foto) {
		if(StringUtils.isEmpty(foto) || foto.equals(THUMBNAIL_PREFIX)) {
			return "/img/semFoto.png";
		}
		return urlBase+"/"+foto;
	}
	
	@Override
	public String getUrlBase() {
		return urlBase;
	}
	
	@PostConstruct
	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			FileSystemUtils.deleteRecursively(this.localTemporario);
			Files.createDirectories(this.localTemporario);
			
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pastas das Fotos criadas!");
				LOGGER.debug("Pastas default: "+this.local.toAbsolutePath());
				LOGGER.debug("Pastas temporária: "+this.local.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar pasta para salvar fotos", e);
		}
	}
	
	
	private String renomearArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}
}

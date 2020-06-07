package br.com.anthonini.feira.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.anthonini.feira.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable {

	private MultipartFile[] files;
	private DeferredResult<FotoDTO> resultado;
	private FotoStorage fotoStorage;
	private String urlFotoTemporaria;
	
	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> resultado, FotoStorage fotoStorage, String urlFotoTemporaria) {
		this.files = files;
		this.resultado = resultado;
		this.fotoStorage = fotoStorage;
		this.urlFotoTemporaria = urlFotoTemporaria;
	}

	@Override
	public void run() {	
		String novoNome = this.fotoStorage.salvarTemporariamente(files);
		String contentType = files[0].getContentType();
		String urlFoto = urlFotoTemporaria+novoNome;
		
		resultado.setResult(new FotoDTO(novoNome, contentType, urlFoto));
	}

}

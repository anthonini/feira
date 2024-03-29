package br.com.anthonini.feira.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.anthonini.feira.dto.FotoDTO;
import br.com.anthonini.feira.storage.FotoStorage;
import br.com.anthonini.feira.storage.FotoStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {
	
	@Autowired
	private FotoStorage fotoStorage;

	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FotoDTO> resultado = new DeferredResult<>();
		
		Thread thread = new Thread(new FotoStorageRunnable(files, resultado, fotoStorage, fotoStorage.getUrlBase()+FotoStorage.TEMP_SUFFIX));
		thread.start();
		
		return resultado;
	}
	
	@GetMapping(FotoStorage.TEMP_SUFFIX+"{nome}")
	public byte[] recuperarFotoTemporaria(@PathVariable String nome) {
		return fotoStorage.recuperarFotoTemporaria(nome);
	}
	
	@GetMapping("/{foto}")
	public byte[] recuperarFoto(@PathVariable String foto) {
		return fotoStorage.recuperar(foto);
	}
}

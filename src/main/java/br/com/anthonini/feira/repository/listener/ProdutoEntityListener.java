package br.com.anthonini.feira.repository.listener;

import javax.persistence.PostLoad;

import br.com.anthonini.feira.FeiraApplication;
import br.com.anthonini.feira.model.Produto;
import br.com.anthonini.feira.storage.FotoStorage;

public class ProdutoEntityListener {
	
	@PostLoad
	public void postLoad(final Produto produto) {
		FotoStorage fotoStorage = FeiraApplication.getBean(FotoStorage.class);
		
		produto.setUrlFoto(fotoStorage.getUrlFoto(produto.getFoto()));
		produto.setUrlThumbnailFoto(fotoStorage.getUrlFoto(FotoStorage.THUMBNAIL_PREFIX+produto.getFoto()));
	}
}

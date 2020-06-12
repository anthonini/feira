package br.com.anthonini.feira.service.event.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.anthonini.feira.storage.FotoStorage;

@Component
public class ProdutoRemovidoListener {
	
	@Autowired
	private FotoStorage fotoStorage;

	@EventListener(condition = "#event.temFoto()")
	public void salvarFoto(ProdutoRemovidoEvent event) {
		fotoStorage.remover(event.getProduto().getFoto());
	}
}

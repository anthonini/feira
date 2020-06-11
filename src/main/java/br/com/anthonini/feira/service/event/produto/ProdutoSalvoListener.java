package br.com.anthonini.feira.service.event.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.anthonini.feira.storage.FotoStorage;

@Component
public class ProdutoSalvoListener {
	
	@Autowired
	private FotoStorage fotoStorage;

	@EventListener(condition = "#event.temFotoNova()")
	public void salvarFoto(ProdutoSalvoEvent event) {
		fotoStorage.salvar(event.getProduto().getFoto());
	}
	
	@EventListener(condition = "#event.removeFotoAntiga()")
	public void removerFoto(ProdutoSalvoEvent event) {
		fotoStorage.remover(event.getProduto().getFotoOriginal());
	}
}

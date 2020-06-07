package br.com.anthonini.feira.service.event.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.anthonini.feira.storage.FotoStorage;

@Component
public class ProdutoSalvoListener {
	
	@Autowired
	private FotoStorage fotoStorage;

	@EventListener(condition = "#event.temFoto() && #event.fotoAlterada()")
	public void salvarFoto(ProdutoSalvoEvent event) {
		fotoStorage.salvar(event.getProduto().getFoto());
	}
	
	@EventListener(condition = "#event.fotoAlterada()")
	public void removerFoto(ProdutoSalvoEvent event) {
		fotoStorage.remover(event.getProduto().getFotoOriginal());
	}
}

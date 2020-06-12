package br.com.anthonini.feira.service.event.produto;

import org.springframework.util.StringUtils;

import br.com.anthonini.feira.model.Produto;

public class ProdutoRemovidoEvent {

	private Produto produto;
	
	public ProdutoRemovidoEvent(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(produto.getFoto());
	}
}

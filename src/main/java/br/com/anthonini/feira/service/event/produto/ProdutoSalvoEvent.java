package br.com.anthonini.feira.service.event.produto;

import org.springframework.util.StringUtils;

import br.com.anthonini.feira.model.Produto;

public class ProdutoSalvoEvent {

	private Produto produto;
	
	public ProdutoSalvoEvent(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}
	
	public boolean temFotoNova() {
		return !StringUtils.isEmpty(produto.getFoto()) && 
				!produto.getFoto().equals(produto.getFotoOriginal());
	}
	
	public boolean removeFotoAntiga() {
		return !StringUtils.isEmpty(produto.getFotoOriginal()) && 
				!produto.getFotoOriginal().equals(produto.getFoto());
	}
	
}

package br.com.anthonini.feira.dto;

import java.math.BigDecimal;

public class ProdutosCadastrados {

	private BigDecimal valor;
	private Long total;
	
	public ProdutosCadastrados() {
	}

	public ProdutosCadastrados(BigDecimal valor, Long total) {
		this.valor = valor;
		this.total = total;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}

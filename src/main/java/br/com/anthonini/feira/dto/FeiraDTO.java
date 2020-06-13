package br.com.anthonini.feira.dto;

import java.math.BigDecimal;

public class FeiraDTO {

	private Integer quantidadeItens;
	private BigDecimal pesoTotal;
	private BigDecimal valorTotal;
	
	public FeiraDTO(Integer quantidadeItens, BigDecimal pesoTotal, BigDecimal valorTotal) {
		this.quantidadeItens = quantidadeItens;
		this.pesoTotal = pesoTotal;
		this.valorTotal = valorTotal;
	}

	public Integer getQuantidadeItens() {
		return quantidadeItens;
	}

	public void setQuantidadeItens(Integer quantidadeItens) {
		this.quantidadeItens = quantidadeItens;
	}

	public BigDecimal getPesoTotal() {
		return pesoTotal;
	}

	public void setPesoTotal(BigDecimal pesoTotal) {
		this.pesoTotal = pesoTotal;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}	
}
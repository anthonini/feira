package br.com.anthonini.feira.model;

public enum Sentido {
	CAIXA_FIM("Do caixa ao fim do supermercado"),
	FIM_CAIXA("Do fim ao caixa do supermercado");

	private String descricao;
	
	private Sentido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}

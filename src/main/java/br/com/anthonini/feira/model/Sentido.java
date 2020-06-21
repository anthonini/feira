package br.com.anthonini.feira.model;

public enum Sentido {
	ENTRADA_FIM("Da entrada para o fim"),
	FIM_ENTRADA("Do fim para a entrada");

	private String descricao;
	
	private Sentido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}

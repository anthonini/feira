package br.com.anthonini.feira.model;

public enum Sentido {
	ENTRADA_FIM("Da entrada ao fim"),
	FIM_ENTRADA("Do fim à entrada");

	private String descricao;
	
	private Sentido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}

package br.com.anthonini.feira.model;

public enum CobradoPor {

	KG("Kg"),
	UNIDADE("Unidade");	
	
	private String descricao;
	
	private CobradoPor(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}

package br.com.anthonini.feira.model;

public enum CobradoPor {

	KG("Kg", "js-peso"),
	UNIDADE("Unidade", "js-numero");	
	
	private String descricao;
	private String classeCss;

	private CobradoPor(String descricao, String classeCss) {
		this.descricao = descricao;
		this.classeCss = classeCss;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getClasseCss() {
		return classeCss;
	}
}

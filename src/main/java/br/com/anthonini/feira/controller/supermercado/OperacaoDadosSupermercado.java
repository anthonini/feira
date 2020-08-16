package br.com.anthonini.feira.controller.supermercado;

public enum OperacaoDadosSupermercado {
	ADICIONAR("Adicionar"),
	ALTERAR("Alterar");
	
	private String descricao;
	
	private OperacaoDadosSupermercado(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public String getMetodo() {
		return descricao.toLowerCase();
	}
}

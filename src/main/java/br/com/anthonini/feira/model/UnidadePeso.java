package br.com.anthonini.feira.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public enum UnidadePeso {

	GRAMA("grama", "g", 1, "#,##0"),
	QUILOGRAMA("quilograma", "Kg", 1000, "#,##0.000");
	
	private String descricao;
	private String simbolo;
	private Integer unidadeConversao;
	private String formato;
	
	private UnidadePeso(String descricao, String simbolo, Integer unidadeConversao, String formato) {
		this.descricao = descricao;
		this.simbolo = simbolo;
		this.unidadeConversao = unidadeConversao;
		this.formato = formato;
	}
	
	public String getDescricao(BigDecimal peso) {
		return String.format("%s %s%s", peso.toString(), this.descricao.toLowerCase(), (peso.intValue()>1?"s":""));
	}
	
	public String getDescricaoAbreviada(BigDecimal peso) {
		DecimalFormat df = new DecimalFormat(this.formato);
		return String.format("%s%s", df.format(peso.doubleValue()), this.simbolo);
	}
	
	public BigDecimal converter(BigDecimal peso, UnidadePeso unidadePeso) {
		double fator = (this.getUnidadeConversao()*1.0 / unidadePeso.getUnidadeConversao()*1.0);
		return peso.multiply(BigDecimal.valueOf(fator));
	}

	public String getDescricao() {
		return descricao;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public Integer getUnidadeConversao() {
		return unidadeConversao;
	}
	
	public String getFormato() {
		return formato;
	}
	
	public static void main(String[] args) {
		System.out.println(QUILOGRAMA.getDescricao(BigDecimal.ZERO));
		System.out.println(QUILOGRAMA.getDescricao(BigDecimal.ONE));
		System.out.println(QUILOGRAMA.getDescricao(BigDecimal.valueOf(1.1)));
		System.out.println(QUILOGRAMA.getDescricao(BigDecimal.valueOf(1.9999)));
		System.out.println(QUILOGRAMA.getDescricao(BigDecimal.valueOf(50)));
		System.out.println();
		
		System.out.println(GRAMA.getDescricao(BigDecimal.ZERO));
		System.out.println(GRAMA.getDescricao(BigDecimal.ONE));
		System.out.println(GRAMA.getDescricao(BigDecimal.valueOf(1.1)));
		System.out.println(GRAMA.getDescricao(BigDecimal.valueOf(1.999)));
		System.out.println(GRAMA.getDescricao(BigDecimal.valueOf(50)));
		System.out.println();
		
		System.out.println(QUILOGRAMA.getDescricaoAbreviada(BigDecimal.ZERO));
		System.out.println(QUILOGRAMA.getDescricaoAbreviada(BigDecimal.ONE));
		System.out.println(QUILOGRAMA.getDescricaoAbreviada(BigDecimal.valueOf(3.330)));
		System.out.println(QUILOGRAMA.getDescricaoAbreviada(BigDecimal.valueOf(50)));
		System.out.println();
		
		System.out.println(GRAMA.getDescricaoAbreviada(BigDecimal.ZERO));
		System.out.println(GRAMA.getDescricaoAbreviada(BigDecimal.ONE));
		System.out.println(GRAMA.getDescricaoAbreviada(BigDecimal.valueOf(3.330)));
		System.out.println(GRAMA.getDescricaoAbreviada(BigDecimal.valueOf(50)));
		System.out.println();
		
		System.out.println("2.2 Kg to g  = "+QUILOGRAMA.converter(BigDecimal.valueOf(2.2), UnidadePeso.GRAMA));
		System.out.println("2200 g to Kg = "+GRAMA.converter(BigDecimal.valueOf(2200), UnidadePeso.QUILOGRAMA));
		System.out.println("200  g to Kg = "+GRAMA.converter(BigDecimal.valueOf(200), UnidadePeso.QUILOGRAMA));
	}
}

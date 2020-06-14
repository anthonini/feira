package br.com.anthonini.feira.service;

public class FeiraVaziaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FeiraVaziaException() {
		super("Feira não pode estar vazia. É obrigatório adicionar pelo menos um item.");
	}
}

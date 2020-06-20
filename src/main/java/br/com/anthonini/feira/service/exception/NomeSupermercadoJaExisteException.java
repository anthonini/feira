package br.com.anthonini.feira.service.exception;

public class NomeSupermercadoJaExisteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NomeSupermercadoJaExisteException() {
		super("Nome do Supermercado já existe");
	}
}

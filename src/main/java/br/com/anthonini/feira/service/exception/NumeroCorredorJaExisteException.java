package br.com.anthonini.feira.service.exception;

public class NumeroCorredorJaExisteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NumeroCorredorJaExisteException() {
		super("Número do corredor já existe");
	}
}

package br.com.anthonini.feira.service.exception;

public class NomeCategoriaJaExisteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NomeCategoriaJaExisteException() {
		super("Nome da Categoria já existe");
	}
}

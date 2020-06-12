package br.com.anthonini.feira.service.exception;

public class NaoEPossivelRemoverProdutoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NaoEPossivelRemoverProdutoException(String message) {
		super(message);
	}
}

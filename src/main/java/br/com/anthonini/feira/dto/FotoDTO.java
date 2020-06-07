package br.com.anthonini.feira.dto;

public class FotoDTO {
	private String nome;
	private String contentType;
	private String urlFoto;

	public FotoDTO(String nome, String contentType, String urlFoto) {
		this.nome = nome;
		this.contentType = contentType;
		this.urlFoto = urlFoto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
}

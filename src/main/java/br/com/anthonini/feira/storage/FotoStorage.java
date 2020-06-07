package br.com.anthonini.feira.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	public static final String THUMBNAIL_PREFIX = "thumbnail.";

	public String salvarTemporariamente(MultipartFile[] files);
	
	public byte[] recuperarFotoTemporaria(String nome);

	public void salvar(String foto);

	public byte[] recuperar(String foto);

	public void remover(String foto);
}

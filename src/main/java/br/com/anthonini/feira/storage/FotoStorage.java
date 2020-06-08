package br.com.anthonini.feira.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	public static final String THUMBNAIL_PREFIX = "thumbnail.";
	
	public static final String TEMP_SUFFIX = "/temp/";

	public String salvarTemporariamente(MultipartFile[] files);
	
	public byte[] recuperarFotoTemporaria(String nome);

	public byte[] recuperar(String foto);
	
	public void salvar(String foto);

	public void remover(String foto);
	
	public String getUrlBase();
	
	public String getUrlFoto(String foto);
}

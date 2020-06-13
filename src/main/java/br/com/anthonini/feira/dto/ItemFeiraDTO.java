package br.com.anthonini.feira.dto;

public class ItemFeiraDTO {

	private String peso;
	private String valorTotal;
	private FeiraDTO feiraDTO;
	
	public ItemFeiraDTO(String peso, String valorTotal, FeiraDTO feiraDTO) {
		this.peso = peso;
		this.valorTotal = valorTotal;
		this.feiraDTO = feiraDTO;
	}
	
	public String getPeso() {
		return peso;
	}
	public void setPeso(String peso) {
		this.peso = peso;
	}
	public String getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
	public FeiraDTO getFeiraDTO() {
		return feiraDTO;
	}
	public void setFeiraDTO(FeiraDTO feiraDTO) {
		this.feiraDTO = feiraDTO;
	}
}

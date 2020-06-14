package br.com.anthonini.feira.repository.filter;

import java.time.LocalDate;

public class FeiraFilter {

	private String nome;
	private LocalDate de;
	private LocalDate ate;
	private String nomeItem;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDe() {
		return de;
	}
	public void setDe(LocalDate de) {
		this.de = de;
	}
	public LocalDate getAte() {
		return ate;
	}
	public void setAte(LocalDate ate) {
		this.ate = ate;
	}
	public String getNomeItem() {
		return nomeItem;
	}
	public void setNomeItem(String nomeItem) {
		this.nomeItem = nomeItem;
	}
}

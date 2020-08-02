package br.com.anthonini.feira.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.anthonini.feira.model.Corredor;
import br.com.anthonini.feira.model.ItemFeira;
import br.com.anthonini.feira.model.Sentido;

public class CorredorListaCompras {

	private Corredor corredor;
	
	private Sentido sentido = Sentido.CAIXA_FIM;
	
	private List<ItemFeira> itens = new ArrayList<>();
	
	public void adicionarItemFeira(ItemFeira itemFeira) {
		itens.add(itemFeira);
	}

	public CorredorListaCompras(Corredor corredor) {		
		this.corredor = corredor;
	}

	public Corredor getCorredor() {
		return corredor;
	}

	public void setCorredor(Corredor corredor) {
		this.corredor = corredor;
	}

	public Sentido getSentido() {
		return sentido;
	}

	public void setSentido(Sentido sentido) {
		this.sentido = sentido;
	}

	public List<ItemFeira> getItens() {
		return itens;
	}

	public void setItens(List<ItemFeira> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((corredor == null) ? 0 : corredor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorredorListaCompras other = (CorredorListaCompras) obj;
		if (corredor == null) {
			if (other.corredor != null)
				return false;
		} else if (!corredor.equals(other.corredor))
			return false;
		return true;
	}
}

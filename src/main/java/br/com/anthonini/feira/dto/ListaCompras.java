package br.com.anthonini.feira.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import br.com.anthonini.feira.model.Corredor;
import br.com.anthonini.feira.model.Feira;
import br.com.anthonini.feira.model.ItemFeira;
import br.com.anthonini.feira.model.Sentido;
import br.com.anthonini.feira.model.Supermercado;

public class ListaCompras {

	private Feira feira;
	private List<CorredorListaCompras> corredores = new ArrayList<>();

	public ListaCompras(Feira feira) {
		this.feira = feira;
		ordernarLista();
	}
	
	public boolean isVazia() {
		for(CorredorListaCompras corredor : corredores) {
			if(!corredor.getItens().isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public List<CorredorListaCompras> getCorredores() {
		return corredores;
	}

	public void setCorredores(List<CorredorListaCompras> corredores) {
		this.corredores = corredores;
	}
	
	private void ordernarLista() {
		if(feira != null) {
			adicionarItens();
			ordernarCorredores();
			ordernarItens();
		}
	}

	private void adicionarItens() {
		for(ItemFeira itemFeira : feira.getItens()) {
			getCorredor(itemFeira).adicionarItemFeira(itemFeira);
		}	
	}
	
	private CorredorListaCompras getCorredor(ItemFeira itemFeira) {
		Corredor corredor = null;
		if(feira.getSupermercado() != null) {
			corredor = feira.getSupermercado().getCorredor(itemFeira.getProduto().getCategoria());
		}
		
		Optional<CorredorListaCompras> corredorListaCompras = getCorredor(corredor);
		if(corredorListaCompras.isPresent()) {
			return corredorListaCompras.get();
		} else {
			return adicionarCorredor(corredor);
		}
	}
	
	private Optional<CorredorListaCompras> getCorredor(Corredor corredor) {
		return corredores.stream().filter(c -> c.getCorredor().equals(corredor)).findFirst();
	}

	private CorredorListaCompras adicionarCorredor(Corredor corredor) {
		CorredorListaCompras corredorListaCompras = null;
		if(corredor != null) {
			corredorListaCompras = new CorredorListaCompras(corredor);
		} else {
			corredorListaCompras = new CorredorListaCompras(corredorNaoCategorizado());
		}
		if(!corredores.contains(corredorListaCompras))
			corredores.add(corredorListaCompras);
		
		return getCorredor(corredorListaCompras);
	}
	
	private Corredor corredorNaoCategorizado() {
		Corredor corredor = new Corredor();
		corredor.setNumero(Long.MAX_VALUE);
		corredor.setDescricao("Não Categorizado");
		
		return corredor;
	}
	
	private CorredorListaCompras getCorredor(CorredorListaCompras corredorListaCompras) {
		return corredores.stream().filter(c -> c.equals(corredorListaCompras)).findFirst().get();
	}
	
	private void ordernarCorredores() {
		corredores.sort(new Comparator<CorredorListaCompras>() {
			@Override
			public int compare(CorredorListaCompras o1, CorredorListaCompras o2) {
				return o1.getCorredor().getNumero().compareTo(o2.getCorredor().getNumero());
			}
		});
		
		corredores.get(corredores.size()-1).getCorredor().setNumero(null);
		
		for(int i = 0; i < corredores.size(); i++) {
			corredores.get(i).setSentido(Sentido.values()[i%2]);
		}
	}
	
	private void ordernarItens() {
		for(CorredorListaCompras corredor : corredores) {
			if(!corredor.getCorredor().isNovo()) {
				corredor.getItens().sort(new Comparator<ItemFeira>() {
					@Override
					public int compare(ItemFeira o1, ItemFeira o2) {
						Supermercado supermercado = feira.getSupermercado();
						Integer posicao1 = supermercado.getSupermercadoCategoria(o1.getProduto().getCategoria()).getPosicaoCorredor();
						Integer posicao2 = supermercado.getSupermercadoCategoria(o2.getProduto().getCategoria()).getPosicaoCorredor();
						
						if(corredor.getSentido().equals(Sentido.CAIXA_FIM)) {
							return posicao1.compareTo(posicao2);
						} else {
							return posicao2.compareTo(posicao1);
						}
					}
				});
			}
		}
	}
}

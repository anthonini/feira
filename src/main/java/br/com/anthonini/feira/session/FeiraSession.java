package br.com.anthonini.feira.session;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.anthonini.feira.model.Feira;
import br.com.anthonini.feira.model.ItemFeira;
import br.com.anthonini.feira.model.Produto;

@Component
@SessionScope
public class FeiraSession {

	private Feira feira = new Feira();
	
	public void adicionarItem(Produto produto, int quantidade) {
		Optional<ItemFeira> itemFeiraOptional = buscarPorProduto(produto);
		
		ItemFeira itemFeira = itemFeiraOptional.orElse(new ItemFeira()); 
		if(itemFeiraOptional.isPresent()) {
			itemFeira.setQuantidade(itemFeira.getQuantidade() + quantidade);
		} else {
			itemFeira.setProduto(produto);
			itemFeira.setQuantidade(quantidade);
			itemFeira.setPrecoCompra(produto.getPreco());
			feira.getItens().add(itemFeira);
		}
	}
	
	private Optional<ItemFeira> buscarPorProduto(Produto produto) {
		return feira.getItens().stream()
			.filter(i -> i.getProduto().equals(produto))
			.findAny();
	}

	public Feira getFeira() {
		return feira;
	}

	public void setFeira(Feira feira) {
		this.feira = feira;
	}
}

package br.com.anthonini.feira.session;

import java.math.BigDecimal;
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
	
	public void alterarQuantidade(Produto produto, BigDecimal quantidade, boolean porQuantidade) {
		Optional<ItemFeira> itemOptional = buscarPorProduto(produto);
		
		ItemFeira item = itemOptional.orElse(new ItemFeira());
		item.setPorQuantidade(porQuantidade);
		
		if(quantidade.compareTo(BigDecimal.ZERO) <= 0 ) {
			removerItemPorProduto(produto);
		} else {
			if(itemOptional.isPresent()) {
				item.setQuantidade(quantidade);
			} else {
				item.setProduto(produto);
				item.setFeira(feira);
				item.setPrecoCompra(produto.getPreco());
				item.setQuantidade(quantidade);
				feira.getItens().add(item);
			}
		}
	}
	
	public Optional<ItemFeira> buscarPorProduto(Produto produto) {
		return feira.getItens().stream()
			.filter(i -> i.getProduto().equals(produto))
			.findAny();
	}
	
	private void removerItemPorProduto(Produto produto) {
		feira.getItens().removeIf(i -> i.getProduto().equals(produto));
	}

	public Feira getFeira() {
		return feira;
	}

	public void setFeira(Feira feira) {
		this.feira = feira;
	}

	public Integer getQuantidadeItens() {
		return feira.getItens().size();
	}
}

package br.com.anthonini.feira.session;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.anthonini.feira.dto.FeiraDTO;
import br.com.anthonini.feira.model.Feira;
import br.com.anthonini.feira.model.ItemFeira;
import br.com.anthonini.feira.model.Produto;

@Component
@SessionScope
public class FeiraSession {

	private Feira feira = new Feira();
	
	public void alterarQuantidade(Produto produto, Integer quantidade, BigDecimal peso, boolean porPeso) {
		Optional<ItemFeira> itemOptional = buscarPorProduto(produto);
		
		ItemFeira item = itemOptional.orElse(new ItemFeira());
		item.setPorPeso(porPeso);
		item.setPeso(peso);
		item.setQuantidade(quantidade);
		
		if((porPeso && peso.compareTo(BigDecimal.ZERO) <= 0) || (!porPeso && quantidade <= 0)) {
			removerItemPorProduto(produto);
		} else {
			if(!itemOptional.isPresent()) {
				item.setProduto(produto);
				item.setFeira(feira);
				item.setPrecoCompra(produto.getPreco());
				feira.getItens().add(item);
			}
			
			item.calculcarPeso();
		}
	}
	
	public Optional<ItemFeira> buscarPorProduto(Produto produto) {
		return feira.getItens().stream()
			.filter(i -> i.getProduto().equals(produto))
			.findAny();
	}
	
	public void removerItemPorProduto(Produto produto) {
		feira.getItens().removeIf(i -> i.getProduto().equals(produto));
	}

	public Feira getFeira() {
		return feira;
	}

	public void setFeira(Feira feira) {
		this.feira = feira;
	}
	
	public FeiraDTO getFeiraDTO() {
		return new FeiraDTO(feira.getQuantidadeItens(), feira.getPesoTotal(), feira.getValorTotal());
	}
}

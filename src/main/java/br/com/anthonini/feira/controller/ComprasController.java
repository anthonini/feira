package br.com.anthonini.feira.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.anthonini.feira.dto.ItemFeiraDTO;
import br.com.anthonini.feira.model.ItemFeira;
import br.com.anthonini.feira.model.Produto;
import br.com.anthonini.feira.repository.ProdutoRepository;
import br.com.anthonini.feira.session.FeiraSession;
import br.com.anthonini.feira.storage.FotoStorage;

/**
 * @author Anthonini
 */
@Controller
@RequestMapping("/compras")
public class ComprasController {

	@Autowired
	private FeiraSession feiraSession;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("compras/listagem");
		List<Produto> produtos = produtoRepository.findAll();		
		List<ItemFeira> itens = new ArrayList<>();
		
		for(Produto produto : produtos) {
			produto.setUrlFoto(fotoStorage.getUrlFoto(produto.getFoto()));
			Optional<ItemFeira> itemOptional = feiraSession.buscarPorProduto(produto);
			ItemFeira item = itemOptional.orElse(new ItemFeira());
			if(!itemOptional.isPresent()) {
				item.setProduto(produto);
				item.setPorPeso(produto.isCobradoPorKG());				
			}			
			itens.add(item);
		}
		
		mv.addObject("itens", itens);
			
		return mv;
	}

	@PutMapping("/item/{produtoId}")
	public @ResponseBody ItemFeiraDTO alterarQuantidadeItem(@PathVariable Long produtoId, Integer quantidade, BigDecimal peso, boolean porPeso) {
		Produto produto = produtoRepository.findById(produtoId).get();
		produto.setUrlFoto(fotoStorage.getUrlFoto(produto.getFoto()));
		feiraSession.alterarQuantidade(produto, quantidade, peso, porPeso);
		
		ItemFeira item = feiraSession.buscarPorProduto(produto).orElse(new ItemFeira());		
		ItemFeiraDTO itemFeiraDTO = new ItemFeiraDTO(item.getDescricaoPeso(), item.getValorTotal().toString(), feiraSession.getFeiraDTO());
		
		return itemFeiraDTO;
	}
}

package br.com.anthonini.feira.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
			ItemFeira item = feiraSession.buscarPorProduto(produto).orElse(new ItemFeira());
			item.setProduto(produto);
			itens.add(item);
		}
		
		mv.addObject("itens", itens);
			
		return mv;
	}
	
	@GetMapping("/listagem")
	public ModelAndView listagem() {
		ModelAndView mv = new ModelAndView("compras/listagem_backup");
		List<Produto> produtos = produtoRepository.findAll();		
		List<ItemFeira> itens = new ArrayList<>();
		
		for(Produto produto : produtos) {
			produto.setUrlFoto(fotoStorage.getUrlFoto(produto.getFoto()));
			itens.add(new ItemFeira(produto, null));
		}
		
		mv.addObject("itens", itens);
			
		return mv;
	}
	

	@PutMapping("/item/{produtoId}")
	public @ResponseBody ResponseEntity<?> changeItemQuantity(@PathVariable Long produtoId, BigDecimal quantidade, boolean porQuantidade) {
		Produto produto = produtoRepository.findById(produtoId).get();
		produto.setUrlFoto(fotoStorage.getUrlFoto(produto.getFoto()));
		feiraSession.alterarQuantidade(produto, quantidade, porQuantidade);

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/carrinho")
	public ModelAndView carrinho() {
		ModelAndView mv = new ModelAndView("compras/carrinho");
		mv.addObject("itens", feiraSession.getFeira().getItens());
			
		return mv;
	}
}

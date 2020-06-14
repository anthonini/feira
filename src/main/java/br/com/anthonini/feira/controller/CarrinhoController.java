package br.com.anthonini.feira.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.anthonini.feira.dto.FeiraDTO;
import br.com.anthonini.feira.model.Produto;
import br.com.anthonini.feira.session.FeiraSession;

/**
 * @author Anthonini
 */
@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

	@Autowired
	private FeiraSession feiraSession;
	
	@GetMapping
	public ModelAndView carrinho() {
		ModelAndView mv = new ModelAndView("carrinho/carrinho");
		mv.addObject("feira", feiraSession.getFeira());
			
		return mv;
	}
	
	@DeleteMapping("/{produtoId}")
	public @ResponseBody FeiraDTO removerItem(@PathVariable Long produtoId) {
		Produto produto = new Produto();
		produto.setId(produtoId);
		feiraSession.removerItemPorProduto(produto);
		
		return feiraSession.getFeiraDTO();
	}
	
	@PutMapping("/itens")
	public ModelAndView itensCarrinho() {
		ModelAndView mv = new ModelAndView("carrinho/itensMiniCarrinho");
		mv.addObject("itens", feiraSession.getFeira().getItens());
		
		return mv;
	}
}

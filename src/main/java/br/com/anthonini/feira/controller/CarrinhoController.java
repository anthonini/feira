package br.com.anthonini.feira.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
		ModelAndView mv = new ModelAndView("compras/carrinho");
		mv.addObject("feira", feiraSession.getFeira());
			
		return mv;
	}
}

package br.com.anthonini.feira.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.anthonini.feira.model.UnidadePeso;
import br.com.anthonini.feira.repository.FeiraRepository;
import br.com.anthonini.feira.repository.ProdutoRepository;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FeiraRepository feiraRepository;
	
	@GetMapping
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("dashboard");
		mv.addObject("produtosCadastrados", produtoRepository.produtosCadastrados());
		mv.addObject("pesoMedio", 	 UnidadePeso.QUILOGRAMA.getDescricaoAbreviada(feiraRepository.pesoMedio()));
		mv.addObject("pesoMedioMes", UnidadePeso.QUILOGRAMA.getDescricaoAbreviada(feiraRepository.pesoMedioMes()));
		
		mv.addObject("valorTotalFeiras", feiraRepository.valorTotalFeiras());
		mv.addObject("valorMedioFeiras", feiraRepository.valorMedioFeiras());
		mv.addObject("valorFeirasAno",   feiraRepository.valorFeirasAno());
		mv.addObject("valorFeirasMes", 	 feiraRepository.valorFeirasMes());
		
		return mv;
	}
}

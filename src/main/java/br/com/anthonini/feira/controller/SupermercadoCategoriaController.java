package br.com.anthonini.feira.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.anthonini.arquitetura.controller.AbstractController;
import br.com.anthonini.feira.model.Categoria;
import br.com.anthonini.feira.model.Sentido;
import br.com.anthonini.feira.model.SupermercadoCategoria;
import br.com.anthonini.feira.repository.CategoriaRepository;

@Controller
@RequestMapping("/supermercado-categoria")
public class SupermercadoCategoriaController extends AbstractController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@PutMapping("/novo")
	public ModelAndView modal(SupermercadoCategoria supermercadoCategoria, ModelMap model, Integer[] categoriasAdicionadas) {
		ModelAndView mv = new ModelAndView("supermercadoCategoria/adicionarCategoriaModal");
		List<Categoria> categorias = categoriaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
		mv.addObject("categorias", categorias);
		mv.addObject("sentidos", Sentido.values());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView adicionar(@Valid SupermercadoCategoria supermercadoCategoria, BindingResult bindingResult, Integer[] categorias, ModelMap model) {
		if(bindingResult.hasErrors()) {
			addMensagensErroValidacao(model, bindingResult);
		} else {
			model.addAttribute("sucesso", true);
			model.addAttribute("categoriaAdicionada", supermercadoCategoria);
			model.addAttribute("descricaoSentido", supermercadoCategoria.getSentido().getDescricao());
			model.addAttribute("supermercadoCategoria", new SupermercadoCategoria());
			addMensagemSucess(model, "Categoria Adicionada com sucesso!");
		}
		
		return modal(supermercadoCategoria, model, categorias);
	}
}

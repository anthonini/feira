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
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PutMapping
	public ModelAndView modal(@RequestBody SupermercadoCategoria supermercadoCategoria, ModelMap model, OperacaoSupermercadoCategoria operacao) {
		ModelAndView mv = new ModelAndView("supermercadoCategoria/categoriaModal");
		List<Categoria> categorias = categoriaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
		mv.addObject("categorias", categorias);
		mv.addObject("sentidos", Sentido.values());
		mv.addObject("operacao", operacao.getDescricao());
		mv.addObject("metodo", operacao.getMetodo());
		
		return mv;
	}
	
	@PostMapping("/adicionar")
	public ModelAndView adicionar(@Valid SupermercadoCategoria supermercadoCategoria, BindingResult bindingResult, ModelMap model) {
		if(bindingResult.hasErrors()) {
			addMensagensErroValidacao(model, bindingResult);
		} else {
			model.addAttribute("categoriaAdicionada", supermercadoCategoria);
			model.addAttribute("descricaoSentido", supermercadoCategoria.getSentido().getDescricao());
			model.addAttribute("supermercadoCategoria", new SupermercadoCategoria());
			addMensagemSucess(model, "Categoria adicionada com sucesso!");
		}
		
		return modal(null, model, OperacaoSupermercadoCategoria.ADICIONAR);
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid SupermercadoCategoria supermercadoCategoria, BindingResult bindingResult, ModelMap model) {
		if(bindingResult.hasErrors()) {
			addMensagensErroValidacao(model, bindingResult);
		} else {
			model.addAttribute("categoriaAlterada", supermercadoCategoria);
			model.addAttribute("descricaoSentido", supermercadoCategoria.getSentido().getDescricao());
		}
		
		return modal(supermercadoCategoria, model, OperacaoSupermercadoCategoria.ALTERAR);
	}
	
	private enum OperacaoSupermercadoCategoria {
		ADICIONAR("Adicionar"),
		ALTERAR("Alterar");
		
		private String descricao;
		
		private OperacaoSupermercadoCategoria(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
		
		public String getMetodo() {
			return descricao.toLowerCase();
		}
	}
}

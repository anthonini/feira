package br.com.anthonini.feira.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.anthonini.arquitetura.controller.AbstractController;
import br.com.anthonini.arquitetura.controller.page.PageWrapper;
import br.com.anthonini.feira.model.Feira;
import br.com.anthonini.feira.repository.FeiraRepository;
import br.com.anthonini.feira.repository.filter.FeiraFilter;
import br.com.anthonini.feira.service.FeiraService;
import br.com.anthonini.feira.service.FeiraVaziaException;
import br.com.anthonini.feira.session.FeiraSession;

/**
 * @author Anthonini
 */
@Controller
@RequestMapping("/feira")
public class FeiraController extends AbstractController {
	
	@Autowired
	private FeiraSession feiraSession;
	
	@Autowired
	private FeiraService service;
	
	@Autowired
	private FeiraRepository repository;

	@GetMapping
	public String index() {
		return "index";
	}
	
	@GetMapping("/nova")
	public ModelAndView nova(Feira feira, ModelMap model) {
		ModelAndView mv = new ModelAndView("feira/form");
		feira.setItens(feiraSession.getFeira().getItens());
		
		return mv;
	}
	
	@PostMapping("/nova")
	public ModelAndView cadastrar(@Valid Feira feira, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			return retornarErrosValidacao(feira, result, model);
		}
		
		feira.adicionarItens(feiraSession.getFeira().getItens());
		
		try {
			service.salvar(feira);
		} catch (FeiraVaziaException e) {
			result.rejectValue("itens", e.getMessage(), e.getMessage());
			return retornarErrosValidacao(feira, result, model);
		}
		
		feiraSession.setFeira(new Feira());
		addMensagemSucess(redirectAttributes, "Feira salva com sucesso!");
		return new ModelAndView("redirect:nova");
	}
	
	@GetMapping("/list")
	public ModelAndView listar(FeiraFilter feiraFilter, HttpServletRequest httpServletRequest, @PageableDefault(size = 3) @SortDefault(value="dataCompra") Pageable pageable) {
		ModelAndView mv = new ModelAndView("feira/list");
		PageWrapper<Feira> paginaWrapper = new PageWrapper<>(repository.filtrar(feiraFilter,pageable),httpServletRequest);
        mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") Feira feira) {
		service.remover(feira);			 
		return ResponseEntity.ok().build();
	}
	
	private ModelAndView retornarErrosValidacao(Feira feira, BindingResult result, ModelMap model) {
		addMensagensErroValidacao(model, result);
		return nova(feira, model);
	}
}

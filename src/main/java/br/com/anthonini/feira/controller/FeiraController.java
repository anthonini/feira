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
import br.com.anthonini.feira.dto.ListaCompras;
import br.com.anthonini.feira.model.Feira;
import br.com.anthonini.feira.repository.FeiraRepository;
import br.com.anthonini.feira.repository.SupermercadoRepository;
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
	
	@Autowired
	private SupermercadoRepository supermercadoRepository;
	
	@GetMapping
	public ModelAndView listar(FeiraFilter feiraFilter, HttpServletRequest httpServletRequest, @PageableDefault(size = 3) @SortDefault(value="dataCompra") Pageable pageable) {
		ModelAndView mv = new ModelAndView("feira/list");
		PageWrapper<Feira> paginaWrapper = new PageWrapper<>(repository.filtrar(feiraFilter,pageable),httpServletRequest);
        mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@GetMapping("/nova")
	public ModelAndView form(Feira feira, ModelMap model) {
		ModelAndView mv = new ModelAndView("feira/form");
		mv.addObject("supermercados", supermercadoRepository.findAll());
		if(feira.isNova())
			feira.setItens(feiraSession.getFeira().getItens());
		
		return mv;
	}
	
	@PostMapping("/nova")
	public ModelAndView cadastrar(@Valid Feira feira, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
		return salvar(feira, result, model, redirectAttributes);
	}
	
	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable("id") Feira feira, ModelMap model, RedirectAttributes redirect) {
		if (feira == null) {
            addMensagemErro(redirect, "Feira não encontrada");
            return new ModelAndView("redirect:/feira/list");
        }

		model.addAttribute("feira", feira);
        return form(feira, model);
    }
	
	@PostMapping("/{\\d+}")
	public ModelAndView salvarAlteracao(@Valid Feira feira, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
		return salvar(feira, result, model, redirectAttributes);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") Feira feira) {
		service.remover(feira);			 
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/lista/{id}")
	public ModelAndView listaCompras(@PathVariable("id") Feira feira, ModelMap model, RedirectAttributes redirect) {
		if (feira == null) {
            addMensagemErro(redirect, "Feira não encontrada");
            return new ModelAndView("redirect:/feira");
        }
		
		ListaCompras listaCompras = new ListaCompras(feira);

		model.addAttribute(feira);
		model.addAttribute(listaCompras);
        return new ModelAndView("feira/lista-feira");
    }
	
	private ModelAndView retornarErrosValidacao(Feira feira, BindingResult result, ModelMap model) {
		addMensagensErroValidacao(model, result);
		return form(feira, model);
	}
	
	private ModelAndView salvar(Feira feira, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			return retornarErrosValidacao(feira, result, model);
		}
		
		try {
			service.salvar(feira);
		} catch (FeiraVaziaException e) {
			result.rejectValue("itens", e.getMessage(), e.getMessage());
			return retornarErrosValidacao(feira, result, model);
		}
		
		addMensagemSucesso(redirectAttributes, "Feira salva com sucesso!");
		return new ModelAndView("redirect:/feira");
	}
}

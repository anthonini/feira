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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.anthonini.arquitetura.controller.AbstractController;
import br.com.anthonini.arquitetura.controller.page.PageWrapper;
import br.com.anthonini.feira.model.Categoria;
import br.com.anthonini.feira.repository.CategoriaRepository;
import br.com.anthonini.feira.service.CategoriaService;
import br.com.anthonini.feira.service.exception.NaoEPossivelRemoverEntidadeException;
import br.com.anthonini.feira.service.exception.NomeCategoriaJaExisteException;

@Controller
@RequestMapping("/categoria")
public class CategoriaController extends AbstractController {

	@Autowired
	private CategoriaService service;
	
	@Autowired
	private CategoriaRepository repository;
	
	@GetMapping("/nova")
	public ModelAndView form(Categoria categoria, ModelMap model) {
		ModelAndView mv = new ModelAndView("categoria/form");
		
		return mv;
	}
	
	@PostMapping("/nova")
	public ModelAndView salvar(@Valid Categoria categoria, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			addMensagensErroValidacao(modelMap, bindingResult);
			return form(categoria, modelMap);
		}
		
		try {
			service.salvar(categoria);
		} catch (NomeCategoriaJaExisteException e) {
			bindingResult.rejectValue("nome", e.getMessage(), e.getMessage());
			addMensagensErroValidacao(modelMap, bindingResult);
			return form(categoria, modelMap);
		}
		
		addMensagemSucess(redirectAttributes, "Categoria salva com sucesso!");
		return new ModelAndView("redirect:/categoria/nova");
	}
	
	@GetMapping
	public ModelAndView listar(@RequestParam(defaultValue = "") String nome, HttpServletRequest httpServletRequest, @PageableDefault(size = 5) @SortDefault(value="nome") Pageable pageable) {		
		ModelAndView mv = new ModelAndView("categoria/list");
		PageWrapper<Categoria> paginaWrapper = new PageWrapper<>(repository.findByNomeContainingIgnoreCase(nome, pageable),httpServletRequest);
        mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Categoria categoria, ModelMap model, RedirectAttributes redirect) {
        if (categoria == null) {
            addMensagemErro(redirect, "Categoria não encontrada");
            return new ModelAndView("redirect:/categoria");
        }

        model.addAttribute("categoria", categoria);
        return form(categoria, model);
    }
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") Categoria categoria) {
		try {
			service.remover(categoria);
		} catch (NaoEPossivelRemoverEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		 
		return ResponseEntity.ok().build();
	}
}

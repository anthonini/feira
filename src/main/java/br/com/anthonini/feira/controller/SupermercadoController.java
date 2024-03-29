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
import br.com.anthonini.feira.controller.validator.SupermercadoValidator;
import br.com.anthonini.feira.model.Supermercado;
import br.com.anthonini.feira.repository.SupermercadoRepository;
import br.com.anthonini.feira.service.SupermercadoService;
import br.com.anthonini.feira.service.exception.NaoEPossivelRemoverEntidadeException;
import br.com.anthonini.feira.service.exception.NomeSupermercadoJaExisteException;
import br.com.anthonini.feira.session.SupermercadoSession;

@Controller
@RequestMapping("/supermercado")
public class SupermercadoController extends AbstractController {

	@Autowired
	private SupermercadoService service;
	
	@Autowired
	private SupermercadoRepository repository;
	
	@Autowired
	private SupermercadoSession sessao;
	
	@Autowired
	private SupermercadoValidator supermercadoValidator;
	
	@GetMapping("/novo")
	public ModelAndView form(Supermercado supermercado, ModelMap model) {
		sessao.adicionarSupermercado(supermercado);
		return new ModelAndView("redirect:/supermercado/edit?id="+supermercado.getUuid());
	}
	
	@GetMapping("/edit")
    public String edit(String id, ModelMap model, RedirectAttributes redirect) {
        Supermercado supermercado = sessao.getSupermercado(id);
        if (supermercado == null) {
        	addMensagemErro(redirect, "Supermercado não encontrado");
            return "redirect:/supermercado";
        }

        model.addAttribute(supermercado);
        return "supermercado/form";
    }

	
	@PostMapping("/novo")
	public ModelAndView cadastrar(@Valid Supermercado supermercado, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		return salvar(supermercado, bindingResult, modelMap, redirectAttributes);
	}
	
	@GetMapping
	public ModelAndView listar(@RequestParam(defaultValue = "") String nome, HttpServletRequest httpServletRequest, @PageableDefault(size = 5) @SortDefault(value="nome") Pageable pageable) {		
		ModelAndView mv = new ModelAndView("supermercado/list");
		PageWrapper<Supermercado> paginaWrapper = new PageWrapper<>(repository.findByNomeContainingIgnoreCase(nome, pageable),httpServletRequest);
        mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable("id") Supermercado supermercado, ModelMap model, RedirectAttributes redirect) {
        if (supermercado == null) {
            addMensagemErro(redirect, "Supermercado não encontrado");
            return new ModelAndView("redirect:/supermercado");
        }

        supermercado.getCorredores().iterator();
        supermercado.getSupermercadoCategorias().iterator();
        model.addAttribute("supermercado", supermercado);
        return form(supermercado, model);
    }
	
	@PostMapping("/{\\d+}")
	public ModelAndView salvarAlteracao(@Valid Supermercado supermercado, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		return salvar(supermercado, bindingResult, modelMap, redirectAttributes);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") Supermercado supermercado) {
		try {
			service.remover(supermercado);
		} catch (NaoEPossivelRemoverEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		 
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/corredores")
	public String corredores(String uuid, ModelMap model) {
		model.addAttribute(sessao.getSupermercado(uuid));
		return "supermercado/fragments/corredores";
	}
	
	@GetMapping("/categorias")
	public String categorias(String uuid, ModelMap model) {
		sessao.getSupermercado(uuid).ordernarSupermercadoCategorias();
		model.addAttribute(sessao.getSupermercado(uuid));
		return "supermercado/fragments/supermercadoCategorias";
	}
	
	private ModelAndView salvar(Supermercado supermercado, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		String nome = supermercado.getNome(); 
		supermercado = sessao.getSupermercado(supermercado.getUuid());
		supermercado.setNome(nome);
		supermercadoValidator.validate(supermercado, bindingResult);
		if(bindingResult.hasErrors()) {
			addMensagensErroValidacao(redirectAttributes, bindingResult);
			return form(supermercado, modelMap);
		}
		
		try {
			service.salvar(supermercado);
		} catch (NomeSupermercadoJaExisteException e) {
			bindingResult.rejectValue("nome", e.getMessage(), e.getMessage());
			addMensagensErroValidacao(redirectAttributes, bindingResult);
			return form(supermercado, modelMap);
		}
		
		sessao.remover(supermercado.getUuid());
		addMensagemSucesso(redirectAttributes, "Supermercado salvo com sucesso!");
		return new ModelAndView("redirect:/supermercado");
	}
}

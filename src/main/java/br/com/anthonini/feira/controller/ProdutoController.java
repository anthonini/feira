package br.com.anthonini.feira.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
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
import br.com.anthonini.feira.model.CobradoPor;
import br.com.anthonini.feira.model.Produto;
import br.com.anthonini.feira.model.UnidadePeso;
import br.com.anthonini.feira.repository.CategoriaRepository;
import br.com.anthonini.feira.repository.ProdutoRepository;
import br.com.anthonini.feira.repository.filter.ProdutoFilter;
import br.com.anthonini.feira.service.ProdutoService;
import br.com.anthonini.feira.service.exception.NaoEPossivelRemoverEntidadeException;

@Controller
@RequestMapping("/produto")
public class ProdutoController extends AbstractController {
		
	@Autowired
	private ProdutoService service;
	
	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping("/novo")
	public String form(Produto produto, ModelMap model) {
		model.addAttribute("cobradosPor", CobradoPor.values());
		model.addAttribute("unidadesPeso", UnidadePeso.values());
		model.addAttribute("categorias", categoriaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome" )));
		
		return "produto/form";
	}
	
	@PostMapping("/novo")
	public String cadastrar(@Valid Produto produto, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
		return salvar(produto, result, model, redirectAttributes, "redirect:novo");
	}
	
	@GetMapping
	public ModelAndView listar(ProdutoFilter produtoFilter, HttpServletRequest httpServletRequest, @PageableDefault(size = 30) @SortDefault(value="nome") Pageable pageable) {
		ModelAndView mv = new ModelAndView("produto/list");
		PageWrapper<Produto> paginaWrapper = new PageWrapper<>(repository.filtrar(produtoFilter,pageable),httpServletRequest);
        mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@GetMapping("/{id}")
	public String alterar(@PathVariable("id") Produto produto, ModelMap model, RedirectAttributes redirect) {
        if (produto == null) {
            addMensagemErro(redirect, "Produto não encontrado");
            return "redirect:/produto";
        }
        
        if(!StringUtils.isEmpty(produto.getFoto())) {
        	produto.setFotoOriginal(produto.getFoto());
        }

        model.addAttribute("produto", produto);
        return form(produto, model);
    }
	
	@PostMapping("/{\\d+}")
	public String salvarAlteracao(@Valid Produto produto, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
		return salvar(produto, result, model, redirectAttributes, "redirect:/produto");
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") Produto produto) {
		try {
			service.remover(produto);
		} catch (NaoEPossivelRemoverEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		 
		return ResponseEntity.ok().build();
	}

	private String retornarErrosValidacao(Produto produto, BindingResult result, ModelMap model) {
		addMensagensErroValidacao(model, result);
		return form(produto, model);
	}
	
	private String salvar(Produto produto, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes, String url) {
		if(result.hasErrors()) {
			return retornarErrosValidacao(produto, result, model);
		}
		
		service.cadastrar(produto);
		
		addMensagemSucesso(redirectAttributes, "Produto salvo com sucesso!");
		return url;
	}
}

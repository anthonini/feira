package br.com.anthonini.feira.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.anthonini.arquitetura.controller.AbstractController;
import br.com.anthonini.feira.controller.supermercado.OperacaoDadosSupermercado;
import br.com.anthonini.feira.model.Categoria;
import br.com.anthonini.feira.model.Supermercado;
import br.com.anthonini.feira.model.SupermercadoCategoria;
import br.com.anthonini.feira.repository.CategoriaRepository;
import br.com.anthonini.feira.session.SupermercadoSession;

@Controller
@RequestMapping("/supermercado-categoria")
public class SupermercadoCategoriaController extends AbstractController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private SupermercadoSession sessao;
	
	@PutMapping
	public ModelAndView modal(@RequestBody SupermercadoCategoria supermercadoCategoria, ModelMap model, OperacaoDadosSupermercado operacao, String uuid) {
		Supermercado supermercado = sessao.getSupermercado(uuid);
		supermercadoCategoria = getSupermercadoCategoria(supermercado, supermercadoCategoria);
		if(supermercadoCategoria != null) {
			model.addAttribute(supermercadoCategoria);
		}
		
		ModelAndView mv = new ModelAndView("supermercado/fragments/supermercadoCategoriaModal");
		List<Categoria> categorias = categoriaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
		
		List<Categoria> categoriasAdicionadas = supermercado.getSupermercadoCategorias().stream()
													.map(SupermercadoCategoria::getCategoria)
													.collect(Collectors.toList());
		categorias.removeIf(c -> categoriasAdicionadas.contains(c));
		
		mv.addObject("categorias", categorias);
		mv.addObject("corredores", supermercado.getCorredores());
		mv.addObject("operacao", operacao.getDescricao());
		mv.addObject("metodo", operacao.getMetodo());
		
		return mv;
	}
	
	@PostMapping("/adicionar")
	public ModelAndView adicionar(@Valid SupermercadoCategoria supermercadoCategoria, BindingResult bindingResult, ModelMap model, String uuid) {
		if(bindingResult.hasErrors()) {
			addMensagensErroValidacao(model, bindingResult);
		} else {
			Supermercado supermercado = sessao.getSupermercado(uuid);
			supermercadoCategoria.setCorredor(supermercado.getCorredor(supermercadoCategoria.getCorredor().getNumero()).get());
			supermercado.getSupermercadoCategorias().add(supermercadoCategoria);
			supermercado.ordernarSupermercadoCategorias();
			model.addAttribute("categoriaAdicionada", true);
			model.addAttribute("supermercadoCategoria", new SupermercadoCategoria());
			addMensagemSucesso(model, "Categoria adicionada com sucesso!");
		}
		
		return modal(null, model, OperacaoDadosSupermercado.ADICIONAR, uuid);
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid SupermercadoCategoria supermercadoCategoria, BindingResult bindingResult, ModelMap model, String uuid) {
		if(bindingResult.hasErrors()) {
			addMensagensErroValidacao(model, bindingResult);
		} else {
			Supermercado supermercado = sessao.getSupermercado(uuid);
			supermercado.getSupermercadoCategorias().removeIf(c -> c.getCategoria().equals(supermercadoCategoria.getCategoria()));
			supermercado.getSupermercadoCategorias().add(supermercadoCategoria);
			supermercado.ordernarSupermercadoCategorias();
			model.addAttribute("categoriaAlterada", true);
		}
		
		return modal(supermercadoCategoria, model, OperacaoDadosSupermercado.ALTERAR, uuid);
	}
	
	@DeleteMapping("/remover/{idCategoria}")
	public @ResponseBody ResponseEntity<?> remover(@PathVariable Long idCategoria, ModelMap model, String uuid) {
		Supermercado supermercado = sessao.getSupermercado(uuid);
		supermercado.getSupermercadoCategorias().removeIf(c -> c.getCategoria().getId().equals(idCategoria));
		
		return ResponseEntity.ok().build();
	}

	private SupermercadoCategoria getSupermercadoCategoria(Supermercado supermercado, SupermercadoCategoria supermercadoCategoria) {
		if(supermercadoCategoria != null) {
			if(!supermercadoCategoria.isNovo() && supermercado.getSupermercadoCategorias().contains(supermercadoCategoria)) {
				return supermercado.getSupermercadoCategorias().get(supermercado.getSupermercadoCategorias().indexOf(supermercadoCategoria));
			} else if(supermercadoCategoria.getCategoria() != null && !supermercadoCategoria.getCategoria().isNova()) {
				Categoria categoria = supermercadoCategoria.getCategoria();
				Optional<SupermercadoCategoria> scOptional = supermercado.getSupermercadoCategorias().stream()
						.filter(sc -> sc.getCategoria().equals(categoria)).findFirst();
				if(scOptional.isPresent())
					return scOptional.get();
			}
		}
		
		return null;
	}
}

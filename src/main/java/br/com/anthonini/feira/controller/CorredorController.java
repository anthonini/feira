package br.com.anthonini.feira.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.anthonini.feira.model.Corredor;
import br.com.anthonini.feira.model.Supermercado;
import br.com.anthonini.feira.session.SupermercadoSession;

@Controller
@RequestMapping("/corredor")
public class CorredorController extends AbstractController {
	
	@Autowired
	private SupermercadoSession sessao;
	
	@PutMapping
	public ModelAndView modal(@RequestBody Corredor corredor, ModelMap model, OperacaoDadosSupermercado operacao) {
		ModelAndView mv = new ModelAndView("supermercado/fragments/corredorModal");
		mv.addObject("operacao", operacao.getDescricao());
		mv.addObject("metodo", operacao.getMetodo());
		if(corredor != null) {
			corredor.setNumeroAnterior(corredor.getNumero());
		}
		
		return mv;
	}
	
	@PostMapping("/adicionar")
	public ModelAndView adicionar(@Valid Corredor corredor, BindingResult bindingResult, ModelMap model, String uuid) {
		Supermercado supermercado = sessao.getSupermercado(uuid);
		validarCorredorJaAdicionado(corredor, bindingResult, supermercado);
		if(bindingResult.hasErrors()) {
			addMensagensErroValidacao(model, bindingResult);
		} else {
			supermercado.getCorredores().add(corredor);
			supermercado.getCorredores().sort((Corredor c1, Corredor c2) -> c1.getNumero().compareTo(c2.getNumero()));
			model.addAttribute("corredorAdicionado", true);
			model.addAttribute("corredor", new Corredor());
			addMensagemSucesso(model, "Corredor adicionado com sucesso!");
		}
		
		return modal(null, model, OperacaoDadosSupermercado.ADICIONAR);
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid Corredor corredor, BindingResult bindingResult, ModelMap model, String uuid) {
		Supermercado supermercado = sessao.getSupermercado(uuid);
		validarCorredorJaAdicionado(corredor, bindingResult, supermercado);
		if(bindingResult.hasErrors()) {
			addMensagensErroValidacao(model, bindingResult);
		} else {
			Optional<Corredor> corredorOptional = supermercado.getCorredor(corredor.getNumeroAnterior());
			if(corredorOptional.isPresent()) {
				Corredor corredorAdicionado = corredorOptional.get();
				corredorAdicionado.setNumero(corredor.getNumero());
				corredorAdicionado.setDescricao(corredor.getDescricao());
			} else {
				supermercado.getCorredores().add(corredor);
			}
			supermercado.getCorredores().sort((Corredor c1, Corredor c2) -> c1.getNumero().compareTo(c2.getNumero()));
			model.addAttribute("corredorAlterado", true);
		}
		
		return modal(null, model, OperacaoDadosSupermercado.ALTERAR);
	}
	
	@DeleteMapping("/remover/{numero}")
	public @ResponseBody ResponseEntity<?> remover(@PathVariable Long numero, ModelMap model, String uuid) {
		Supermercado supermercado = sessao.getSupermercado(uuid);
		supermercado.getCorredores().removeIf(c -> c.getNumero().equals(numero));
		supermercado.getSupermercadoCategorias().removeIf(c -> c.getCorredor().getNumero().equals(numero));
		
		return ResponseEntity.ok().build();
	}
	
	private void validarCorredorJaAdicionado(Corredor corredor, BindingResult bindingResult, Supermercado supermercado) {
		Optional<Corredor> corredorOptional = supermercado.getCorredor(corredor.getNumero());
		if(corredorOptional.isPresent() && !corredorOptional.get().getNumero().equals(corredor.getNumeroAnterior())) {
			String message = "Já existe um corredor com número o "+corredor.getNumero()+" adicionado";
			bindingResult.rejectValue("numero", message, message);
		}
	}
}

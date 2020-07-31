package br.com.anthonini.feira.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.anthonini.feira.model.Supermercado;

@Component
public class SupermercadoValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Supermercado.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		Supermercado supermercado = (Supermercado)target;
		validarCorredoresInseridos(errors, supermercado);
		validarCategoriasInseridas(errors, supermercado);
	}

	private void validarCorredoresInseridos(Errors errors, Supermercado supermercado) {
		if(supermercado.getCorredores().isEmpty()) {
			errors.rejectValue("corredores", "", "É obrigatório adicionar pelo menos um corredor");
		}
	}

	private void validarCategoriasInseridas(Errors errors, Supermercado supermercado) {
		if(supermercado.getSupermercadoCategorias().isEmpty()) {
			errors.rejectValue("supermercadoCategorias", "", "É obrigatório adicionar pelo menos uma categoria");
		}
	}
}

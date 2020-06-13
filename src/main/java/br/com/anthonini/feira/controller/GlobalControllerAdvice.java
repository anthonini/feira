package br.com.anthonini.feira.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.anthonini.feira.session.FeiraSession;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	private FeiraSession feiraSession;
	
	@ModelAttribute("quantidadeItensCarrinho")
    public Integer quantidadeItensCarrinho() {
        return feiraSession.getFeira().getQuantidadeItens();
    }
}

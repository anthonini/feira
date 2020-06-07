package br.com.anthonini.feira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.anthonini.feira.model.Feira;

/**
 * @author Anthonini
 */
@Controller
@RequestMapping("/feira")
public class FeiraController {

	@GetMapping
	public String index() {
		return "index";
	}
	
	@GetMapping("/nova")
	public String novo(Feira feira) {
		return "feira/form";
	}
}

package br.com.anthonini.feira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Anthonini
 */
@Controller
@RequestMapping("/compras")
public class ComprasController {

	@GetMapping("/listagem")
	public String listagem() {
		return "compras/listagem";
	}
}

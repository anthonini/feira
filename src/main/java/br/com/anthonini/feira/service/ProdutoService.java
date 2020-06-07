package br.com.anthonini.feira.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import br.com.anthonini.feira.model.Produto;
import br.com.anthonini.feira.repository.ProdutoRepository;
import br.com.anthonini.feira.service.event.produto.ProdutoSalvoEvent;

@Controller
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public void cadastrar(Produto produto) {
		repository.save(produto);
		publisher.publishEvent(new ProdutoSalvoEvent(produto));
	}
	
	public Page<Produto> todos(Pageable pageable) {
		return repository.findAll(pageable);
	}
}

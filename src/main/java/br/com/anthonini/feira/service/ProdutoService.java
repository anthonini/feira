package br.com.anthonini.feira.service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import br.com.anthonini.feira.model.Produto;
import br.com.anthonini.feira.repository.ProdutoRepository;
import br.com.anthonini.feira.service.event.produto.ProdutoRemovidoEvent;
import br.com.anthonini.feira.service.event.produto.ProdutoSalvoEvent;
import br.com.anthonini.feira.service.exception.NaoEPossivelRemoverProdutoException;

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
	
	@Transactional
	public void remover(Produto produto) {
		try {
			repository.delete(produto);
			repository.flush();
			publisher.publishEvent(new ProdutoRemovidoEvent(produto));
		} catch (PersistenceException e) {
			throw new NaoEPossivelRemoverProdutoException("Não é possivel remover o produto. Produto já associado com alguma feira.");
		}
	}
	
	public Page<Produto> todos(Pageable pageable) {
		return repository.findAll(pageable);
	}
}

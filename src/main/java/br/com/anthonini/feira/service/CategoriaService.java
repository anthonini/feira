package br.com.anthonini.feira.service;

import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.anthonini.feira.model.Categoria;
import br.com.anthonini.feira.repository.CategoriaRepository;
import br.com.anthonini.feira.service.exception.NaoEPossivelRemoverEntidadeException;
import br.com.anthonini.feira.service.exception.NomeCategoriaJaExisteException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	@Transactional
	public void salvar(Categoria categoria) {
		Optional<Categoria> categoriaOptional = repository.findByNomeIgnoreCase(categoria.getNome());
		
		if(categoriaOptional.isPresent() && !categoriaOptional.get().equals(categoria)) {
			throw new NomeCategoriaJaExisteException();
		}
		repository.save(categoria);
	}
	
	@Transactional
	public void remover(Categoria categoria) {
		try {
			repository.delete(categoria);
			repository.flush();
		} catch (PersistenceException | DataIntegrityViolationException e) {
			throw new NaoEPossivelRemoverEntidadeException("Não é possivel remover a categoria. Categoria já associada com algum produto ou supermercado.");
		}
	}
}

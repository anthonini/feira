package br.com.anthonini.feira.service;

import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.anthonini.feira.model.Supermercado;
import br.com.anthonini.feira.repository.SupermercadoRepository;
import br.com.anthonini.feira.service.exception.NaoEPossivelRemoverEntidadeException;
import br.com.anthonini.feira.service.exception.NomeSupermercadoJaExisteException;

@Service
public class SupermercadoService {

	@Autowired
	private SupermercadoRepository repository;
	
	@Transactional
	public void salvar(Supermercado supermercado) {
		Optional<Supermercado> supermercadoOptional = repository.findByNomeIgnoreCase(supermercado.getNome());
		
		if(supermercadoOptional.isPresent() && !supermercadoOptional.get().equals(supermercado)) {
			throw new NomeSupermercadoJaExisteException();
		}
		supermercado.getSupermercadoCategorias().forEach(sc -> sc.setSupermercado(supermercado));
		repository.save(supermercado);
	}
	
	@Transactional
	public void remover(Supermercado supermercado) {
		try {
			repository.delete(supermercado);
			repository.flush();
		} catch (PersistenceException | DataIntegrityViolationException e) {
			throw new NaoEPossivelRemoverEntidadeException("Não é possivel remover o supermercado. Supermercado já associada com alguma feira.");
		}
	}
}

package br.com.anthonini.feira.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.anthonini.feira.model.Feira;
import br.com.anthonini.feira.repository.FeiraRepository;

@Service
public class FeiraService {

	@Autowired
	private FeiraRepository feiraRepository;
	
	@Transactional
	public void salvar(Feira feira) {
		if(feira.isVazia()) {
			throw new FeiraVaziaException();
		}
		feiraRepository.save(feira);
	}

	@Transactional
	public void remover(Feira feira) {
		feiraRepository.delete(feira);
	}
}

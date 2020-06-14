package br.com.anthonini.feira.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.anthonini.feira.model.Feira;
import br.com.anthonini.feira.repository.FeiraRepository;
import br.com.anthonini.feira.session.FeiraSession;

@Service
public class FeiraService {

	@Autowired
	private FeiraRepository feiraRepository;
	
	@Autowired
	private FeiraSession feiraSession;
	
	@Transactional
	public void salvar(Feira feira) {
		if(feira.isVazia()) {
			throw new FeiraVaziaException();
		}
		if(feira.isNova()) {
			feira.getItens().forEach(i -> i.setFeira(feira));
		}
		feiraRepository.save(feira);
		feiraRepository.flush();
		
		if(feira.isNova()) {
			feiraSession.setFeira(new Feira());
		}
	}

	@Transactional
	public void remover(Feira feira) {
		feiraRepository.delete(feira);
	}
}

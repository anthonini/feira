package br.com.anthonini.feira.repository.helper.feira;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.anthonini.feira.model.Feira;
import br.com.anthonini.feira.repository.filter.FeiraFilter;

public interface FeiraRepositoryQueries {

	public Page<Feira> filtrar(FeiraFilter filter, Pageable pageable);
	
	public BigDecimal pesoMedio();
	
	public BigDecimal pesoMedioMes();
	
	public BigDecimal valorTotalFeiras();
	
	public BigDecimal valorMedioFeiras();
	
	public BigDecimal valorFeirasAno();
	
	public BigDecimal valorFeirasMes();
}

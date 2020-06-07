package br.com.anthonini.feira.repository.helper.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.anthonini.feira.model.Produto;
import br.com.anthonini.feira.repository.filter.ProdutoFilter;

public interface ProdutoRepositoryQueries {

	public Page<Produto> filtrar(ProdutoFilter filter, Pageable pageable);
}

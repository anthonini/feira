package br.com.anthonini.feira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anthonini.feira.model.Produto;
import br.com.anthonini.feira.repository.helper.produto.ProdutoRepositoryQueries;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

}

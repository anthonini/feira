package br.com.anthonini.feira.repository.helper.produto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.anthonini.feira.dto.ProdutosCadastrados;
import br.com.anthonini.feira.model.Produto;
import br.com.anthonini.feira.repository.PaginationUtil;
import br.com.anthonini.feira.repository.filter.ProdutoFilter;

public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	private PaginationUtil<Produto> paginationUtil;
	
	@Override
	public Page<Produto> filtrar(ProdutoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = builder.createQuery(Produto.class);
		Root<Produto> produto = criteriaQuery.from(Produto.class);
		
		criteriaQuery.where(getWhere(filter, builder, produto));
		
		TypedQuery<Produto> query = paginationUtil.prepare(builder, criteriaQuery, produto, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}
	
	private Long total(ProdutoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Produto> produto = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(builder.count(produto)).where(getWhere(filter, builder, produto));
		
		return manager.createQuery(criteriaQuery).getSingleResult();
	}


	private Predicate[] getWhere(ProdutoFilter filter, CriteriaBuilder builder, Root<Produto> produto) {
		List<Predicate> where = new ArrayList<>();
		
		if(filter != null) {			
			if (!StringUtils.isEmpty(filter.getNome())) {
				where.add(builder.like(builder.upper(produto.get("nome")), "%"+filter.getNome().toUpperCase()+"%"));
			}
		}
		
		return where.stream().toArray(Predicate[]::new);
	}

	@Override
	public ProdutosCadastrados produtosCadastrados() {
		String query = "select new " + ProdutosCadastrados.class.getName() + "(coalesce(sum(preco), 0), count(*)) from Produto";
		return manager.createQuery(query, ProdutosCadastrados.class).getSingleResult();
	}

}

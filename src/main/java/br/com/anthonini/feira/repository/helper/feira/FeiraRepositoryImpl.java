package br.com.anthonini.feira.repository.helper.feira;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.anthonini.feira.model.Feira;
import br.com.anthonini.feira.model.ItemFeira;
import br.com.anthonini.feira.model.Produto;
import br.com.anthonini.feira.repository.PaginationUtil;
import br.com.anthonini.feira.repository.filter.FeiraFilter;

public class FeiraRepositoryImpl implements FeiraRepositoryQueries {

	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	private PaginationUtil<Feira> paginationUtil;
	
	@Override
	public Page<Feira> filtrar(FeiraFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Feira> criteriaQuery = builder.createQuery(Feira.class);
		Root<Feira> feira = criteriaQuery.from(Feira.class);		
		
		criteriaQuery.where(getWhere(filter, builder, feira)).distinct(true);
		
		TypedQuery<Feira> query = paginationUtil.prepare(builder, criteriaQuery, feira, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}
	
	private Long total(FeiraFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Feira> feira = criteriaQuery.from(Feira.class);
		
		criteriaQuery.select(builder.count(feira)).where(getWhere(filter, builder, feira));
		
		return manager.createQuery(criteriaQuery).getSingleResult();
	}


	private Predicate[] getWhere(FeiraFilter filter, CriteriaBuilder builder, Root<Feira> feira) {
		List<Predicate> where = new ArrayList<>();
		
		if(filter != null) {			
			if (!StringUtils.isEmpty(filter.getNome())) {
				where.add(builder.like(builder.upper(feira.get("nome")), "%"+filter.getNome().toUpperCase()+"%"));
			}
			if (!StringUtils.isEmpty(filter.getNomeItem())) {
				Join<Produto, ItemFeira> produto = feira.join("itens", JoinType.INNER).join("produto", JoinType.INNER);
				where.add(builder.like(builder.upper(produto.get("nome")), "%" + filter.getNomeItem().toUpperCase() + "%"));
			}
			
			if (filter.getDe() != null) {
				where.add(builder.greaterThanOrEqualTo(feira.get("dataCompra"), filter.getDe()));
			}
			
			if (filter.getAte() != null) {
				where.add(builder.lessThanOrEqualTo(feira.get("dataCompra"), filter.getAte()));
			}
		}
		
		return where.stream().toArray(Predicate[]::new);
	}

}

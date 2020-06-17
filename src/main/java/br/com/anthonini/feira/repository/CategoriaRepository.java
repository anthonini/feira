package br.com.anthonini.feira.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.anthonini.feira.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	public Optional<Categoria> findByNomeIgnoreCase(String nome);

	public Page<Categoria> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}

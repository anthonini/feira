package br.com.anthonini.feira.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.anthonini.feira.model.Supermercado;

public interface SupermercadoRepository extends JpaRepository<Supermercado, Long> {

	Optional<Supermercado> findByNomeIgnoreCase(String nome);

	Page<Supermercado> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}

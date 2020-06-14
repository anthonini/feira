package br.com.anthonini.feira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anthonini.feira.model.Feira;

@Repository
public interface FeiraRepository extends JpaRepository<Feira, Long> {

}

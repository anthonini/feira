package br.com.anthonini.feira.model;

import java.io.Serializable;

public interface Entidade extends Serializable {

	Long getId();
	
	default boolean isNovo() {
        return getId() == null;
    }
	
	default boolean isNova() {
        return isNovo();
    }
}

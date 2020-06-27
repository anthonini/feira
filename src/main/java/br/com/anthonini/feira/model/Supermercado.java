package br.com.anthonini.feira.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "supermercado")
public class Supermercado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_supermercado")
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@NotEmpty(message = "É obrigatório adicionar pelo menos uma categoria.")
	@OneToMany(mappedBy = "supermercado", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<SupermercadoCategoria> supermercadoCategorias = new ArrayList<>();
	
	

	@Override
	public String toString() {
		return "Supermercado [id=" + id + ", nome=" + nome + "]";
	}

	public boolean isNovo() {
		return id == null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<SupermercadoCategoria> getSupermercadoCategorias() {
		return supermercadoCategorias;
	}

	public void setSupermercadoCategorias(List<SupermercadoCategoria> supermercadoCategorias) {
		this.supermercadoCategorias = supermercadoCategorias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supermercado other = (Supermercado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

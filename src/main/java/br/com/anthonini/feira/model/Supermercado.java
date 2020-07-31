package br.com.anthonini.feira.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "supermercado")
public class Supermercado implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_supermercado")
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@OneToMany(mappedBy = "supermercado", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@OrderBy(value = "numero")
	private List<Corredor> corredores = new ArrayList<>();

	@OneToMany(mappedBy = "supermercado", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@OrderBy(value = "corredor, posicaoCorredor")
	private List<SupermercadoCategoria> supermercadoCategorias = new ArrayList<>();

	@Transient
	private String uuid;
	
	public Optional<Corredor> getCorredor(Long numero) {
		return corredores.stream().filter(c -> c.getNumero().equals(numero)).findFirst();
	}
	
	public void ordernarSupermercadoCategorias() {
		Comparator<SupermercadoCategoria> comparator = Comparator
						.comparing( SupermercadoCategoria::getNumeroCorredor )
						.thenComparing(SupermercadoCategoria::getPosicaoCorredor);
		
		supermercadoCategorias.sort(comparator);
	}
	
	@Override
	public String toString() {
		return "Supermercado [id=" + id + ", nome=" + nome + "]";
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

	public List<Corredor> getCorredores() {
		return corredores;
	}

	public void setCorredores(List<Corredor> corredores) {
		this.corredores = corredores;
	}

	public List<SupermercadoCategoria> getSupermercadoCategorias() {
		return supermercadoCategorias;
	}

	public void setSupermercadoCategorias(List<SupermercadoCategoria> supermercadoCategorias) {
		this.supermercadoCategorias = supermercadoCategorias;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

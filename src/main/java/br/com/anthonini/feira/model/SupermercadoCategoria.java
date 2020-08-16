package br.com.anthonini.feira.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "supermercado_categoria")
public class SupermercadoCategoria implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_supermercado_categoria")
	private Long id;	
	
	@ManyToOne
	@JoinColumn(name = "id_supermercado")
	private Supermercado supermercado;
	
	@NotNull(message = "Categoria é obrigatória")
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;
	
	@NotNull(message = "Corredor é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_corredor")
	private Corredor corredor;
	
	@NotNull(message = "Posição é obrigatória")
	@Column(name = "posicao_corredor")
	private Integer posicaoCorredor;

	@Override
	public String toString() {
		return "SupermercadoCategoria [id=" + id + ", " + (supermercado == null ? "supermercado=null" : supermercado) + ", categoria=" + categoria
				+ ", " + (corredor == null ? "corredor=null" : corredor) + ", posicaoCorredor=" + posicaoCorredor + "]";
	}
	
	public Long getNumeroCorredor() {
		return corredor.getNumero();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Supermercado getSupermercado() {
		return supermercado;
	}

	public void setSupermercado(Supermercado supermercado) {
		this.supermercado = supermercado;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Corredor getCorredor() {
		return corredor;
	}

	public void setCorredor(Corredor corredor) {
		this.corredor = corredor;
	}

	public Integer getPosicaoCorredor() {
		return posicaoCorredor;
	}

	public void setPosicaoCorredor(Integer posicaoCorredor) {
		this.posicaoCorredor = posicaoCorredor;
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
		SupermercadoCategoria other = (SupermercadoCategoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

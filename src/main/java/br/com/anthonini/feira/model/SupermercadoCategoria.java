package br.com.anthonini.feira.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "supermercado_categoria")
public class SupermercadoCategoria implements Serializable {

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
	@Column(name = "corredor")
	private Integer corredor;
	
	@NotNull(message = "Posição no Corredor é obrigatório")
	@Column(name = "posicao_corredor")
	private Integer posicaoCorredor;
	
	@NotNull(message = "Sentido é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "sentido")
	private Sentido sentido;

	@Override
	public String toString() {
		return "SupermercadoCategoria [id=" + id + ", " + (supermercado == null ? "supermercado=null" : supermercado) + ", categoria=" + categoria
				+ ", corredor=" + corredor + ", posicaoCorredor=" + posicaoCorredor + ", sentido=" + sentido + "]";
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

	public Integer getCorredor() {
		return corredor;
	}

	public void setCorredor(Integer corredor) {
		this.corredor = corredor;
	}

	public Integer getPosicaoCorredor() {
		return posicaoCorredor;
	}

	public void setPosicaoCorredor(Integer posicaoCorredor) {
		this.posicaoCorredor = posicaoCorredor;
	}

	public Sentido getSentido() {
		return sentido;
	}

	public void setSentido(Sentido sentido) {
		this.sentido = sentido;
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

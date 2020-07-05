package br.com.anthonini.feira.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="feira")
public class Feira implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_feira")
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotNull(message = "Data da Compra é obrigatório")
	@DateTimeFormat(pattern =  "dd/MM/yyyy")
	@Column(name = "data_compra")
	private LocalDate dataCompra;
	
	@ManyToOne
	@JoinColumn(name = "id_supermercado")
	private Supermercado supermercado;
	
	@OneToMany(mappedBy = "feira", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemFeira> itens = new ArrayList<>();
	
	@Transient
	private BigDecimal valor;
	
	public boolean isNova() {
		return id == null;
	}
	
	public boolean isVazia() {
		return itens.isEmpty();
	}
	
	public Integer getQuantidadeItens() {
		return itens.size();
	}
	
	public BigDecimal getPesoTotal() {
		 return itens.stream()
				.map(ItemFeira::getPeso)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
	public String getDescricaoPesoTotal() {
		return UnidadePeso.QUILOGRAMA.getDescricaoAbreviada(getPesoTotal());
	}
	
	public BigDecimal getValorTotal() {
		 return itens.stream()
				.map(ItemFeira::getValorTotal)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
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

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Supermercado getSupermercado() {
		return supermercado;
	}

	public void setSupermercado(Supermercado supermercado) {
		this.supermercado = supermercado;
	}

	public List<ItemFeira> getItens() {
		return itens;
	}

	public void setItens(List<ItemFeira> itens) {
		this.itens = itens;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
		Feira other = (Feira) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

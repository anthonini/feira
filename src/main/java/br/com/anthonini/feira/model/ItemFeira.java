package br.com.anthonini.feira.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name="item_feira")
public class ItemFeira implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_item_feira")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "id_feira")
	private Feira feira;
	
	@NotNull(message = "Quantidade é obrigatório")
	private Integer quantidade = 0;
	
	@DecimalMax(value = "9999999.99", message = "Preço deve ser menor ou igual a R$9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	@Column(name="preco_compra")
	private BigDecimal precoCompra;
	
	@NotNull(message = "Peso é obrigatório")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal peso = BigDecimal.ZERO;
	
	@Transient
	private boolean porPeso;
	
	@PostLoad
	private void inicializarPorPeso() {
		porPeso = produto.isCobradoPorKG();
	}
	
	public BigDecimal getValorTotal() {
		if(produto == null) {
			return BigDecimal.ZERO;
		}
		
		BigDecimal preco = precoCompra != null ? precoCompra : BigDecimal.ZERO;
		if(porPeso || produto.isCobradoPorKG())
			return preco.multiply(peso);
		else
			return preco.multiply(BigDecimal.valueOf(quantidade));
	}
	
	public String getDescricaoPeso() {
		if(peso.compareTo(BigDecimal.valueOf(1)) < 0) {
			return UnidadePeso.GRAMA.getDescricaoAbreviada(UnidadePeso.QUILOGRAMA.converter(peso, UnidadePeso.GRAMA));
		}
		return UnidadePeso.QUILOGRAMA.getDescricaoAbreviada(peso);
	}
	
	public String getDescricaoPesoOuQuantidade() {
		if(porPeso) {
			return String.format("Peso (%s)", UnidadePeso.QUILOGRAMA.getSimbolo());
		} else {
			return "Qtd";
		}
	}
	
	public void calculcarPeso() {
		if(produto.getCobradoPor().equals(CobradoPor.UNIDADE) || (produto.isCobradoPorKG() && !porPeso)) {
			peso = produto.getUnidadePeso().converter(produto.getPesoUnidade(), UnidadePeso.QUILOGRAMA);
			peso = peso.multiply(BigDecimal.valueOf(quantidade));
		}
	}

	public ItemFeira() {
	}

	public ItemFeira(Produto produto, Feira feira) {
		this.produto = produto;
		this.feira = feira;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Feira getFeira() {
		return feira;
	}

	public void setFeira(Feira feira) {
		this.feira = feira;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(BigDecimal precoCompra) {
		this.precoCompra = precoCompra;
	}

	public boolean isPorPeso() {
		return porPeso;
	}

	public void setPorPeso(boolean porPeso) {
		this.porPeso = porPeso;
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
		ItemFeira other = (ItemFeira) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

package br.com.anthonini.feira.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

import br.com.anthonini.feira.repository.listener.ProdutoEntityListener;

@EntityListeners(ProdutoEntityListener.class)
@Entity
@Table(name="produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_produto")
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotNull(message = "Cobrado por é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "cobrado_por")
	private CobradoPor cobradoPor;
	
	@NotNull(message = "Preço é obrigatório")
	@DecimalMax(value = "9999999.99", message = "Preço deve ser menor ou igual a R$9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal preco;
	
	@NotNull(message = "Unidade do peso é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "unidade_peso")
	private UnidadePeso unidadePeso;
	
	@NotNull(message = "O peso por unidade é obrigatório")
	@DecimalMax(value = "9999.000", message = "O peso por unidade deve ser menor ou igual a 9.999,000")
	@NumberFormat(pattern = "#,##0.000")
	@Column(name = "peso_unidade", precision = 19, scale = 3)
	private BigDecimal pesoUnidade;
	
	private String foto;
	
	@Column(name = "content_type")
	private String contentType;
	
	@Transient
	private String urlFoto;
	
	@Transient
	private String urlThumbnailFoto;
	
	@Transient
	private String fotoOriginal;
	
	public String getDescricaoPeso() {
		return this.unidadePeso.getDescricaoAbreviada(pesoUnidade);
	}
	
	public boolean isTemFoto() {
		return foto != null && !foto.isEmpty();
	}
	
	public boolean isCobradoPorKG() {
		return this.cobradoPor.equals(CobradoPor.KG);
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

	public CobradoPor getCobradoPor() {
		return cobradoPor;
	}

	public void setCobradoPor(CobradoPor cobradoPor) {
		this.cobradoPor = cobradoPor;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public UnidadePeso getUnidadePeso() {
		return unidadePeso;
	}

	public void setUnidadePeso(UnidadePeso unidadePeso) {
		this.unidadePeso = unidadePeso;
	}

	public BigDecimal getPesoUnidade() {
		return pesoUnidade;
	}

	public void setPesoUnidade(BigDecimal pesoUnidade) {
		this.pesoUnidade = pesoUnidade;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public String getUrlThumbnailFoto() {
		return urlThumbnailFoto;
	}

	public void setUrlThumbnailFoto(String urlThumbnailFoto) {
		this.urlThumbnailFoto = urlThumbnailFoto;
	}

	public String getFotoOriginal() {
		return fotoOriginal;
	}

	public void setFotoOriginal(String fotoOriginal) {
		this.fotoOriginal = fotoOriginal;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

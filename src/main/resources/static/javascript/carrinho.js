var Feira = Feira || {};

Feira.Carrinho = (function(){
	
	function Carrinho() {
		this.inputComprasQuantidadeItem = $('.js-compras-quantidade-item');
		this.inputComprasPesoItem = $('.js-compras-peso-item');
	}
	
	Carrinho.prototype.iniciar = function() {
		this.inputComprasQuantidadeItem.on('feira.carrinho.item-atualizado', onItemCarrinhoAtualizado);
		this.inputComprasPesoItem.on('feira.carrinho.item-atualizado', onItemCarrinhoAtualizado);
	}
	
	function onItemCarrinhoAtualizado(event, itemFeiraDTO) {		
		var idProduto = $(event.currentTarget).data('produto-id');
		
		$('#peso-produto-'+idProduto).html(itemFeiraDTO.peso);
		$('#valor-total-produto-'+idProduto).html(Feira.formatarMoeda(itemFeiraDTO.valorTotal));
		
		$('#feira-quantidade-itens').html(itemFeiraDTO.feiraDTO.quantidadeItens);
		$('#feira-peso-total').html(Feira.formatarPeso(itemFeiraDTO.feiraDTO.pesoTotal)+'Kg');
		$('#feira-valor-total').html('R$ ' + Feira.formatarMoeda(itemFeiraDTO.feiraDTO.valorTotal));
	}
	
	return Carrinho;
}());

$(function(){
	var carrinho = new Feira.Carrinho();
	carrinho.iniciar();
});
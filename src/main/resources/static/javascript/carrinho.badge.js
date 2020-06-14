var Feira = Feira || {};

Feira.BadgeCarrinho = (function() {
	
	function BadgeCarrinho() {
		this.badgeCarrinho = $('#badge-carrinho');
		this.inputComprasPesoItem = $('.js-compras-peso-item');
		this.inputComprasQuantidadeItem = $('.js-compras-quantidade-item');
	}
	
	BadgeCarrinho.prototype.iniciar = function() {
		this.inputComprasQuantidadeItem.on('feira.carrinho.item-atualizado', onItemCarrinhoAtualizado.bind(this));
		this.inputComprasPesoItem.on('feira.carrinho.item-atualizado', onItemCarrinhoAtualizado.bind(this));
	}
	
	function onItemCarrinhoAtualizado(event, itemFeiraDTO) {
		var quantidadeItens = itemFeiraDTO.feiraDTO.quantidadeItens;
		
		if(quantidadeItens > 0){
			this.badgeCarrinho.show();
		} else {
			this.badgeCarrinho.hide();
		}
		this.badgeCarrinho.html(quantidadeItens);
	}
	
	return BadgeCarrinho;
}());

$(function() {
	var badgeCarrinho = new Feira.BadgeCarrinho();
	badgeCarrinho.iniciar();
});
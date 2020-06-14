var Feira = Feira || {};

Feira.Carrinho = (function(){
	
	function Carrinho() {
		this.inputComprasQuantidadeItem = $('.js-compras-quantidade-item');
		this.inputComprasPesoItem = $('.js-compras-peso-item');
		this.removerItem = $('.js-remover-item');
	}
	
	Carrinho.prototype.iniciar = function() {
		this.inputComprasQuantidadeItem.on('feira.carrinho.item-atualizado', onItemCarrinhoAtualizado);
		this.inputComprasPesoItem.on('feira.carrinho.item-atualizado', onItemCarrinhoAtualizado);
		this.removerItem.on('click', onRemoverItemClicked);
	}
	
	function onItemCarrinhoAtualizado(event, itemFeiraDTO) {		
		var idProduto = $(event.currentTarget).data('produto-id');
		
		$('#peso-produto-'+idProduto).html(itemFeiraDTO.peso);
		$('#valor-total-produto-'+idProduto).html(Feira.formatarMoeda(itemFeiraDTO.valorTotal));
		
		atualizarTotais(itemFeiraDTO.feiraDTO);		
	}
	
	function atualizarTotais(feiraDTO) {
		$('#feira-quantidade-itens').html(feiraDTO.quantidadeItens);
		$('#feira-peso-total').html(Feira.formatarPeso(feiraDTO.pesoTotal)+'Kg');
		$('#feira-valor-total').html('R$ ' + Feira.formatarMoeda(feiraDTO.valorTotal));
	}
	
	function onRemoverItemClicked(event) {
		event.preventDefault();
		var produtoId = $(this).data('produto-id');
		
		var response = $.ajax({
			url: '/carrinho/'+produtoId,
			method: 'DELETE'
		});
		
		response.done(onItemRemovido.bind(this,produtoId));
	}
	
	function onItemRemovido(produtoId, feiraDTO) {
		$('#div-item-'+produtoId).remove();
		atualizarTotais(feiraDTO);
	}
	
	return Carrinho;
}());

$(function(){
	var carrinho = new Feira.Carrinho();
	carrinho.iniciar();
});
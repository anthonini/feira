var Feira = Feira || {};

Feira.MiniCarrinho = (function(){
	
	function MiniCarrinho() {
		this.miniCarrinho = $('.js-mini-carrinho');
		this.itensMiniCarrinho = $('.js-itens-mini-carrinho');
	}
	
	MiniCarrinho.prototype.iniciar = function() {
		this.miniCarrinho.on('click', onMiniCarrinhoClicked.bind(this));
	}
	
	function onMiniCarrinhoClicked() {
		var response = 
		$.ajax({
			url: '/carrinho/itens',
			method: 'PUT'
		});
		
		response.done(onItensMiniCarrinhoRecuperado.bind(this));
	}
	
	function onItensMiniCarrinhoRecuperado(html) {
		this.itensMiniCarrinho.html(html)
	}
	
	return MiniCarrinho;
}());

$(function() {
	var miniCarrinho = new Feira.MiniCarrinho();
	miniCarrinho.iniciar();
});
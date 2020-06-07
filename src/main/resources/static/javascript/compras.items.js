Feira = Feira || {};

Feira.Items = (function() {
	function Items () {
		this.inputQuantidadeItem = $('.js-listagem-quantidade-item');
	}
	
	Items.prototype.iniciar = function() {
		this.inputQuantidadeItem.mask('#0', {reverse: true});
		this.inputQuantidadeItem.on('keypress', onInputQuantidadeItemKeyPress);
	}
	
	function onInputQuantidadeItemKeyPress() {
		var peso = $(this).closest('.js-peso-unidade').val();
		console.log('peso', peso);
	}
	
	return Items;
}());

$(function(){
	var items = new Feira.Items();
	items.iniciar();
});
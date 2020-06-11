Feira = Feira || {};

Feira.Items = (function() {
	function Items () {
		this.inputQuantidadeItem = $('.js-listagem-quantidade-item');
	}
	
	Items.prototype.iniciar = function() {
		this.inputQuantidadeItem.on('keypress', onInputQuantidadeItemKeyPress);
		var options =  {
			onKeyPress: function(peso, e, field, options) {
				var tamanho = peso.indexOf(',') > -1 && peso.indexOf(',') < 4 ? 5 : 4;
				if(peso.length > tamanho) {
					field.val(peso.substring(0,tamanho));
				}
			}
		};
		$('.js-peso').mask('0999,999', options);
	}
	
	function onInputQuantidadeItemKeyPress() {
		
	}
	
	return Items;
}());

$(function(){
	var items = new Feira.Items();
	items.iniciar();
});
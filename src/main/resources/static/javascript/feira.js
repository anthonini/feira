var Feira = Feira || {};

Feira.MascaraNumeros = (function(){
	function MascaraNumeros() {
		this.preco = $('.js-preco');		
		this.numero = $('.js-numero');
		this.peso = $('.js-peso');
	}
	
	MascaraNumeros.prototype.habilitar = function() {
		this.preco.mask('#.##0,00', {reverse: true});
		this.numero.mask('000.000.000.000.000', {reverse: true});
		this.peso.mask('#.##0', {reverse: true});
	}
	
	return MascaraNumeros;
})();

$(function(){	
	var mascaraNumeros = new Feira.MascaraNumeros();
	mascaraNumeros.habilitar();
});
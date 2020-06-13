var Feira = Feira || {};

Feira.MascaraNumeros = (function(){
	function MascaraNumeros() {
		this.preco = $('.js-preco');		
		this.numero = $('.js-numero');
		this.peso = $('.js-peso');
	}
	
	MascaraNumeros.prototype.habilitar = function() {
		this.preco.mask('#.##0,00', {reverse: true});
		this.numero.mask('#0', {reverse: true});
		this.peso.mask('#.##0,000', {reverse: true});
	}
	
	return MascaraNumeros;
})();

numeral.language('pt-br');

Feira.formatarMoeda = function(valor) {	
	return numeral(valor).format('0,0.00');
}

Feira.formatarPeso = function(valor) {	
	return numeral(valor).format('0,0.000');
}

Feira.removerFormatoPeso = function(valorFormatado) {
	return numeral().unformat(valorFormatado);
}

Feira.formatarBigDecimal = function(valor) {	
	return numeral(valor).format('#0.000').replace(',', '.');
}

$(function(){	
	var mascaraNumeros = new Feira.MascaraNumeros();
	mascaraNumeros.habilitar();
});
var Feira = Feira || {};

Feira.Peso = (function(){
	function Peso() {
		this.peso = $('#pesoUnidade');
		this.unidadePeso = $('#unidadePeso');
	}
	
	Peso.prototype.iniciar = function() {
		this.unidadePeso.on('change', onPesoUnidadeAlterado.bind(this));
		alterarUnidadePeso.call(this);
	}
	
	function onPesoUnidadeAlterado() {
		alterarUnidadePeso.call(this);
	}
	
	function alterarUnidadePeso() {
		if(this.unidadePeso.val() && this.unidadePeso.val() !== 'GRAMA') {
			this.peso.mask('#.##0,000', {reverse: true} );
		} else {
			this.peso.mask('#.##0', {reverse: true});
		}
	}
	
	return Peso;
}());

$(function(){
	var peso = new Feira.Peso();
	peso.iniciar();
})
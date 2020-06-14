var Feira = Feira || {};

Feira.Peso = (function(){
	function Peso() {
		this.peso = $('#pesoUnidade');
		this.unidadePeso = $('#unidadePeso');
	}
	
	Peso.prototype.iniciar = function() {
		this.unidadePeso.on('change', onPesoUnidadeAlterado.bind(this));
		if(this.unidadePeso.val() === 'GRAMA')
			alterarUnidadePesoParaGrama.call(this);
	}
	
	function onPesoUnidadeAlterado() {
		alterarUnidadePeso.call(this);
	}
	
	function alterarUnidadePeso() {
		if(this.unidadePeso.val() && this.unidadePeso.val() !== 'GRAMA') {
			var peso = this.peso.cleanVal();
			this.peso.mask('#.##0,000', {reverse: true} );
			this.peso.val(peso);
		} else {
			alterarUnidadePesoParaGrama.call(this);
		}
	}
	
	function alterarUnidadePesoParaGrama() {
		var peso = this.peso.val().split(',')[0];
		this.peso.val(peso);
		this.peso.mask('#.##0', {reverse: true});
	}
	
	return Peso;
}());

$(function(){
	var peso = new Feira.Peso();
	peso.iniciar();
})
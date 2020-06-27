var Feira = Feira || {};

Feira.SupermercadoCategoria = (function() {
	function SupermercadoCategoria() {
		this.categoriaModal = $('#categoriaModal');
		this.form = $('form[name="form-supermercado-categoria"]');		
		this.url = this.form.attr('action');		
	}
	
	SupermercadoCategoria.prototype.iniciar = function() {
		this.form.submit(onFormSubmitted.bind(this));
	}
	
	function onFormSubmitted(event) {
		event.preventDefault();
		var response = $.ajax({
			url: this.url,
			method: 'POST',
	        contentType: 'application/x-www-form-urlencoded',
	        data : this.form.serialize()
		 });
		
		response.done(onFormSubmittedResponse.bind(this));
	}
	
	function onFormSubmittedResponse(html) {
		this.categoriaModal.html(html);
	}
	
	return SupermercadoCategoria;
}());

$(function() {
	var supermercadoCategoria = new Feira.SupermercadoCategoria();
	supermercadoCategoria.iniciar();
});
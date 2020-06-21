var Feira = Feira || {};

Feira.AdicionarCategoria = (function() {
	function AdicionarCategoria() {
		this.adicionarCategoriaModal = $('#adicionarCategoriaModal');
		this.form = $('form[name="form-adicionar-categoria"]');		
		this.url = this.form.attr('action');		
		this.submitBtn = $('.js-adicionar-categoria-submit-btn');
	}
	
	AdicionarCategoria.prototype.iniciar = function() {
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
		this.adicionarCategoriaModal.html(html);
	}
	
	return AdicionarCategoria;
}());

$(function() {
	var adicionarCategoria = new Feira.AdicionarCategoria();
	adicionarCategoria.iniciar();
});
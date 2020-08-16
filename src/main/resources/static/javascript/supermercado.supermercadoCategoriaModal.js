var Feira = Feira || {};

Feira.CategoriaModal = (function() {
	function CategoriaModal() {
		this.modal = $('#categoriaModal');
		this.form = $('form[name="form-supermercado-categoria"]');		
		this.url = this.form.attr('action');
		this.uuid = $('#uuid').val();
	}
	
	CategoriaModal.prototype.iniciar = function() {
		this.form.submit(onFormSubmitted.bind(this));
	}
	
	function onFormSubmitted(event) {
		event.preventDefault();
		var response = $.ajax({
			url: this.url + '?uuid=' + this.uuid,
			method: 'POST',
	        contentType: 'application/x-www-form-urlencoded',
	        data : this.form.serialize()
		 });
		
		response.done(onFormSubmittedResponse.bind(this));
	}
	
	function onFormSubmittedResponse(html) {
		this.modal.html(html);
	}
	
	return CategoriaModal;
}());

$(function() {
	var categoriaModal = new Feira.CategoriaModal();
	categoriaModal.iniciar();
});
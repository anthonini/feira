var Feira = Feira || {};

Feira.CorredorModal = (function() {
	function CorredorModal() {
		this.modal = $('#corredorModal');
		this.form = $('form[name="form-corredor"]');		
		this.url = this.form.attr('action');
		this.uuid = $('#uuid').val();
	}
	
	CorredorModal.prototype.iniciar = function() {
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
	
	return CorredorModal;
}());

$(function() {
	var corredorModal = new Feira.CorredorModal();
	corredorModal.iniciar();
});
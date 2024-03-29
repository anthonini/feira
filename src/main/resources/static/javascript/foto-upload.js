var Arquitetura = Arquitetura || {};

Arquitetura.UploadFoto = (function() {
	
	function UploadFoto() {
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.inputUrlFoto = $('input[name=urlFoto]');
		
		this.htmlFotoTemplate = $('#foto-template').html();
		this.template = Handlebars.compile(this.htmlFotoTemplate);
		
		this.uploadDrop = $('#upload-drop');
		this.uploadSelect = $('#upload-select')
		this.fotoContainer = $('.js-foto-container');
		
		this.hiddenClass = 'd-none';
	}
	
	UploadFoto.prototype.iniciar = function() {
		var settings = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				action: this.fotoContainer.data('url-fotos'),
				complete: onUploadComplete.bind(this)
		}
		
		UIkit.uploadSelect(this.uploadSelect,settings);
		UIkit.uploadDrop(this.uploadDrop,settings);
		
		if(this.inputNomeFoto.val()) {
			onUploadComplete.call(this, {nome: this.inputNomeFoto.val(), contentType: this.inputContentType.val(), urlFoto: this.inputUrlFoto.val() });
		}
	}
	
	function onUploadComplete(resposta) {
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);
		this.inputUrlFoto.val(resposta.urlFoto);
		
		this.uploadDrop.addClass(this.hiddenClass);
		var htmlFoto = this.template({urlFoto: resposta.urlFoto});
		this.fotoContainer.append(htmlFoto);
		
		$('.js-remove-foto').on('click', onRemoverFoto.bind(this));
	}
	
	function onRemoverFoto() {
		$('.js-foto').remove();
		this.uploadDrop.removeClass(this.hiddenClass);
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
	}
	
	return UploadFoto;
	
}());

$(function() {
	var uploadFoto = new Arquitetura.UploadFoto();
	uploadFoto.iniciar();
})
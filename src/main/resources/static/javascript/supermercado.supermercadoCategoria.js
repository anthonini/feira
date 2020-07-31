var Feira = Feira || {};

Feira.Categoria = (function() {
	function Categoria() {
		this.categoriaModal = $('#categoriaModal');
		this.uuid = $('#uuid').val();
	}
	
	Categoria.prototype.iniciar = function() {
		this.categoriaModal.on('show.bs.modal', onShowCategoriaModal.bind(this));
		this.categoriaModal.on('categorias-atualizadas', onCategoriasAtualizadas.bind(this));
		this.categoriaModal.on('categoria-alterada', onCategoriaAlterada.bind(this));
		bindBtn.call(this);
	}
	
	function onShowCategoriaModal(event) {
		var operacao = $(event.relatedTarget).data('operacao') || event.relatedTarget.data['operacao'];
		var url = this.categoriaModal.data('url') + '?operacao=' + operacao + '&uuid=' + this.uuid;
		var response = $.ajax({
			url: url,
			contentType: 'application/json',
			method: 'PUT',
			data: JSON.stringify(event.relatedTarget)
		});
		
		response.done(onCategoriaModalResponse.bind(this));
	}
	
	function onCategoriaModalResponse(html) {
		this.categoriaModal.html(html);
	}
	
	function onCategoriasAtualizadas(event) {
		var response = $.ajax({
			url: 'categorias?uuid='+this.uuid,
			contentType: 'application/json',
			method: 'GET'
		});
		
		response.done(onCategoriasAtualizadasResponse.bind(this));
	}

	function onCategoriasAtualizadasResponse(html) {
		$('#categorias-adicionadas').html(html);
		bindBtn.call(this);
	}
	
	function bindBtn() {
		this.alterarCategoriaBtn = $('.js-alterar-categoria');
		this.alterarCategoriaBtn.off('click');
		this.alterarCategoriaBtn.on('click', onAlterarCategoriaBtnClicked.bind(this));
		
		this.removerCategoriaBtn = $('.js-remover-categoria');
		this.removerCategoriaBtn.off('click');
		this.removerCategoriaBtn.on('click', onRemoverCategoriaBtnClicked.bind(this));
	}
	
	function onAlterarCategoriaBtnClicked(event) {
		event.preventDefault();
		var btn = $(event.currentTarget);
		var supermercadoCategoria = {
			id: btn.data('id'),
			categoria: {
				id: btn.data('categoria-id')
			},
			data: {
				operacao: btn.data('operacao')
			}
		}
		this.categoriaModal.modal({show: true}, supermercadoCategoria);
	}
	
	function onCategoriaAlterada() {
		bindBtn.call(this);
		this.categoriaModal.modal('hide');
		swal('Categoria alterada com sucesso!', '', 'success');
	}
	
	function onRemoverCategoriaBtnClicked(event) {
		event.preventDefault();
		var btn = $(event.currentTarget);
		var url = btn.data('url');
		var response = $.ajax({
			url: url + '?uuid=' + this.uuid,
			method: 'DELETE'
		});
		
		response.done(onRemoverCategoriaResponse.bind(this));
	}
	
	function onRemoverCategoriaResponse(html) {
		onCategoriasAtualizadasResponse.call(this,html);
	}
	
	return Categoria;
}());

$(function() {
	var categoria = new Feira.Categoria();
	categoria.iniciar();
});
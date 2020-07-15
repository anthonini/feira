var Feira = Feira || {};

Feira.Supermercado = (function() {
	
	function Supermercado() {
		this.categoriaModal = $('#categoriaModal');
		this.tbody = $('.js-tabela_supermercado-categorias').find('tbody');
	}
	
	Supermercado.prototype.iniciar = function() {
		this.categoriaModal.on('show.bs.modal', onShowCategoriaModal.bind(this));
		this.categoriaModal.on('categoria-adicionada', onCategoriaAdicionada.bind(this));
		this.categoriaModal.on('categoria-alterada', onCategoriaAlterada.bind(this));
		iniciarObjetosSupermercadoCategorias.call(this);
	}
	
	function onShowCategoriaModal(event) {
		var operacao = $(event.relatedTarget).data('operacao') || event.relatedTarget.data['operacao'];
		var categoriasAdicionadas = $('input[name*="].categoria.id"]').map(function(){return $(this).val();}).get();
		var url = this.categoriaModal.data('url') + '?operacao=' + operacao + '&categoriasAdicionadas=' + categoriasAdicionadas;
		var response = $.ajax({
			url: url,
			contentType: 'application/json',
			method: 'PUT',
			data: JSON.stringify(event.relatedTarget)
		});
		
		response.done(onCategoriaResponse.bind(this));
	}
	
	function onCategoriaResponse(html) {
		this.categoriaModal.html(html);
	}
	
	function onCategoriaAdicionada(event, supermercadoCategoriaAdicionada) {
		this.linhaNenhumaCategoriaAdicionada.remove();
		var idx = this.tbody.children().length;
		var tr = gerarLinhaCategoria(supermercadoCategoriaAdicionada, idx);
		this.tbody.append(tr);
		iniciarObjetosSupermercadoCategorias.call(this);
	}
	
	function onCategoriaAlterada(event, supermercadoCategoria, acao) {
		var tr = $('input[name*="].categoria.id"][value='+supermercadoCategoria.categoria.id+']').parent().parent();
		var idx = tr.index();
		var novaTr = gerarLinhaCategoria(supermercadoCategoria, idx);
		tr.html(novaTr.html());
		iniciarObjetosSupermercadoCategorias.call(this);
		this.categoriaModal.modal('hide');
		swal('Categoria alterada com sucesso!', '', 'success');
	}
	
	function iniciarObjetosSupermercadoCategorias() {
		this.linhaNenhumaCategoriaAdicionada = $('.js-linha-nenhuma-categoria-adicionada');
		this.removerCategoriaBtn = $('.js-remover-categoria');
		this.alterarCategoriaBtn = $('.js-alterar-categoria');
		bindBtnAcoes.call(this);
	}
	
	function bindBtnAcoes() {
		this.removerCategoriaBtn.off('click');
		this.removerCategoriaBtn.on('click', onRemoverCategoriaBtnClicked.bind(this));
		this.alterarCategoriaBtn.off('click');
		this.alterarCategoriaBtn.on('click', onAlterarCategoriaBtnClicked.bind(this));
	}
	
	function onRemoverCategoriaBtnClicked(event) {
		event.preventDefault();
		$(event.target).parents('tr').remove();
				
		var quantidadeCategorias = this.tbody.children().length;
		
		if(quantidadeCategorias <= 0) {
			this.tbody.append(
				$('<tr>').attr('class', 'js-linha-nenhuma-categoria-adicionada')
					.append($('<td>').attr('class', 'text-center').attr('colspan', '5').text('Nenhuma categoria adicionada.')));
			this.linhaNenhumaCategoriaAdicionada = $('.js-linha-nenhuma-categoria-adicionada');
		} else {
			reorganizarIdENameCategorias.call(this);
		}
	}
	
	function onAlterarCategoriaBtnClicked(event) {
		event.preventDefault();
		var idx = $(event.currentTarget).data('idx');
		var categoria = {
			id: $('input[name="'+getName(idx,'id')+'"]').val(),
			supermercado: {
				id: $('input[name="'+getName(idx,'supermercado.id')+'"]').val()
			},
			categoria: {
				id: $('input[name="'+getName(idx,'categoria.id')+'"]').val(),
				nome: $('input[name="'+getName(idx,'categoria.nome')+'"]').val()
			},
			corredor: $('input[name="'+getName(idx,'corredor')+'"]').val(),
			posicaoCorredor: $('input[name="'+getName(idx,'posicaoCorredor')+'"]').val(),
			data: {
				operacao: $(event.currentTarget).data('operacao')
			}
		}
		this.categoriaModal.modal({show: true}, categoria);
	}
	
	function getId(idx,obj) {
		return 'supermercadoCategorias'+idx+'.'+obj;
	}
	
	function getName(idx, obj) {
		return 'supermercadoCategorias['+idx+'].'+obj;
	}
	
	function reorganizarIdENameCategorias() {
		var trs = this.tbody.find('tr');
		trs.each(function( idx ) {
			$(this).find('input').each(function() {
				$(this).attr('id', $(this).attr('id').replace(/[0-9]/g, idx));
				$(this).attr('name', $(this).attr('name').replace(/[0-9]/g, idx));
			});
		});
	}
	
	function gerarLinhaCategoria(supermercadoCategoria, idx) {	
		var tr = $('<tr>')
			.append($('<td>').text(supermercadoCategoria.categoria.nome))
			.append($('<td>').text(supermercadoCategoria.corredor))
			.append($('<td>').text(supermercadoCategoria.posicaoCorredor))
			.append($('<td>')
				.append($('<a>').attr('class', 'btn btn-sm text-primary feira-item-col-acao js-alterar-categoria').attr('title', 'Alterar').attr('href', '#').attr('data-idx', idx).attr('data-operacao', 'ALTERAR')
						.append($('<i>').attr('class', 'fa fa-edit')))
				.append($('<a>').attr('class', 'btn btn-sm text-danger feira-item-col-acao js-remover-categoria').attr('title', 'Remover').attr('href', '#')
						.append($('<i>').attr('class', 'fa fa-trash-alt')))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'id')				).attr('name', getName(idx,'id')			 ).attr('value', supermercadoCategoria.id))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'supermercado.id')	).attr('name', getName(idx,'supermercado.id')).attr('value', supermercadoCategoria.supermercado?.id))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'categoria.id')		).attr('name', getName(idx,'categoria.id')	 ).attr('value', supermercadoCategoria.categoria.id))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'categoria.nome')	).attr('name', getName(idx,'categoria.nome') ).attr('value', supermercadoCategoria.categoria.nome))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'corredor')			).attr('name', getName(idx,'corredor')		 ).attr('value', supermercadoCategoria.corredor))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'posicaoCorredor')	).attr('name', getName(idx,'posicaoCorredor')).attr('value', supermercadoCategoria.posicaoCorredor))
			)
			
		return tr;
	}
	
	return Supermercado;
}());

$(function(){
	var supermercado = new Feira.Supermercado();
	supermercado.iniciar();
})
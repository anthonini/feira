var Feira = Feira || {};

Feira.Supermercado = (function() {
	
	function Supermercado () {
		this.adicionarCategoriaModal = $('#adicionarCategoriaModal');
		this.url = this.adicionarCategoriaModal.data('url');
		this.tbody = $('.js-tabela_supermercado-categorias').find('tbody');
	}
	
	Supermercado.prototype.iniciar = function() {
		this.adicionarCategoriaModal.on('show.bs.modal', onShowAdicionarCategoriaModal.bind(this));
		this.adicionarCategoriaModal.on('categoria-adicionada', onCategoriaAdicionada.bind(this));
		iniciarObjetosSupermercadoCategorias.call(this);
	}
	
	function onShowAdicionarCategoriaModal() {
		var response = $.ajax({
			url: this.url,
			method: 'PUT'
		});
		
		response.done(onAdicionarCategoriaResponse.bind(this));
	}
	
	function onAdicionarCategoriaResponse(html) {
		this.adicionarCategoriaModal.html(html);
	}
	
	function onCategoriaAdicionada(event, supermercadoCategoriaAdicionada) {
		this.linhaNenhumaCategoriaAdicionada.remove();
		var idx = this.tbody.children().length;
		
		var tr = $('<tr>')
			.append($('<td>').text(supermercadoCategoriaAdicionada.categoria.nome))
			.append($('<td>').text(supermercadoCategoriaAdicionada.corredor))
			.append($('<td>').text(supermercadoCategoriaAdicionada.posicaoCorredor))
			.append($('<td>').text(supermercadoCategoriaAdicionada.descricaoSentido))
			.append($('<td>')
				.append($('<a>').attr('class', 'btn btn-sm text-primary feira-item-col-acao').attr('title', 'Alterar').attr('href', '#')
						.append($('<i>').attr('class', 'fa fa-edit')))
				.append($('<a>').attr('class', 'btn btn-sm text-danger feira-item-col-acao js-remover-categoria').attr('title', 'Remover').attr('href', '#')
						.append($('<i>').attr('class', 'fa fa-trash-alt')))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'id')				).attr('name', getName(idx,'id')			 ).attr('value', supermercadoCategoriaAdicionada.id))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'supermercado.id')	).attr('name', getName(idx,'supermercado.id')).attr('value', supermercadoCategoriaAdicionada.supermercado?.id))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'categoria.id')		).attr('name', getName(idx,'categoria.id')	 ).attr('value', supermercadoCategoriaAdicionada.categoria.id))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'categoria.nome')	).attr('name', getName(idx,'categoria.nome') ).attr('value', supermercadoCategoriaAdicionada.categoria.nome))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'corredor')			).attr('name', getName(idx,'corredor')		 ).attr('value', supermercadoCategoriaAdicionada.corredor))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'posicaoCorredor')	).attr('name', getName(idx,'posicaoCorredor')).attr('value', supermercadoCategoriaAdicionada.posicaoCorredor))
				.append($('<input>').attr('type','hidden').attr('id', getId(idx,'sentido')			).attr('name', getName(idx,'sentido')		 ).attr('value', supermercadoCategoriaAdicionada.sentido))
			)
		this.tbody.append(tr);
		iniciarObjetosSupermercadoCategorias.call(this);
	}
	
	function iniciarObjetosSupermercadoCategorias() {
		this.linhaNenhumaCategoriaAdicionada = $('.js-linha-nenhuma-categoria-adicionada');
		this.removerCategoriaBtn = $('.js-remover-categoria');
		bindBtnAcoes.call(this);
	}
	
	function bindBtnAcoes() {
		this.removerCategoriaBtn.off('click');
		this.removerCategoriaBtn.on('click', onRemoverCategoriaBtnClicked.bind(this));
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
	
	function getId(idx,obj) {
		return 'supermercadoCategorias'+idx+'.'+obj;
	}
	
	function getName(idx, obj){
		return 'supermercadoCategorias['+idx+'].'+obj;
	}
	
	function reorganizarIdENameCategorias() {
		var trs = this.tbody.find('tr');
		trs.each(function( idx ) {
			$(this).find('input').each(function(){
				$(this).attr('id', $(this).attr('id').replace(/[0-9]/g, idx));
				$(this).attr('name', $(this).attr('name').replace(/[0-9]/g, idx));
			});
		});
	}
	
	return Supermercado;
}());

$(function(){
	var supermercado = new Feira.Supermercado();
	supermercado.iniciar();
})
var Feira = Feira || {};

Feira.Supermercado = (function() {
	
	function Supermercado () {
		this.adicionarCategoriaModal = $('#adicionarCategoriaModal');
		this.url = this.adicionarCategoriaModal.data('url');
		this.tabelaSupermercadoCategorias = $('.js-tabela_supermercado-categorias');
		this.linhaNenhumaCategoriaAdicionada = $('.js-linha-nenhuma-categoria-adicionada');
	}
	
	Supermercado.prototype.iniciar = function() {
		this.adicionarCategoriaModal.on('show.bs.modal', onShowAdicionarCategoriaModal.bind(this));
		this.adicionarCategoriaModal.on('categoria-adicionada', onCategoriaAdicionada.bind(this));
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
		var tbody = this.tabelaSupermercadoCategorias.find('tbody');
		var idx = tbody.children().length;
		
		var tr = $('<tr>')
			.append($('<td>').text(supermercadoCategoriaAdicionada.categoria.nome))
			.append($('<td>').text(supermercadoCategoriaAdicionada.corredor))
			.append($('<td>').text(supermercadoCategoriaAdicionada.posicaoCorredor))
			.append($('<td>').text(supermercadoCategoriaAdicionada.descricaoSentido))
			.append($('<td>')
				.append($('<a>').attr('class', 'btn btn-sm text-primary feira-item-col-acao').attr('title', 'Alterar').attr('href', '#')
						.append($('<i>').attr('class', 'fa fa-edit')))
				.append($('<a>').attr('class', 'btn btn-sm text-danger feira-item-col-acao').attr('title', 'Remover').attr('href', '#')
						.append($('<i>').attr('class', 'fa fa-trash-alt')))
				.append($('<input>').attr('type','hidden').attr('id', 'supermercadoCategorias'+idx+'.supermercado.id').attr('name', 'supermercadoCategorias['+idx+'].id').attr('value', supermercadoCategoriaAdicionada.id))
				.append($('<input>').attr('type','hidden').attr('id', 'supermercadoCategorias'+idx+'.supermercado.id').attr('name', 'supermercadoCategorias['+idx+'].supermercado.id').attr('value', supermercadoCategoriaAdicionada.supermercado?.id))
				.append($('<input>').attr('type','hidden').attr('id', 'supermercadoCategorias'+idx+'.categoria.id').attr('name', 'supermercadoCategorias['+idx+'].categoria.id').attr('value', supermercadoCategoriaAdicionada.categoria.id))
				.append($('<input>').attr('type','hidden').attr('id', 'supermercadoCategorias'+idx+'.categoria.nome').attr('name', 'supermercadoCategorias['+idx+'].categoria.nome').attr('value', supermercadoCategoriaAdicionada.categoria.nome))
				.append($('<input>').attr('type','hidden').attr('id', 'supermercadoCategorias'+idx+'.corredor').attr('name', 'supermercadoCategorias['+idx+'].corredor').attr('value', supermercadoCategoriaAdicionada.corredor))
				.append($('<input>').attr('type','hidden').attr('id', 'supermercadoCategorias'+idx+'.posicaoCorredor').attr('name', 'supermercadoCategorias['+idx+'].posicaoCorredor').attr('value', supermercadoCategoriaAdicionada.posicaoCorredor))
				.append($('<input>').attr('type','hidden').attr('id', 'supermercadoCategorias'+idx+'.sentido').attr('name', 'supermercadoCategorias['+idx+'].sentido').attr('value', supermercadoCategoriaAdicionada.sentido))
			)
		tbody.append(tr);
	}
	
	return Supermercado;
}());

$(function(){
	var supermercado = new Feira.Supermercado();
	supermercado.iniciar();
})
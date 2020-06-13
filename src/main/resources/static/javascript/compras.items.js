var Feira = Feira || {};

Feira.Items = (function() {
	function Items() {
		this.inputComprasPesoItem = $('.js-compras-peso-item');
		this.inputComprasQuantidadeItem = $('.js-compras-quantidade-item');
		this.diminuirQuantidadeItemBtn = $('.js-diminuir-quantidade-item-btn');
		this.aumentarQuantidadeItemBtn = $('.js-aumentar-quantidade-item-btn');
		this.switchQuantidadePeso = $('.js-switch-quantidade-peso');
	}
	
	Items.prototype.iniciar = function() {
		aplicarMascaras.call(this);		
		bindQuantidade.call(this);
		this.switchQuantidadePeso.on('switchChange.bootstrapSwitch', onSwitchQuantidadePeso.bind(this));
	}
	
	function aplicarMascaras() {
		var options =  {
			onKeyPress: function(peso, e, field, options) {
				field.val(limitarTamanhoCampo(peso));
			}
		};

		this.inputComprasQuantidadeItem.mask('#0', {reverse: true});
		this.inputComprasPesoItem.mask('#0,000', {reverse: true});
		this.inputComprasPesoItem.mask('0999,999', options);
		this.inputComprasPesoItem.each(function() {
			$(this).val(limitarTamanhoCampo($(this).val()));
		});
		
	}
	
	function limitarTamanhoCampo(campo) {
		var tamanho = campo.indexOf(',') > -1 && campo.indexOf(',') < 4 ? 5 : 4;
		if(campo.length > tamanho) {
			campo = campo.substring(0,tamanho);
		}
		
		return campo;
	}
	
	function bindQuantidade() {
		this.inputComprasQuantidadeItem.on('change', onQuantidadeItemChange.bind(this));
		this.inputComprasPesoItem.on('change', onQuantidadeItemChange.bind(this));
		this.diminuirQuantidadeItemBtn.on('click', onDiminuirQuantidadeItem);
		this.aumentarQuantidadeItemBtn.on('click', onAumentarQuantidadeItem);
	}
	
	function onQuantidadeItemChange(event) {
		var input = $(event.target);
		var quantidade = input.val();
		var peso = 0;
		
		if(numeral(quantidade) < 0) {
			input.val(0);
			quantidade = 0;
		} else if(numeral(quantidade) > 9999) {
			input.val(9999);
			quantidade = 9999;
		}
		
		if(isInputPeso(input)) {
			quantidade = Feira.formatarPeso(quantidade);
			input.val(limitarTamanhoCampo(quantidade.replace('.','')));
		}
		
		var produtoId = input.data('produto-id');
		var porPeso = $('#switch-quantidade-peso-'+produtoId).data('por-peso');
		
		if(porPeso) {
			peso = quantidade;
			quantidade = 0;
		}
		
		var response = $.ajax({
			url: '/compras/item/'+produtoId,
			method: 'PUT',
			data: {
				quantidade: quantidade,
				peso: peso,
				porPeso: porPeso
			}
		});
		
		response.done(onItemAtualizadoServidor.bind(this, input));
	}
	
	function onItemAtualizadoServidor(input, itemFeiraDTO) {
		$(input).trigger('feira.carrinho.item-atualizado', itemFeiraDTO);
	}
	
	function onDiminuirQuantidadeItem(event) {
		var dados = getInput(event);
		alterarQuantidade(event, dados.input, diminuirValor, dados.valor);
	}
	
	function onAumentarQuantidadeItem(event) {
		var dados = getInput(event);
		alterarQuantidade(event, dados.input, aumentarValor, dados.valor);
	}
	
	function getInput(event) {
		var produtoId = $(event.currentTarget).data('produto-id');
		var porPeso = $('#switch-quantidade-peso-'+produtoId).data('por-peso');
		if(porPeso) {
			return {input: $('#item_peso-'+produtoId), valor: 0.1};
		} else {
			return {input: $('#item_quantidade-'+produtoId), valor: 1};
		}
	}
	
	function alterarQuantidade(event, input, funcao, valorAlteracao) {
		event.preventDefault();
		var valor = numeral(input.val());
		var quantidade = funcao(valor, valorAlteracao);
		input.val(quantidade);
		input.trigger('change');
	}
	
	function aumentarValor(valor, quantidade) {
		return (valor*10 + quantidade*10)/10;
	}
	
	function diminuirValor(valor, quantidade) {
		return (valor*10 - quantidade*10)/10;
	}
	
	function isInputPeso(input) {
		return input.hasClass('js-compras-peso-item');
	}
	
	function onSwitchQuantidadePeso(event, state) {
		var produtoId = $(event.currentTarget).data('produto-id');
		var itemDescricao = $('#item-descricao-'+produtoId).text('Qtd:');
		var inputItemQuantidade = $('#item_quantidade-'+produtoId);
		var inputItemPeso = $('#item_peso-'+produtoId);
		
		if(state) {
			itemDescricao.text('Peso (Kg):');
			inputItemQuantidade.hide()
			inputItemPeso.show();
		} else {
			itemDescricao.text('Qtd:');
			inputItemQuantidade.show()
			inputItemPeso.hide();
		}
		
		$('#switch-quantidade-peso-'+produtoId).data('por-peso', state);
		inputItemQuantidade.val(0);
		inputItemPeso.val(0);
		inputItemQuantidade.trigger('change');
	};
	
	return Items;
}());

$(function(){
	var items = new Feira.Items();
	items.iniciar();
});
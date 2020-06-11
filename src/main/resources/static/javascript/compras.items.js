var Feira = Feira || {};

Feira.Items = (function() {
	function Items () {
		this.inputQuantidadeItem = $('.js-listagem-quantidade-item');
		this.diminuirQuantidadeItemBtn = $('.js-diminuir-quantidade-item-btn');
		this.aumentarQuantidadeItemBtn = $('.js-aumentar-quantidade-item-btn');
		this.numero = $('.js-numero-compras');
		this.classePeso = 'js-peso-compras';
		this.peso = $('.'+this.classePeso);
	}
	
	Items.prototype.iniciar = function() {
		aplicarMascaras.call(this);		
		bindQuantidade.call(this);
	}
	
	function aplicarMascaras() {
		var options =  {
			onKeyPress: function(peso, e, field, options) {
				field.val(limitarTamanhoCampo(peso));
			}
		};
		this.numero.mask('#0', {reverse: true});
		this.peso.mask('#0,000', {reverse: true});
		this.peso.mask('0999,999', options);
		this.peso.each(function() {
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
		this.inputQuantidadeItem.on('change', onQuantidadeItemChange);
		this.diminuirQuantidadeItemBtn.on('click', onDiminuirQuantidadeItem);
		this.aumentarQuantidadeItemBtn.on('click', onAumentarQuantidadeItem);
	}
	
	function onQuantidadeItemChange(event) {
		var input = $(event.target);
		var quantidade = input.val();
		
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
		
		var response = $.ajax({
			url: '/compras/item/'+produtoId,
			method: 'PUT',
			data: {
				quantidade: quantidade
			}
		});
	}
	
	function onDiminuirQuantidadeItem(event, funcao) {
		alterarQuantidade(event, diminuirValor);
	}
	
	function onAumentarQuantidadeItem(event) {
		alterarQuantidade(event, aumentarValor);
	}
	
	function getInput(event) {
		var btn = $(event.currentTarget);
		var produtoId = btn.data('produto-id');

		return $('#item_'+produtoId);
	}
	
	function alterarQuantidade(event, funcao) {
		event.preventDefault();
		var input = getInput(event);
		var valor = numeral(input.val()); 
		var valorAlteracao = isInputPeso(input) && valor < 1000 ? 0.1 : 1;
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
		return input.hasClass('js-peso-compras');
	}
	
	return Items;
}());

$(function(){
	var items = new Feira.Items();
	items.iniciar();
});
var Feira = Feira || {};

Feira.Corredor = (function() {
	function Corredor() {
		this.corredorModal = $('#corredorModal');
		this.uuid = $('#uuid').val();
	}
	
	Corredor.prototype.iniciar = function() {
		this.corredorModal.on('show.bs.modal', onShowCorredorModal.bind(this));
		this.corredorModal.on('corredores-atualizados', onCorredoresAtualizados.bind(this));
		this.corredorModal.on('corredor-alterado', onCorredorAlterado.bind(this));
		bindBtn.call(this);
	}
	
	function onShowCorredorModal(event) {
		var operacao = $(event.relatedTarget).data('operacao') || event.relatedTarget.data['operacao'];
		var url = this.corredorModal.data('url') + '?operacao=' + operacao + '&uuid=' + this.uuid;
		var response = $.ajax({
			url: url,
			contentType: 'application/json',
			method: 'PUT',
			data: JSON.stringify(event.relatedTarget)
		});
		
		response.done(onCorredorModalResponse.bind(this));
	}
	
	function onCorredorModalResponse(html) {
		this.corredorModal.html(html);
	}
	
	function onCorredoresAtualizados(event) {
		var response = $.ajax({
			url: 'corredores?uuid='+this.uuid,
			contentType: 'application/json',
			method: 'GET'
		});
		
		response.done(onCorredoresAtualizadosResponse.bind(this));
	}

	function onCorredoresAtualizadosResponse(html) {
		$('#corredores-adicionados').html(html);
		$('#categoriaModal').trigger('categorias-atualizadas');
		bindBtn.call(this);
	}
	
	function bindBtn() {
		this.alterarCorredorBtn = $('.js-alterar-corredor');
		this.alterarCorredorBtn.off('click');
		this.alterarCorredorBtn.on('click', onAlterarCorredorBtnClicked.bind(this));
		
		this.removerCorredorBtn = $('.js-remover-corredor');
		this.removerCorredorBtn.off('click');
		this.removerCorredorBtn.on('click', onRemoverCorredorBtnClicked.bind(this));
	}
	
	function onAlterarCorredorBtnClicked(event) {
		event.preventDefault();
		var btn = $(event.currentTarget);
		var corredor = {
			id: btn.data('id'),
			numero: btn.data('numero'),
			descricao: btn.data('descricao'),
			data: {
				operacao: btn.data('operacao')
			}
		}
		this.corredorModal.modal({show: true}, corredor);
	}
	
	function onCorredorAlterado() {
		bindBtn.call(this);
		this.corredorModal.modal('hide');
		swal('Corredor alterado com sucesso!', '', 'success');
	}
	
	function onRemoverCorredorBtnClicked(event) {
		event.preventDefault();
		var btn = $(event.currentTarget);
		var url = btn.data('url');
		var numero = btn.data('numero');
		
		swal({
			title: 'Tem certeza?',
			text: 'Excluir o corredor "' + numero + '"? Ao excluir o corredor as categorias associadas a ele também serão removidas.',
			icon: 'warning',
			buttons: ['Cancelar', 
				{
					text: 'Sim, exclua agora!',
				    value: true,
				    visible: true,
				    closeModal: false
				}]
		}).then((remover) => {
		  if (remover) {
			  onRemoverConfirmado.call(this, url)
		  }
		});
	}
	
	function onRemoverConfirmado(url) {
		$.ajax({
			url: url + '?uuid=' + this.uuid,
			method: 'DELETE',
			success: onRemovidoComSucesso.bind(this),
			error: onErroRemocao.bind(this)
		});
	}
	
	function onRemovidoComSucesso() {
		$('#corredorModal').trigger('corredores-atualizados');
		$('#categoriaModal').trigger('categorias-atualizadas');
		swal('Corredor removido com sucesso!', '', 'success');
	}
	function onErroRemocao(e) {
		swal('Oops!', e.responseText, 'error');
	}
	
	return Corredor;
}());

$(function() {
	var corredor = new Feira.Corredor();
	corredor.iniciar();
});
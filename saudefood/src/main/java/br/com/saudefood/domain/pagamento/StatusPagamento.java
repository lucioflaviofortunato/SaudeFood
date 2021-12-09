package br.com.saudefood.domain.pagamento;

public enum StatusPagamento {
	
	Autorizado("Autorizado"),
	NaoAutorizado("Não autorizado pela isntituição financeira"),
	CartaoInvalido("Cartão invalido ou bloqueado");
	
	
	String descricao;
	
	StatusPagamento(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}

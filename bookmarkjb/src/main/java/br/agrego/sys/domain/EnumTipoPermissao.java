package br.agrego.sys.domain;

import java.util.ArrayList;
import java.util.List;

public enum EnumTipoPermissao {
	NEGADO			("NEGADO","N"),
	INDETERMINADO	("INDETERMINADO","I"),
	PERMITIDO		("PERMITIDO","P");
	
	public static List<EnumTipoPermissao> asList() {
		List<EnumTipoPermissao> lista = new ArrayList<EnumTipoPermissao>();

		lista.add(NEGADO);
		lista.add(INDETERMINADO);
		lista.add(PERMITIDO);
		
		return lista;
	}
	
	private String descricao;
	private String sigla;
	
	EnumTipoPermissao(String descricao,String sigla) {
		this.descricao = descricao;
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}

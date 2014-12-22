package br.agrego.sys.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DEF_DEFINICOES")
public class Definicoes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public Definicoes() {
		super();
	}
	
	public Definicoes(String chave, String valor) {
		super();
		this.chave = chave.toLowerCase();
		this.valor = valor;
	}

	@Id
	@Column(name="CHAVE")
	private String chave;

	@Column(name="VALOR",nullable=false)
	private String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}
	
}

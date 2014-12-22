package br.agrego.sys.domain;

import java.util.ArrayList;
import java.util.List;

public enum EnumMeses {
	JANEIRO (1),
	FEVEREIRO (2),
	MARÇO (3),
	ABRIL (4),
	MAIO (5),
	JUNHO (6),
	JULHO (7),
	AGOSTO (8),
	SETEMBRO (9),
	OUTUBRO (10),
	NOVEMBRO (11),
	DEZEMBRO (12);
	
	private int numMes;
	
	private EnumMeses(int numMes) {  
		this.numMes = numMes;
	}
	
	public static List<EnumMeses> asList() {
		List<EnumMeses> lista = new ArrayList<EnumMeses>();
		for (EnumMeses e : EnumMeses.values()) {
			lista.add(e);
		}
		return lista;
	}

	public static EnumMeses getMes(int numMes) {
		for (EnumMeses enumMes : EnumMeses.values()) {
			if (enumMes.getNumMes()==numMes){
				return enumMes;
			}
		}
		return null;
	}
	
	public int getNumMes() {
		return numMes;
	}
}

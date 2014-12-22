package br.agrego.sys.persistence;

import java.util.List;

import javax.persistence.Query;

import br.agrego.sys.domain.Definicoes;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
@SuppressWarnings("unchecked")
public class DefinicoesDAO extends JPACrud<Definicoes, String> {

	private static final long serialVersionUID = 1L;

	public List<Object[]> testeHql(String hql) {
		Query query = createQuery(hql);
		List<Object[]> listaResultado=null;
		listaResultado = query.getResultList(); 
		return listaResultado; 
	}
	
	public List<?> testeHql2(String hql) {
		Query query = createQuery(hql);
		List<?> listaResultado=null;
		listaResultado = query.getResultList(); 
		return listaResultado; 
	}
}

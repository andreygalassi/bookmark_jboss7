package br.agrego.sys.persistence;

import javax.persistence.Query;

import br.agrego.sys.domain.Menu;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class MenuDAO extends JPACrud<Menu, Integer> {

	private static final long serialVersionUID = 1L;
	
	public Menu findByNome(String nome){
		Query query = createQuery("select b from Menu b where nome = :nome");
		query.setParameter("nome", nome);
		
		return (Menu) query.getSingleResult();
	}

}

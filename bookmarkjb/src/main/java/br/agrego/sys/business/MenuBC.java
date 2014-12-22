package br.agrego.sys.business;

import br.agrego.sys.domain.EnumMenu;
import br.agrego.sys.domain.Menu;
import br.agrego.sys.persistence.MenuDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class MenuBC extends DelegateCrud<Menu, Integer, MenuDAO> {

	private static final long serialVersionUID = 1L;

//	@Inject	private MessageContext messageContext;
	
	public void inicia() {
//		messageContext.add("teste de mensagem", "parametro");
		for (EnumMenu enumMenu : EnumMenu.asList()) {
			Menu menu = super.load(enumMenu.ordinal());
			if (menu==null){
				Menu entity = new Menu();
				entity.setId((Integer)enumMenu.ordinal());
				entity.setNome(enumMenu.getNome());
				super.insert(entity);
			}else{
				menu.setNome(enumMenu.getNome());
				super.update(menu);
			}
		}
		
	}

	public Menu findByNome(String nome){
		return getDelegate().findByNome(nome);
	}
}

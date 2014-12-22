package br.agrego.sys.ui.presenter;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import br.agrego.sys.domain.EnumMenu;
import br.agrego.sys.ui.AgregoApplication;
import br.agrego.sys.ui.view.AboutWindow;
import br.agrego.sys.ui.view.MainView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessMenuSelection;
import br.gov.frameworkdemoiselle.vaadin.template.AbstractPresenter;
import br.gov.frameworkdemoiselle.vaadin.util.ViewNavigator;

import com.vaadin.ui.Window;

@ViewController
@SessionScoped
public class MainPresenter extends AbstractPresenter<MainView> {

//	public static final String MENU_BOOKMARK = "Bookmark";
//
//	public static final String MENU_CATEGORY = "Category";
//	
//	public static final String MENU_ABOUT = "About";

	private static final long serialVersionUID = 1L;

	@Inject	private ViewNavigator navigator;
	
	@Inject	private PermissaoPresenter permissaoPresenter;
	@Inject	private GrupoPresenter grupoPresenter ;
	@Inject	private UsuarioPresenter usuarioPresenter ;

	@Inject	private AlterarSenhaPresenter alterarSenhaPresenter ;
	

	public void processMenuSelected(@Observes @ProcessMenuSelection EnumMenu selection) {
//		if (MENU_BOOKMARK.equals(selection)) {
//			navigator.navigate(bookmarkPresenter.getView());
//		}
//		if (MENU_CATEGORY.equals(selection)) {
//			navigator.navigate(categoryPresenter.getView());
//		}
//		if (MENU_ABOUT.equals(selection)) {
//			Window about = new AboutWindow();
//			getView().getWindow().addWindow(about);
//		}
		switch(selection){
			case LOGOUT:
				((AgregoApplication) AgregoApplication.getInstance()).logout();
				getView().onLogout();
				break;
			case ABOUT:
				Window about = new AboutWindow();
				getView().getWindow().addWindow(about);
		    	break;
			case ALTERA_SENHA:
		    	navigator.navigate(alterarSenhaPresenter.getView());
				break;
			case PERMISSOES:
		    	navigator.navigate(permissaoPresenter.getView());
				break;
			case GRUPO:
		    	navigator.navigate(grupoPresenter.getView());
				break;
			case USUARIO:
		    	navigator.navigate(usuarioPresenter.getView());
				break;
				
			default:
				break;
	    	  
		}
	}
	
}

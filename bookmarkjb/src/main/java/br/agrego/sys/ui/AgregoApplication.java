package br.agrego.sys.ui;

import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import br.agrego.sys.acesscontrol.Credenciais;
import br.agrego.sys.business.UsuarioBC;
import br.agrego.sys.domain.Usuario;
import br.agrego.sys.ui.presenter.MainPresenter;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import br.gov.frameworkdemoiselle.vaadin.template.VaadinApplication;

import com.vaadin.ui.Window;

@SessionScoped
public class AgregoApplication extends VaadinApplication {

	private static final long serialVersionUID = 1L;

	@Inject
	private ResourceBundle bundle;

	@Inject
	private MainPresenter mainPresenter; 

	@Inject	private SecurityContext context;
	@Inject	private Credenciais credenciais;
	@Inject	private UsuarioBC usuarioBC;
	
	public void init() {
		setTheme("bookmark");
		final Window window = new Window(bundle.getString("app.title"));
		window.addComponent(mainPresenter.getView());
		setMainWindow(window);
	}
	
	public void logout() {
		try {
			Usuario u = usuarioBC.load(Long.parseLong(credenciais.getId()));
			u.setDtUltimoAcesso(new Date());
			usuarioBC.update(u);
		} catch (Exception e) {
			// TODO: handle exception
		}
		credenciais.limpar();

		context.logout();
	}

}

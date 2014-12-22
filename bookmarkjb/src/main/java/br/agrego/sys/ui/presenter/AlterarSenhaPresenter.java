package br.agrego.sys.ui.presenter;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import br.agrego.sys.acesscontrol.Credenciais;
import br.agrego.sys.business.UsuarioBC;
import br.agrego.sys.domain.Usuario;
import br.agrego.sys.ui.view.AlterarSenhaView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.vaadin.event.BeforeNavigateToView;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessSave;
import br.gov.frameworkdemoiselle.vaadin.template.AbstractPresenter;

import com.vaadin.ui.Window;

@ViewController
@SessionScoped
public class AlterarSenhaPresenter extends AbstractPresenter<AlterarSenhaView> {

	private static final long serialVersionUID = 1L;

	@Inject 
	Credenciais credenciais;
	
	@Inject
	UsuarioBC usuarioBC;
	

	public void alterarSenha(@Observes @ProcessSave String novaSenha) {
		Usuario usuario = usuarioBC.load(Long.valueOf(credenciais.getId()));
		usuario.setSenha(novaSenha);
		usuarioBC.update(usuario);
		credenciais.setSenha(usuario.getSenha());
		getView().getApplication().getMainWindow().showNotification("Senha alterada com sucesso!", Window.Notification.TYPE_HUMANIZED_MESSAGE);
		getView().clear();
	}

	public void beforeNavigate(@Observes @BeforeNavigateToView AlterarSenhaView view) {
		getView().clear();
	}

}

package br.agrego.sys.ui.view;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import br.agrego.sys.acesscontrol.Credenciais;
import br.agrego.sys.business.PermissaoBC;
import br.agrego.sys.business.UsuarioBC;
import br.agrego.sys.domain.EnumMenu;
import br.agrego.sys.domain.EnumTipoPermissao;
import br.agrego.sys.domain.Usuario;
import br.agrego.sys.ui.AgregoApplication;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.vaadin.annotation.Navigable;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessMenuSelection;
import br.gov.frameworkdemoiselle.vaadin.ui.StructuredView;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TabSheet;

public class MainView extends StructuredView {

	private static final long serialVersionUID = 1L;

//	@Inject	private ResourceBundle bundle;
	@Inject	private SecurityContext context;
	@Inject	private Credenciais credenciais;

	@Inject UsuarioBC usuarioBC;
	@Inject PermissaoBC permissaoBC;
	
	@Inject
	@ProcessMenuSelection
	private javax.enterprise.event.Event<EnumMenu> menuEvent;
	
	@Navigable	private TabSheet tabSheet = new TabSheet();

	private MenuBar.MenuItem menuLogin;

	private LoginForm loginForm = new LoginForm();
	private GridLayout gridLayoutcorpo = new GridLayout(3, 2);
	
	private Command menuCommand = new Command() {

		private static final long serialVersionUID = 1L;

		public void menuSelected(MenuItem selectedItem) {
			menuEvent.fire(EnumMenu.findByNome(selectedItem.getText()));
		}

	};
	
	@Override
	public void initializeComponents() {
		AgregoApplication.getInstance().setLocale(new Locale("pt_BR"));
		
		final GridLayout grid = new GridLayout(3, 1);

		getMenuBar().setWidth("100%");

		Embedded em = new Embedded("", new ThemeResource("images/bookmark-icon-logo.png"));
		grid.addComponent(em, 0, 0);
		grid.setComponentAlignment(em, Alignment.MIDDLE_CENTER);

		Label labelLogo = new Label("Bookmark Manager");
		labelLogo.setStyleName("logolabel");

		grid.addComponent(labelLogo, 1, 0);
		grid.setComponentAlignment(labelLogo, Alignment.MIDDLE_CENTER);

		getHeader().addComponent(grid);
		getFooter().addComponent(new Label("Bookmark Manager - Developed with Vaadin and Demoiselle Framework"));

		loginForm.setUsernameCaption("Usuario:");
		loginForm.setPasswordCaption("Senha:");
		loginForm.setLoginButtonCaption("Entrar");
		
		gridLayoutcorpo.addComponent(loginForm,2,0);

		getContent().addComponent(gridLayoutcorpo);
		
		loginForm.addListener(new LoginForm.LoginListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onLogin(LoginEvent event) {
				Usuario u = usuarioBC.login(event.getLoginParameter("username"),event.getLoginParameter("password"));
				
				if (u!=null){
					try {
						credenciais.setId(u.getId().toString());
						credenciais.setLogin(u.getLogin());
						credenciais.setSenha(u.getSenha());
//						credenciais.setAttribute(event.getLoginParameter("username"), "1");
						
//						context.hasRole("");
						context.login();
						
						createMenu();
						getContent().removeComponent(gridLayoutcorpo);
						getContent().addComponent(getTabSheet());
					} catch (Exception e) {
						System.out.println("Erro ao logar");
						e.printStackTrace();
						((AgregoApplication) AgregoApplication.getInstance()).logout();
						onLogout();
					}
				}
				
			}
			
		});
		
//		createMenu();
//		
//		getContent().addComponent(getTabSheet());
//		 
//		context.login(); 
		
	}

	public TabSheet getTabSheet() {
		return tabSheet;
	}

	public void onLogout() {
		getTabSheet().removeAllComponents();
		getContent().removeAllComponents();
		getMenuBar().removeItems();
		getMenuBar().setWidth("100%");
		getContent().addComponent(gridLayoutcorpo);
	}
	
	private void createMenu() {
		getMenuBar().setWidth("100%");
//		final MenuBar.MenuItem menuNew = getMenuBar().addItem(bundle.getString("menu.new"), null);
//		menuNew.setIcon(new ThemeResource("icons/16/document.png"));
//
//		menuNew.setIcon(new ThemeResource("icons/16/document.png"));
//		menuNew.addItem(bundle.getString("menu.new.bookmark"), menuCommand);
//		menuNew.addItem(bundle.getString("menu.new.category"), menuCommand);

//		final MenuBar.MenuItem menuHelp = getMenuBar().addItem(bundle.getString("menu.help"), null);
//		menuHelp.addItem(bundle.getString("menu.help.about"), null);
//		menuHelp.setIcon(new ThemeResource("icons/16/help.png"));
		
		Usuario usuario = usuarioBC.load(Long.valueOf(credenciais.getId()));
		
		Map<EnumMenu,EnumTipoPermissao> pVisualizar = permissaoBC.getPermissoesVisualizacao(usuario);
		Map<String,MenuItem> menuitens = new HashMap<String,MenuItem>();
		for (EnumMenu menu : EnumMenu.asList()) {
			if (pVisualizar.containsKey(menu) ){
				if (pVisualizar.get(menu).equals(EnumTipoPermissao.PERMITIDO)){
					if (menu.getPai()== null){
						if (menu.getExecuta()){ 
							menuitens.put(menu.getNome(),getMenuBar().addItem(menu.getNome(),menu.getIcone(),menuCommand));
						}else{
							menuitens.put(menu.getNome(),getMenuBar().addItem(menu.getNome(),menu.getIcone(),null));
						}
					}else {
						if (menuitens.containsKey(menu.getPai().getNome())){
							MenuItem mi = menuitens.get(menu.getPai().getNome());
							menuitens.put(menu.getNome(), mi.addItem(menu.getNome(),menu.getIcone(),menuCommand));
						}	
					}
				}
			}
		}

		menuLogin = getMenuBar().addItem(credenciais.getLogin().toUpperCase(), null);
		menuLogin.setIcon(new ThemeResource("icons/16/user.png"));
		menuLogin.addItem(EnumMenu.ALTERA_SENHA.getNome().toUpperCase(), menuCommand);
		menuLogin.addItem(EnumMenu.ABOUT.getNome().toUpperCase(), menuCommand);
		menuLogin.addItem(EnumMenu.LOGOUT.getNome().toUpperCase(), menuCommand);
	}
}

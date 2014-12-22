package br.agrego.sys.ui.view;

import br.agrego.sys.domain.EnumMenu;
import br.agrego.sys.util.FieldFactoryUtil;
import br.agrego.sys.util.components.BotoesBasicos;
import br.gov.frameworkdemoiselle.vaadin.template.BaseVaadinView;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public class UsuarioView extends BaseVaadinView {

	private static final long serialVersionUID = 1L;
	
	public TextField login;
	public PasswordField senha;
	public TextField email;
	public TwinColSelect grupos;
	public Table tabela;
	public BotoesBasicos bb;
	
	public void initializeComponents() {
		setCaption(EnumMenu.USUARIO.getNome());

		bb = BotoesBasicos.newInstance(this);		
		
		grupos =  FieldFactoryUtil.createTwinColSelect("GRUPOS","descricao");
		login =  FieldFactoryUtil.createTextField("LOGIN");
		senha =  FieldFactoryUtil.createPasswordField("SENHA");
		email =  FieldFactoryUtil.createTextField("E-MAIL");
		tabela =  FieldFactoryUtil.createTabelaFormatada(this);
		tabela.setSizeFull();
		tabela.setSelectable(true);
		
		grupos.setRows(7);
		grupos.setNullSelectionAllowed(true);
		grupos.setMultiSelect(true);
		grupos.setImmediate(true);
		grupos.setLeftColumnCaption("MEMBRO DE");
		grupos.setRightColumnCaption("SELECIONADOS");
		grupos.setWidth("450px");
		
//		tabela.setColumnOrder(new String[] { "id","ativo", "login", "dtCriacao", "dtUltimoAcesso", "email","gruposString"});
//		tabela.setVisibleColumns(new Object[]{"id","ativo", "login", "dtCriacao", "dtUltimoAcesso", "email","gruposString"});
//		tabela.setColumnHeader("dtCriacao","CRIAÇÃO");
//		tabela.setColumnHeader("dtUltimoAcesso","ULTIMO ACESSO");
//		tabela.setLocale(new Locale("pt_BR"));
//		tabela.addGeneratedColumn("ativo", new SimNaoColumnGenerator());
//		tabela.addGeneratedColumn("dtCriacao", new DataHoraColumnGenerator());
//		tabela.addGeneratedColumn("dtUltimoAcesso", new DataHoraColumnGenerator());
		
		
		addComponent(montaDados());
		addComponent(bb);
		addComponent(tabela);
	}
	
	private Panel montaDados(){
		Panel p = new Panel("DADOS");
		p.setWidth("100%");
//		p.addStyleName(Reindeer.PANEL_LIGHT);
		GridLayout gl = new GridLayout(2,3);
		
		gl.setSpacing(true);
		gl.setMargin(true);
		p.setContent(gl);
		
		gl.addComponent(login,0,0);
		gl.addComponent(senha,0,1);
		gl.addComponent(email,0,2);
		gl.addComponent(grupos,1,0,1,2);
		
		login.setRequired(true);
		senha.setRequired(true);
		
		login.setRequiredError("Login não deve ser branco");
		senha.setRequiredError("Senha não deve ser branco");
		
		return p;
	}


}

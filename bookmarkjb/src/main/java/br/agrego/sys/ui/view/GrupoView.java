package br.agrego.sys.ui.view;

import br.agrego.sys.domain.EnumMenu;
import br.agrego.sys.util.FieldFactoryUtil;
import br.agrego.sys.util.components.BotoesBasicos;
import br.gov.frameworkdemoiselle.vaadin.template.BaseVaadinView;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public class GrupoView extends BaseVaadinView {

	private static final long serialVersionUID = 1L;

	public TextField descricao;
	public TwinColSelect usuarios;
	public Table tabela;
	public BotoesBasicos bb;
	
	public void initializeComponents() {
		setCaption(EnumMenu.GRUPO.getNome());

		bb = BotoesBasicos.newInstance(this);	
		
		usuarios =  FieldFactoryUtil.createTwinColSelect("USUÁRIOS","login");
		descricao =  FieldFactoryUtil.createTextField("DESCRIÇÃO");
		tabela =  FieldFactoryUtil.createTabelaFormatada(this);
		tabela.setSizeFull();
		tabela.setSelectable(true);
		
		usuarios.setRows(5);
		usuarios.setNullSelectionAllowed(true);
		usuarios.setMultiSelect(true);
		usuarios.setImmediate(true);
		usuarios.setLeftColumnCaption("USUÁRIOS");
		usuarios.setRightColumnCaption("SELECIONADOS");
		usuarios.setWidth("350px");

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
		
		gl.addComponent(descricao);
		gl.addComponent(usuarios);
		
		descricao.setRequired(true);
		usuarios.setRequired(true);
		
		descricao.setRequiredError("Descrição não deve ser branco");
		usuarios.setRequiredError("Usuários não deve ser branco");
		
		return p;
	}

}

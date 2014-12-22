package br.agrego.sys.ui.view;

import br.agrego.sys.domain.EnumMenu;
import br.agrego.sys.domain.EnumTipoPermissao;
import br.agrego.sys.domain.Grupo;
import br.agrego.sys.util.FieldFactoryUtil;
import br.agrego.sys.util.components.BotoesBasicos;
import br.gov.frameworkdemoiselle.vaadin.stereotype.View;
import br.gov.frameworkdemoiselle.vaadin.template.BaseVaadinView;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

@View
public class PermissaoView extends BaseVaadinView {

	private static final long serialVersionUID = 1L;
	
	public ComboBox grupo;
	public ComboBox menu;
	public OptionGroup visualizar;
	public OptionGroup alterar;
	public OptionGroup criar;
	public OptionGroup excluir;
	public OptionGroup imprimir;

	public BotoesBasicos bb;
	
	public Table tabela;
	
//	public Permissao bean;
	
	public void initializeComponents() {
		setCaption(EnumMenu.PERMISSOES.getNome());

		bb = BotoesBasicos.newInstance(this);	
		
		grupo		= FieldFactoryUtil.createComboBox("GRUPO", "descricao");
		menu		= FieldFactoryUtil.createComboBox("MENU", "nome");
		visualizar	= FieldFactoryUtil.createOptionGroup("VISUALIZAR","descricao");
		alterar		= FieldFactoryUtil.createOptionGroup("ALTERAR", "descricao");
		criar		= FieldFactoryUtil.createOptionGroup("CRIAR", "descricao");
		excluir		= FieldFactoryUtil.createOptionGroup("EXCLUIR", "descricao");
		imprimir	= FieldFactoryUtil.createOptionGroup("IMPRIMIR", "descricao");
		tabela =  FieldFactoryUtil.createTabelaFormatada(this);
		tabela.setSizeFull();
		tabela.setSelectable(true);
		

		
		addComponent(montaDados());
		addComponent(bb);
		addComponent(montarTabela());
	}
	
	private Panel montaDados(){
		Panel p = new Panel("DADOS");
		p.setWidth("100%");
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setMargin(true);
		hl.setSpacing(true);
		
		p.setContent(hl);
		
		p.addComponent(grupo);
		p.addComponent(menu);
		p.addComponent(visualizar);
		p.addComponent(alterar);
		p.addComponent(criar);
		p.addComponent(excluir);
		p.addComponent(imprimir);
		
		return p;
	}
	
	private Table montarTabela(){
		tabela.setSelectable(true);
		tabela.setImmediate(true);
		tabela.setWidth("100%");
		
		tabela.addContainerProperty("grupo", Grupo.class,  null);
		tabela.addContainerProperty("menu", String.class,  null);
		tabela.addContainerProperty("visualizar", EnumTipoPermissao.class,  null);
		tabela.addContainerProperty("alterar", EnumTipoPermissao.class,  null);
		tabela.addContainerProperty("criar", EnumTipoPermissao.class,  null);
		tabela.addContainerProperty("excluir", EnumTipoPermissao.class,  null);
		tabela.addContainerProperty("imprimir", EnumTipoPermissao.class,  null);
		
		tabela.setVisibleColumns(new Object[]{"grupo", "menu", "visualizar", 
				"alterar", "criar", "excluir","imprimir"});
		
		tabela.setColumnHeaders(new String[]{"grupo", "menu", "visualizar", 
				"alterar", "criar", "excluir","imprimir"});
		
		return tabela;
	}

}

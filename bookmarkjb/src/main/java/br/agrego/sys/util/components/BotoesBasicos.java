package br.agrego.sys.util.components;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;

import br.agrego.sys.ui.annotation.ProcessAdd;
import br.agrego.sys.ui.annotation.ProcessFilter;
import br.agrego.sys.ui.annotation.ProcessNovo;
import br.agrego.sys.ui.annotation.ProcessRefresh;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessClear;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessDelete;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessSave;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;

public class BotoesBasicos extends GridLayout  {
	
	public enum EnumEstado {
		SEM_ITEM,
		NOVO_ITEM,
		ITEM_SELECIONADO;
	}

	private static final long serialVersionUID = 1L;

	private Button btNovo;
	private Button btSave;
	private Button btAdd;
	private Button btRem;
	private Button btClean;
	private Button btFilter;
	private Button btRefresh;
	private Button btPrint;
	
	private EnumEstado estado;
//	private AbstractComponent view;
	
	public static BotoesBasicos newInstance(final AbstractComponent view) {
		BotoesBasicos bb = new BotoesBasicos();
		
		bb.btNovo.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				Beans.getBeanManager().fireEvent(view, new AnnotationLiteral<ProcessNovo>() {});
			}});
		
		bb.btSave.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override 
			public void buttonClick(ClickEvent event) {
				Beans.getBeanManager().fireEvent(view, new AnnotationLiteral<ProcessSave>() {});
			}});
		bb.btAdd.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				Beans.getBeanManager().fireEvent(view, new AnnotationLiteral<ProcessAdd>() {});
			}});
		bb.btRem.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				Beans.getBeanManager().fireEvent(view, new AnnotationLiteral<ProcessDelete>() {});
			}});
		bb.btClean.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				Beans.getBeanManager().fireEvent(view, new AnnotationLiteral<ProcessClear>() {});
			}});
		bb.btFilter.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				Beans.getBeanManager().fireEvent(view, new AnnotationLiteral<ProcessFilter>() {});
			}});
		bb.btRefresh.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				Beans.getBeanManager().fireEvent(view, new AnnotationLiteral<ProcessRefresh>() {});
			}});
//		bb.btPrint.addListener(new ClickListener(){
//			private static final long serialVersionUID = 1L;
//			@SuppressWarnings("serial")
//			@Override
//			public void buttonClick(ClickEvent event) {
//				Beans.getBeanManager().fireEvent(view, new AnnotationLiteral<ProcessPrint>() {});
//			}});
		
		return bb;
		
	}
	public static BotoesBasicos newInstance(final Button.ClickListener view) {
		BotoesBasicos bb = new BotoesBasicos();
		bb.btNovo.addListener(view);
		bb.btSave.addListener(view);
		bb.btAdd.addListener(view);
		bb.btRem.addListener(view);
		bb.btClean.addListener(view);
		bb.btFilter.addListener(view);
		bb.btRefresh.addListener(view);
		bb.btPrint.addListener(view);
		
		return bb;
	}
	public static BotoesBasicos newInstance(final AbstractComponent view, final BeanManager beanManager) {
		BotoesBasicos bb = new BotoesBasicos();

		bb.btNovo.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				beanManager.fireEvent(view, new AnnotationLiteral<ProcessNovo>() {});
			}});
		bb.btSave.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				beanManager.fireEvent(view, new AnnotationLiteral<ProcessSave>() {});
			}});
		bb.btAdd.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				beanManager.fireEvent(view, new AnnotationLiteral<ProcessAdd>() {});
			}});
		bb.btRem.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				beanManager.fireEvent(view, new AnnotationLiteral<ProcessDelete>() {});
			}});
		bb.btClean.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				beanManager.fireEvent(view, new AnnotationLiteral<ProcessClear>() {});
			}});
		bb.btFilter.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				beanManager.fireEvent(view, new AnnotationLiteral<ProcessFilter>() {});
			}});
		bb.btRefresh.addListener(new ClickListener(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("serial")
			@Override
			public void buttonClick(ClickEvent event) {
				beanManager.fireEvent(view, new AnnotationLiteral<ProcessRefresh>() {});
			}});
//		bb.btPrint.addListener(new ClickListener(){
//			private static final long serialVersionUID = 1L;
//			@SuppressWarnings("serial")
//			@Override
//			public void buttonClick(ClickEvent event) {
//				beanManager.fireEvent(view, new AnnotationLiteral<ProcesPrint>() {});
//			}});
		
		
		return bb;
	}
	
	private BotoesBasicos() {
		this.estado = EnumEstado.SEM_ITEM;
		this.setColumns(4);
		this.setRows(1);
		this.setSpacing(true);

		btNovo = new Button("NOVO");
		btSave = new Button("SALVAR");
		btAdd = new Button("ADICIONAR");
		btRem = new Button("EXCLUIR");
		btClean = new Button("LIMPAR");
		btFilter = new Button("FILTRAR");
		btRefresh = new Button("REFRESH");
		btPrint = new Button("IMPRIMIR");

		btNovo.setImmediate(true);
		btSave.setImmediate(true);
		btAdd.setImmediate(true);
		btRem.setImmediate(true);
		btClean.setImmediate(true);
		btFilter.setImmediate(true);

		btNovo.setIcon(new ThemeResource("icons/16/document.png"));
		btNovo.setDescription("Cria um novo registro.");
		
		btSave.setIcon(new ThemeResource("icons/16/save_16.png"));
		btSave.setDescription("Atualiza um registro selecionado e limpa os campos.");

		btAdd.setIcon(new ThemeResource("icons/16/add_16.png"));
		btAdd.setDescription("Adiciona um novo registro e não limpa os campos.");
		
		btRem.setIcon(new ThemeResource("icons/16/recycle_16.png"));
		btRem.setDescription("Exclui registro.");
		
		btClean.setIcon(new ThemeResource("icons/16/document.png"));
		btClean.setDescription("Limpa os campos e cria um novo registro.");

//		btFilter.setIcon(new ThemeResource("icons/16/add_16.png"));
		btFilter.setDescription("Filtra de acordo com os campos.");

		btPrint.setIcon(new ThemeResource("icons/16/print2_16.png"));
		btPrint.setDescription("Imprimi o registro selecionado.");

		btRefresh.setIcon(new ThemeResource("icons/16/refresh_16.png"));
		btRefresh.setDescription("Recarrega os campos.");

		this.addComponent(btClean);
		this.addComponent(btAdd);
		this.addComponent(btSave);
		this.addComponent(btRem);

		this.setComponentAlignment(btClean, Alignment.BOTTOM_LEFT);
		this.setComponentAlignment(btAdd, Alignment.BOTTOM_LEFT);
		this.setComponentAlignment(btSave, Alignment.BOTTOM_LEFT);
		this.setComponentAlignment(btRem, Alignment.BOTTOM_LEFT);
		
		setEstado(EnumEstado.SEM_ITEM);
	}
	
	public void setEstado(EnumEstado estado){
		this.estado = estado;
		
		switch (this.estado) {
		case SEM_ITEM:
			btClean.setEnabled(true);
			btAdd.setEnabled(true);
			btSave.setEnabled(false);
			btRem.setEnabled(false);
			break;
		case NOVO_ITEM:
			btClean.setEnabled(true);
			btAdd.setEnabled(true);
			btSave.setEnabled(true);
			btRem.setEnabled(false);
			break;
		case ITEM_SELECIONADO:
			btClean.setEnabled(true);
			btAdd.setEnabled(true);
			btSave.setEnabled(true);
			btRem.setEnabled(true);
			break;

		default:
			break;
		}
		
	}

	public Button getBtSave() {return btSave;}

	public Button getBtAdd() {return btAdd;}

	public Button getBtRem() {return btRem;}
	
	public Button getBtClean() {return btClean;}

	public Button getBtFilter() {return btFilter;}

	public Button getBtRefresh() {return btRefresh;}
	
	public Button getBtPrint() {return btPrint;}
	
}

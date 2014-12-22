package br.agrego.sys.ui.presenter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.vaadin.data.collectioncontainer.CollectionContainer;

import br.agrego.sys.acesscontrol.Credenciais;
import br.agrego.sys.business.GrupoBC;
import br.agrego.sys.business.PermissaoBC;
import br.agrego.sys.domain.EnumMenu;
import br.agrego.sys.domain.EnumTipoPermissao;
import br.agrego.sys.domain.Grupo;
import br.agrego.sys.domain.Permissao;
import br.agrego.sys.exception.MyException;
import br.agrego.sys.ui.annotation.ProcessAdd;
import br.agrego.sys.ui.annotation.ProcessLoad;
import br.agrego.sys.ui.view.PermissaoView;
import br.agrego.sys.util.components.BotoesBasicos.EnumEstado;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.vaadin.event.BeforeNavigateToView;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessClear;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessDelete;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessItemSelection;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessSave;
import br.gov.frameworkdemoiselle.vaadin.template.AbstractPresenter;

import com.vaadin.data.Item;

@ViewController
@SessionScoped
public class PermissaoPresenter extends AbstractPresenter<PermissaoView> {

	private static final long serialVersionUID = 1L;

	@Inject	private GrupoBC grupoBC;
	@Inject	private PermissaoBC permissaoBC;
	@Inject private Credenciais credenciais;
	@Inject private BeanManager beanManager;

	public void processLoad(@Observes @ProcessLoad Grupo grupo) {
		getView().grupo.setContainerDataSource(CollectionContainer.fromBeans(grupoBC.findAllAtivos()));
	}
	
	@SuppressWarnings({ "serial" })
	public void processSave(@Observes @ProcessSave PermissaoView view) {
		Permissao permissao = null;
		try {
			credenciais.autorizacao(view, Credenciais.ACAO_ALTERAR);
			permissao = (Permissao) view.tabela.getValue();
			if (permissao==null) permissao = new Permissao();
			
			permissao.setGrupo((Grupo)	view.grupo.getValue());
			permissao.setMenu((EnumMenu)view.menu.getValue());
			permissao.setVisualizar((EnumTipoPermissao)	view.visualizar.getValue());
			permissao.setAlterar((EnumTipoPermissao)	view.alterar.getValue());
			permissao.setCriar((EnumTipoPermissao)		view.criar.getValue());
			permissao.setExcluir((EnumTipoPermissao)	view.excluir.getValue());
			permissao.setImprimir((EnumTipoPermissao)	view.imprimir.getValue());
			
			permissaoBC.save(permissao);
		} catch (MyException e) {
			e.showMessage(getView());
			setList(permissaoBC.findAll());
			beanManager.fireEvent(permissao, new AnnotationLiteral<ProcessLoad>() {});
			clear();
		} catch (Exception e) {
			getView().getWindow().showNotification("ERRO AO EXECUTAR AÇÃO", 3);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "serial" })
	public void processAdd(@Observes @ProcessAdd PermissaoView view) {
		Permissao permissao = new Permissao();
		try {
			credenciais.autorizacao(view, Credenciais.ACAO_CRIAR);
			permissao.setGrupo((Grupo)	view.grupo.getValue());
			permissao.setMenu((EnumMenu)view.menu.getValue());
			permissao.setVisualizar((EnumTipoPermissao)	view.visualizar.getValue());
			permissao.setAlterar((EnumTipoPermissao)	view.alterar.getValue());
			permissao.setCriar((EnumTipoPermissao)		view.criar.getValue());
			permissao.setExcluir((EnumTipoPermissao)	view.excluir.getValue());
			permissao.setImprimir((EnumTipoPermissao)	view.imprimir.getValue());
			
			permissaoBC.save(permissao);
		} catch (MyException e) {
			e.showMessage(getView());
			setList(permissaoBC.findAll());
			beanManager.fireEvent(permissao, new AnnotationLiteral<ProcessLoad>() {});
			clear();
		} catch (Exception e) {
			getView().getWindow().showNotification("ERRO AO EXECUTAR AÇÃO", 3);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	public void processDelete(@Observes @ProcessDelete PermissaoView view) {
		Permissao permissao = null;
		try {
			credenciais.autorizacao(view, Credenciais.ACAO_EXCLUIR);
			permissao = (Permissao) view.tabela.getValue();
			if (permissao==null) new MyException(2, "SELECIONE UM REGISTRO PARA EXCLUIR.");

			permissaoBC.delete(permissao.getId());
		} catch (MyException e) {
			e.showMessage(getView());
			setList(permissaoBC.findAll());
			beanManager.fireEvent(permissao, new AnnotationLiteral<ProcessLoad>() {});
			clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processItemSelection(@Observes @ProcessItemSelection PermissaoView view) {
		Permissao permissao = (Permissao) view.tabela.getValue();
		if (permissao!=null){

			view.grupo.setValue(permissao.getGrupo());
			view.menu.setValue(permissao.getMenu());
			view.visualizar.setValue(permissao.getVisualizar());
			view.alterar.setValue(permissao.getAlterar());
			view.criar.setValue(permissao.getCriar());
			view.excluir.setValue(permissao.getExcluir());
			view.imprimir.setValue(permissao.getImprimir());
			
			view.bb.setEstado(EnumEstado.ITEM_SELECIONADO);
		}else{
			view.bb.setEstado(EnumEstado.SEM_ITEM);
		}
	}

	public void beforeNavigateToView(@Observes @BeforeNavigateToView PermissaoView view) {
		getView().grupo.setContainerDataSource(CollectionContainer.fromBeans(grupoBC.findAllAtivos()));
		
		class TituloComparator implements Comparator<EnumMenu> {
		    public int compare(EnumMenu nome, EnumMenu outroNome) {
		        return nome.getNome().compareTo(outroNome.getNome());
		    }
		}
		TituloComparator comparator = new TituloComparator();
		Collections.sort(EnumMenu.asList(), comparator);
		view.menu.setContainerDataSource(CollectionContainer.fromBeans(EnumMenu.asList()));
		
		view.visualizar.setContainerDataSource(CollectionContainer.fromBeans(EnumTipoPermissao.asList()));
		view.alterar.setContainerDataSource(CollectionContainer.fromBeans(EnumTipoPermissao.asList()));
		view.criar.setContainerDataSource(CollectionContainer.fromBeans(EnumTipoPermissao.asList()));
		view.excluir.setContainerDataSource(CollectionContainer.fromBeans(EnumTipoPermissao.asList()));
		view.imprimir.setContainerDataSource(CollectionContainer.fromBeans(EnumTipoPermissao.asList()));
		
		setList(permissaoBC.findPermissoes());
	}
	
	public void processFormClear(@Observes @ProcessClear PermissaoView view) {
		clear();
	}


	private void clear() {
		getView().tabela.select(null);
		getView().grupo.select(null);
		getView().menu.select(null);
		getView().visualizar.setValue(EnumTipoPermissao.INDETERMINADO);
		getView().alterar.select(EnumTipoPermissao.INDETERMINADO);
		getView().criar.setValue(EnumTipoPermissao.INDETERMINADO);
		getView().excluir.setValue(EnumTipoPermissao.INDETERMINADO);
		getView().imprimir.setValue(EnumTipoPermissao.INDETERMINADO);
	}

	private void setList(List<Permissao> lista){
//		getView().tabela.setContainerDataSource(CollectionContainer.fromBeans(lista));
//		getView().tabela.setVisibleColumns(new Object[]{"id", "descricao", "usuariosString"});
		getView().tabela.removeAllItems();
		for (Permissao p : lista) {
			Item item;
			if (getView().tabela.getItem(p) == null){
				item = getView().tabela.addItem(p);
			}else{
				item = getView().tabela.getItem(p);
			}
			try {} catch (Exception e) {}
			try {item.getItemProperty("grupo").setValue(p.getGrupo());} catch (Exception e) {}
			try {item.getItemProperty("menu").setValue(p.getMenu().getNome());} catch (Exception e) {}
			try {item.getItemProperty("visualizar").setValue(p.getVisualizar());} catch (Exception e) {}
			try {item.getItemProperty("alterar").setValue(p.getAlterar());} catch (Exception e) {}
			try {item.getItemProperty("criar").setValue(p.getCriar());} catch (Exception e) {}
			try {item.getItemProperty("excluir").setValue(p.getExcluir());} catch (Exception e) {}
			try {item.getItemProperty("imprimir").setValue(p.getImprimir());} catch (Exception e) {}
		}
	}
	
}

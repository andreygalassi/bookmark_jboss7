package br.agrego.sys.ui.presenter;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.vaadin.data.collectioncontainer.CollectionContainer;

import br.agrego.sys.acesscontrol.Credenciais;
import br.agrego.sys.business.GrupoBC;
import br.agrego.sys.business.UsuarioBC;
import br.agrego.sys.domain.Grupo;
import br.agrego.sys.domain.Usuario;
import br.agrego.sys.exception.MyException;
import br.agrego.sys.ui.annotation.ProcessAdd;
import br.agrego.sys.ui.annotation.ProcessLoad;
import br.agrego.sys.ui.view.GrupoView;
import br.agrego.sys.ui.view.UsuarioView;
import br.agrego.sys.util.components.BotoesBasicos.EnumEstado;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.vaadin.event.BeforeNavigateToView;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessClear;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessDelete;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessItemSelection;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessSave;
import br.gov.frameworkdemoiselle.vaadin.template.AbstractPresenter;

@ViewController
@SessionScoped
public class GrupoPresenter extends AbstractPresenter<GrupoView> {

	private static final long serialVersionUID = 1L;

	@Inject	private UsuarioBC usuarioBC;
	@Inject	private GrupoBC grupoBC;
	@Inject private Credenciais credenciais;
	@Inject private BeanManager beanManager;
	
	public void processLoad(@Observes @ProcessLoad Usuario usuario) {
		getView().usuarios.setContainerDataSource(CollectionContainer.fromBeans(usuarioBC.findAllAtivos()));
	}
	
	@SuppressWarnings({ "serial", "unchecked" })
	public void processSave(@Observes @ProcessSave GrupoView view) {
		Grupo grupo = null;
		try {
			credenciais.autorizacao(view, Credenciais.ACAO_ALTERAR);
			grupo = (Grupo) view.tabela.getValue();
			if (grupo==null) grupo = new Grupo();
			grupo.setDescricao((String)view.descricao.getValue());
			grupo.setUsuarios((Set<Usuario>)view.usuarios.getValue());
			grupoBC.save(grupo);
		} catch (MyException e) {
			e.showMessage(getView());
			setList(grupoBC.findAll());
			beanManager.fireEvent(grupo, new AnnotationLiteral<ProcessLoad>() {});
			clear();
		} catch (Exception e) {
			getView().getWindow().showNotification("ERRO AO EXECUTAR AÇÃO", 3);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "serial", "unchecked" })
	public void processAdd(@Observes @ProcessAdd GrupoView view) {
		Grupo grupo = new Grupo();
		try {
			credenciais.autorizacao(view, Credenciais.ACAO_CRIAR);
			grupo.setDescricao((String)view.descricao.getValue());
			grupo.setUsuarios((Set<Usuario>)view.usuarios.getValue());
			grupoBC.save(grupo);
		} catch (MyException e) {
			e.showMessage(getView());
			setList(grupoBC.findAll());
			beanManager.fireEvent(grupo, new AnnotationLiteral<ProcessLoad>() {});
			clear();
		} catch (Exception e) {
			getView().getWindow().showNotification("ERRO AO EXECUTAR AÇÃO", 3);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	public void processDelete(@Observes @ProcessDelete UsuarioView view) {
		Grupo grupo = null;
		try {
			credenciais.autorizacao(view, Credenciais.ACAO_EXCLUIR);
			grupo = (Grupo) view.tabela.getValue();
			if (grupo==null) new MyException(2, "SELECIONE UM REGISTRO PARA EXCLUIR.");
			grupo.setAtivo(false);
			grupoBC.save(grupo);
		} catch (MyException e) {
			e.showMessage(getView());
			setList(grupoBC.findAll());
			beanManager.fireEvent(grupo, new AnnotationLiteral<ProcessLoad>() {});
			clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processItemSelection(@Observes @ProcessItemSelection GrupoView view) {
		Grupo grupo = (Grupo) view.tabela.getValue();
		if (grupo!=null){
			view.descricao.setValue(grupo.getDescricao());
			view.bb.setEstado(EnumEstado.ITEM_SELECIONADO);
		}else{
			view.bb.setEstado(EnumEstado.SEM_ITEM);
		}
	}

	public void beforeNavigateToView(@Observes @BeforeNavigateToView GrupoView view) {
		getView().usuarios.setContainerDataSource(CollectionContainer.fromBeans(usuarioBC.findAll()));
		setList(grupoBC.findAll());
	}
	
	public void processFormClear(@Observes @ProcessClear GrupoView view) {
		clear();
	}

	private void clear() {
		getView().descricao.setValue(null);
		getView().usuarios.setValue(null);
		getView().tabela.select(null);
	}

	private void setList(List<Grupo> lista){
		getView().tabela.setContainerDataSource(CollectionContainer.fromBeans(lista));
		getView().tabela.setVisibleColumns(new Object[]{"id", "descricao", "usuariosString"});
	}

}

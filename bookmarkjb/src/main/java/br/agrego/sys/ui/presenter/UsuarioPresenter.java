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
public class UsuarioPresenter extends AbstractPresenter<UsuarioView> {

	private static final long serialVersionUID = 1L;
	
	@Inject	private UsuarioBC usuarioBC;
	@Inject	private GrupoBC grupoBC;
	@Inject private Credenciais credenciais;
	@Inject private BeanManager beanManager;
	
	public void processLoad(@Observes @ProcessLoad Usuario usuario) {
		getView().grupos.setContainerDataSource(CollectionContainer.fromBeans(grupoBC.findAllAtivos()));
	}
	
	@SuppressWarnings({ "unchecked", "serial" })
	public void processSave(@Observes @ProcessSave UsuarioView view) {
		Usuario usuario = null;
		try {
			credenciais.autorizacao(view, Credenciais.ACAO_ALTERAR);
			usuario = (Usuario) view.tabela.getValue();
			if (usuario==null) usuario = new Usuario();
			usuario.setLogin((String)view.login.getValue());
			usuario.setSenha((String)view.senha.getValue());
			usuario.setEmail((String)view.email.getValue());
			usuario.setGrupos((Set<Grupo>) getView().grupos.getValue());
			usuarioBC.save(usuario);
		} catch (MyException e) {
			e.showMessage(getView());
			setList(usuarioBC.findAll());
			beanManager.fireEvent(usuario, new AnnotationLiteral<ProcessLoad>() {});
			clear();
//			Beans.getBeanManager().fireEvent(usuario, new AnnotationLiteral<ProcessSave>() {});
		} catch (Exception e) {
			getView().getWindow().showNotification("ERRO AO EXECUTAR AÇÃO", 3);
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "serial" })
//	@Transactional
	public void processAdd(@Observes @ProcessAdd UsuarioView view) {
		Usuario usuario = new Usuario();
		try {
			credenciais.autorizacao(view, Credenciais.ACAO_CRIAR);
			usuario.setLogin((String)view.login.getValue());
			usuario.setSenha((String)view.senha.getValue());
			usuario.setEmail((String)view.email.getValue());
			usuario.setGrupos((Set<Grupo>) getView().grupos.getValue());
//			usuario.getGrupos().addAll((Set<Grupo>) getView().grupos.getValue());
			usuarioBC.save(usuario);
		} catch (MyException e) {
			e.showMessage(getView());
			setList(usuarioBC.findAll());
			beanManager.fireEvent(usuario, new AnnotationLiteral<ProcessLoad>() {});
			clear();
		} catch (Exception e) {
			getView().getWindow().showNotification("ERRO AO EXECUTAR AÇÃO", 3);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	public void processDelete(@Observes @ProcessDelete UsuarioView view) {
		Usuario usuario = null;
		try {
			credenciais.autorizacao(view, Credenciais.ACAO_EXCLUIR);
			usuario = (Usuario) view.tabela.getValue();
			if (usuario==null) new MyException(2, "SELECIONE UM REGISTRO PARA EXCLUIR.");
			usuario.setAtivo(false);
			usuarioBC.save(usuario);
		} catch (MyException e) {
			e.showMessage(getView());
			setList(usuarioBC.findAll());
			beanManager.fireEvent(usuario, new AnnotationLiteral<ProcessLoad>() {});
			clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processItemSelection(@Observes @ProcessItemSelection UsuarioView view) {
		Usuario usuario = (Usuario) view.tabela.getValue();
		if (usuario!=null){
			view.login.setValue(usuario.getLogin());
			view.senha.setValue(usuario.getSenha());
			view.email.setValue(usuario.getEmail());
			view.grupos.setValue(usuario.getGrupos());
			view.bb.setEstado(EnumEstado.ITEM_SELECIONADO);
		}else{
			view.bb.setEstado(EnumEstado.SEM_ITEM);
		}
	}

	public void beforeNavigateToView(@Observes @BeforeNavigateToView UsuarioView view) {
		view.grupos.setContainerDataSource(CollectionContainer.fromBeans(grupoBC.findAll()));
		setList(usuarioBC.findAll());

	}
	
	public void processFormClear(@Observes @ProcessClear UsuarioView view) {
		clear();
	}

	private void clear() {
		getView().login.setValue(null);
		getView().senha.setValue(null);
		getView().email.setValue(null);
		getView().grupos.setValue(null);
		getView().tabela.select(null);
	}

	private void setList(List<Usuario> lista){
		getView().tabela.setContainerDataSource(CollectionContainer.fromBeans(lista));
		getView().tabela.setVisibleColumns(new Object[]{"id","ativo", "login", "dtCriacao", "dtUltimoAcesso", "email","gruposString"});
		getView().tabela.setColumnHeader("dtCriacao","CRIAÇÃO");
		getView().tabela.setColumnHeader("dtUltimoAcesso","ULTIMO ACESSO");
		getView().tabela.setColumnHeader("gruposString","GRUPOS");
	}
	
	
	
}

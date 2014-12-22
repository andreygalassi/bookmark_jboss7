package br.agrego.sys.business;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.agrego.sys.acesscontrol.Credenciais;
import br.agrego.sys.domain.EnumMenu;
import br.agrego.sys.domain.EnumTipoPermissao;
import br.agrego.sys.domain.Grupo;
import br.agrego.sys.domain.Permissao;
import br.agrego.sys.domain.Usuario;
import br.agrego.sys.exception.MyException;
import br.agrego.sys.persistence.GrupoDAO;
import br.agrego.sys.template.MyBCInterface;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@BusinessController
public class GrupoBC extends DelegateCrud<Grupo, Long, GrupoDAO> implements MyBCInterface<Grupo> {

	private static final long serialVersionUID = 1L;

	@Inject	private UsuarioBC usuarioBC;

	@Transactional
	public void inicia(){
		List<Grupo> bean = getDelegate().findAll();
		if (bean.size() == 0) {
			getDelegate().insert(Grupo.valueOf("ADMINISTRADORES", usuarioBC.load(1l)));
			getDelegate().insert(Grupo.valueOf("USUARIOS", usuarioBC.load(2l)));
		}	
	}
	
	@Override
	public void save(Grupo bean) throws MyException {
		if (bean.getId() != null) {
			update(bean);
			throw MyException.update();
		} else {
			insert(bean);
			throw MyException.insert();
		}
	}
	
	/**
	 * @param grupo
	 * @return Lista com as permissões PERMITIDO para todos os menus
	 */
	public List<Permissao> geraPermissoes(Grupo grupo){
		List<Permissao> ps = new ArrayList<Permissao>();
		Permissao p;
		for (EnumMenu menu : EnumMenu.asList()) {
			p = Permissao.valueOf(menu);
			p.setAlterar(EnumTipoPermissao.PERMITIDO);
			p.setCriar(EnumTipoPermissao.PERMITIDO);
			p.setExcluir(EnumTipoPermissao.PERMITIDO);
			p.setImprimir(EnumTipoPermissao.PERMITIDO);
			p.setVisualizar(EnumTipoPermissao.PERMITIDO);
			p.setGrupo(grupo);
			ps.add(p);
		}
		return ps;
	}
	
	public Grupo findByDescricao(String descricao){
		return getDelegate().findByDescricao(descricao);
	}
	
	public List<Grupo> findGrupos(){
		return getDelegate().findGrupos();
	}
	public List<Grupo> findAllAtivos(){
		return getDelegate().findAllAtivos();
	}
	public List<Grupo> findByCredenciais(Credenciais credenciais){
		return findByUsuario(usuarioBC.load(Long.parseLong(credenciais.getId())));
	}

	public List<Grupo> findByUsuario(Usuario usuario){
		return getDelegate().findByUsuario(usuario.getId());
	}

//	public List<Clinica> findClinicas(Long id) {
//		return getDelegate().findClinicas(id);
//	}

	public List<Usuario> findUsuarios(Long id) {
		return getDelegate().findUsuarios(id);
	}

}

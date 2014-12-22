package br.agrego.sys.business;

import java.util.List;

import javax.inject.Inject;

import br.agrego.sys.acesscontrol.Credenciais;
import br.agrego.sys.domain.Grupo;
import br.agrego.sys.domain.Usuario;
import br.agrego.sys.exception.MyException;
import br.agrego.sys.persistence.UsuarioDAO;
import br.agrego.sys.template.MyBCInterface;
import br.agrego.sys.util.CriptografiaUtil;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> implements MyBCInterface<Usuario> {
	
	private static final long serialVersionUID = 1L;

	@Inject Credenciais credenciais;

	@Override 
	public void save(Usuario bean) throws MyException {
		if (bean.getId() != null) {
			if (bean.getSenha().length()<30){
				bean.setSenha(CriptografiaUtil.criptografaString(bean.getSenha()));
			}
			update(bean);
			throw MyException.update();
		} else {
			bean.setSenha(CriptografiaUtil.criptografaString(bean.getSenha()));
			insert(bean);
			throw MyException.insert();
		}
	}
	
	@Transactional
	public void inicia(){
		List<Usuario> bean = getDelegate().findAll();
		if (bean.size() == 0) {
			Usuario entity1 = new Usuario();
			entity1.setLogin("admin");
			entity1.setSenha(CriptografiaUtil.criptografaString("admin"));
			insert(entity1);
			
			Usuario entity2 = new Usuario();
			entity2.setLogin("usuario");
			entity2.setSenha(CriptografiaUtil.criptografaString("usuario"));
			insert(entity2);
			
//			List<String> lista  = new ArrayList<String>() ;
//			lista.add("admin");
//			lista.add("usuario");	
		}	
	}

	@Override
	public List<Usuario> findAll(){
		return getDelegate().findAll();
	}

	public Usuario findByExemple(Usuario usuario) {
		UsuarioDAO dao = (UsuarioDAO) getDelegate();
		Usuario user = null;
		List<Usuario> lista = dao.findByExample(usuario);
		if(lista.size()==1){
			user = lista.get(0);
		}
		return user;
	}

	public Usuario login(String login, String senha) {
		Usuario u = new Usuario();
		u.setLogin(login);
		u.setSenha(CriptografiaUtil.criptografaString(senha));
		return findByLoginAndPass(u);
	}
	
	public List<Grupo> findGroupsByUsuario(Usuario usuario){
		return getDelegate().findGroupsByUsuario(usuario.getId());
	}

	public Usuario findByLoginAndPass(Usuario usuario) {
		UsuarioDAO dao = (UsuarioDAO) getDelegate();
		return dao.findByLoginAndPass(usuario);
	}

	public List<Usuario> findAllAtivos() {
		return getDelegate().findAllAtivo();
	}

}

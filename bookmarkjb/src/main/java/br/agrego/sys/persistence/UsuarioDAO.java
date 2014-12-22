package br.agrego.sys.persistence;

import java.util.List;

import javax.persistence.Parameter;
import javax.persistence.Query;

import br.agrego.sys.domain.Grupo;
import br.agrego.sys.domain.Usuario;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class UsuarioDAO extends JPACrud<Usuario, Long> {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	public List<Usuario> findByExample(Usuario example) {
		return super.findByExample(example);
	}

	public Usuario findByLogin(String login) {
		Query query = createQuery("select t from usuario t where t.login = :login");
		query.setParameter("login", login);
	
		@SuppressWarnings("unchecked")
		List<Usuario> p = query.getResultList();	
		
		if (p.size()>0){
			return  (Usuario)query.getResultList().get(0);	
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> findAllAtivo() {
		Query query = createQuery("select t from Usuario t where t.ativo = true");
		
		return  query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Grupo> findGroupsByUsuario(Long id) {
		Query query = createQuery("select u.grupos from Usuario u where u.id = :id ");
		query.setParameter("id", id);
		return query.getResultList();
	}

	public Usuario findByLoginAndPass(Usuario usuario) {
		Query query = createQuery("select t from Usuario t where t.ativo = true and t.login = :login and t.senha = :senha");
		
		for (Parameter<?> p : query.getParameters()) {
			if (p.getName().equals("login")){query.setParameter("login", usuario.getLogin());}
			else if(p.getName().equals("senha")){query.setParameter("senha", usuario.getSenha());}
		}
	
		@SuppressWarnings("unchecked")
		List<Usuario> p = query.getResultList();	
		
		if (p.size()>0){
			return  (Usuario)query.getResultList().get(0);	
		}
		return null;
	}

}

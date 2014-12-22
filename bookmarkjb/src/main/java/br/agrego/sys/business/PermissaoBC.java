package br.agrego.sys.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import br.agrego.sys.acesscontrol.Credenciais;
import br.agrego.sys.domain.EnumMenu;
import br.agrego.sys.domain.EnumTipoPermissao;
import br.agrego.sys.domain.Grupo;
import br.agrego.sys.domain.Permissao;
import br.agrego.sys.domain.Usuario;
import br.agrego.sys.exception.MyException;
import br.agrego.sys.persistence.PermissaoDAO;
import br.agrego.sys.template.MyBCInterface;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class PermissaoBC extends DelegateCrud<Permissao, Integer, PermissaoDAO> implements MyBCInterface<Permissao> {

	@Inject private GrupoBC grupoBC;
	
	@Inject Credenciais credenciais;
	@Inject UsuarioBC usuarioBC;

	private static final long serialVersionUID = 1L;
	
	public void inicia(){
		List<Permissao> bean = getDelegate().findAll();
		if (bean.size() == 0) {
			for (Permissao p : geraPermissoes(grupoBC.load(1l))) {//gera permissões para administradores
				insert(p);
			}
			for (Permissao p : geraPermissoes(grupoBC.load(2l))) {//gera permissões para usuarios
				insert(p);
			}
		}else{
			
		}
	}

	@Override
	public void save(Permissao bean) throws MyException {
		// TODO Auto-generated method stub
		
	}
	
	private List<Permissao> geraPermissoes(Grupo grupo){
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
	
	public Set<EnumMenu> findMenuByUsuario(Usuario usuario){
		Set<EnumMenu> n = getDelegate().findMenuByUsuario(usuario,EnumTipoPermissao.NEGADO);
		Set<EnumMenu> p = getDelegate().findMenuByUsuario(usuario,EnumTipoPermissao.PERMITIDO);

		p.removeAll(n);
		return p;
	}
	
	public Permissao getPermissao(Usuario usuario, EnumMenu menu){
		Permissao permissao = new Permissao();
		permissao.setId(0);
		GrupoBC grupoBC2 = new GrupoBC();
		if (grupoBC2.findByUsuario(usuario).contains(grupoBC2.load(1l))){
			permissao.setAlterar(EnumTipoPermissao.PERMITIDO);
			permissao.setCriar(EnumTipoPermissao.PERMITIDO);
			permissao.setExcluir(EnumTipoPermissao.PERMITIDO);
			permissao.setImprimir(EnumTipoPermissao.PERMITIDO);
			permissao.setVisualizar(EnumTipoPermissao.PERMITIDO);
		}else{
			permissao.setAlterar(getDelegate().getPermissaoAlterar(usuario, menu));
			permissao.setCriar(getDelegate().getPermissaoCriar(usuario, menu));
			permissao.setExcluir(getDelegate().getPermissaoExcluir(usuario, menu));
			permissao.setImprimir(getDelegate().getPermissaoImprimir(usuario, menu));
			permissao.setVisualizar(getDelegate().getPermissaoVisualizar(usuario, menu));
		}
		return permissao;
	}
	
	public Permissao getPermissao(Credenciais credenciais, EnumMenu menu){
		if (credenciais!=null){
			Usuario usuario = usuarioBC.load(Long.parseLong(credenciais.getId()));
			return getPermissao(usuario,menu);
		}
		return null;
	}
	
	public List<Permissao> findPermissoes() {
		return getDelegate().findPermissoes();
	}
	public List<Permissao> findAllAtivos() {
		return getDelegate().findAllAtivos();
	}

	public Map<EnumMenu,EnumTipoPermissao> getPermissoesVisualizacao(Usuario usuario){
		Map<EnumMenu,EnumTipoPermissao> permissoes = new HashMap<EnumMenu, EnumTipoPermissao>();
		
		List<Permissao> p =getDelegate().getPermissao(usuario);
		for (Permissao permissao : p) {
			if(!permissoes.containsKey(permissao.getMenu())){
				permissoes.put(permissao.getMenu(), permissao.getVisualizar());
			}else if (EnumTipoPermissao.INDETERMINADO.equals(permissoes.get(permissao.getMenu()))) { 
					permissoes.put(permissao.getMenu(),permissao.getVisualizar());
			}else if(EnumTipoPermissao.PERMITIDO.equals(permissoes.get(permissao.getMenu()))){
				if(EnumTipoPermissao.NEGADO.equals(permissoes.get(permissao.getMenu()))){
					permissoes.put(permissao.getMenu(),permissao.getVisualizar());
				}
			}
		}
		
		return permissoes;
	}

	public Map<EnumMenu,EnumTipoPermissao> getPermissoesAlteracao(Usuario usuario){
		Map<EnumMenu,EnumTipoPermissao> permissoes = new HashMap<EnumMenu, EnumTipoPermissao>();
		List<Permissao> p =getDelegate().getPermissao(usuario);
		for (Permissao permissao : p) {
			if(!permissoes.containsKey(permissao.getMenu())){
				permissoes.put(permissao.getMenu(), permissao.getAlterar());
			}else if (EnumTipoPermissao.INDETERMINADO.equals(permissoes.get(permissao.getMenu()))) { 
					permissoes.put(permissao.getMenu(),permissao.getAlterar());
			}else if(EnumTipoPermissao.PERMITIDO.equals(permissoes.get(permissao.getMenu()))){
				if(EnumTipoPermissao.NEGADO.equals(permissoes.get(permissao.getMenu()))){
					permissoes.put(permissao.getMenu(),permissao.getAlterar());
				}
			}
		}
		return permissoes;
	}
	public Map<EnumMenu,EnumTipoPermissao> getPermissoesCriacao(Usuario usuario){
		Map<EnumMenu,EnumTipoPermissao> permissoes = new HashMap<EnumMenu, EnumTipoPermissao>();
		List<Permissao> p =getDelegate().getPermissao(usuario);
		for (Permissao permissao : p) {
			if(!permissoes.containsKey(permissao.getMenu())){
				permissoes.put(permissao.getMenu(), permissao.getCriar());
			}else if (EnumTipoPermissao.INDETERMINADO.equals(permissoes.get(permissao.getMenu()))) { 
					permissoes.put(permissao.getMenu(),permissao.getCriar());
			}else if(EnumTipoPermissao.PERMITIDO.equals(permissoes.get(permissao.getMenu()))){
				if(EnumTipoPermissao.NEGADO.equals(permissoes.get(permissao.getMenu()))){
					permissoes.put(permissao.getMenu(),permissao.getCriar());
				}
			}
		}
		return permissoes;
	}
	public Map<EnumMenu,EnumTipoPermissao> getPermissoesExclusao(Usuario usuario){
		Map<EnumMenu,EnumTipoPermissao> permissoes = new HashMap<EnumMenu, EnumTipoPermissao>();
		List<Permissao> p =getDelegate().getPermissao(usuario);
		for (Permissao permissao : p) {
			if(!permissoes.containsKey(permissao.getMenu())){
				permissoes.put(permissao.getMenu(), permissao.getExcluir());
			}else if (EnumTipoPermissao.INDETERMINADO.equals(permissoes.get(permissao.getMenu()))) { 
					permissoes.put(permissao.getMenu(),permissao.getExcluir());
			}else if(EnumTipoPermissao.PERMITIDO.equals(permissoes.get(permissao.getMenu()))){
				if(EnumTipoPermissao.NEGADO.equals(permissoes.get(permissao.getMenu()))){
					permissoes.put(permissao.getMenu(),permissao.getExcluir());
				}
			}
		}
		return permissoes;
	}
	public Map<EnumMenu,EnumTipoPermissao> getPermissoesImpressaoo(Usuario usuario){
		Map<EnumMenu,EnumTipoPermissao> permissoes = new HashMap<EnumMenu, EnumTipoPermissao>();
		List<Permissao> p =getDelegate().getPermissao(usuario);
		for (Permissao permissao : p) {
			if(!permissoes.containsKey(permissao.getMenu())){
				permissoes.put(permissao.getMenu(), permissao.getImprimir());
			}else if (EnumTipoPermissao.INDETERMINADO.equals(permissoes.get(permissao.getMenu()))) { 
					permissoes.put(permissao.getMenu(),permissao.getImprimir());
			}else if(EnumTipoPermissao.PERMITIDO.equals(permissoes.get(permissao.getMenu()))){
				if(EnumTipoPermissao.NEGADO.equals(permissoes.get(permissao.getMenu()))){
					permissoes.put(permissao.getMenu(),permissao.getImprimir());
				}
			}
		}
		return permissoes;
	}
}
	

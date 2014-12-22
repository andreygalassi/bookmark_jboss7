package br.agrego.sys.business;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import br.agrego.sys.domain.Definicoes;
import br.agrego.sys.persistence.DefinicoesDAO;
import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class DefinicoesBC extends DelegateCrud<Definicoes, String, DefinicoesDAO> {

	private static final long serialVersionUID = 1L;
	
	@Inject private MenuBC menuBC;
	@Inject private UsuarioBC usuarioBC;
	@Inject private GrupoBC grupoBC;
	@Inject private PermissaoBC permissaoBC;
	
	public HashMap<String, String> getMap(){
		List<Definicoes> l = findAll();
		HashMap<String, String> m =  new HashMap<String, String>();
		for (Definicoes definicoes : l) {
			m.put(definicoes.getChave(), definicoes.getValor());
		}
		return m;
	}
	
	@Startup
	public void inicia() {
		setValor("upload","../upload");
		setValor("upload_file","file");
		setValor("upload_pessoa","pessoa");
		setValor("servidor_producao","0");
		setValor("sys_dir","/var/jboss-6.0.0.Final");
		setValor("war_dir","/var/jboss-6.0.0.Final/deploy/default");
		
		//=====MENU
		menuBC.inicia();
		usuarioBC.inicia();
		grupoBC.inicia();
		permissaoBC.inicia();
		
	}
	
	private void setValor(String chave, String valor){
		if (load(chave.toLowerCase())==null){
			insert(new Definicoes(chave,valor));
		}
	}
	public String getValor(String chave){
		return load(chave.toLowerCase()).getValor();
	}
	
	public List<Object[]> testeHql(String hql) {
		return getDelegate().testeHql(hql);
	}
	public List<?> testeHql2(String hql) {
		return getDelegate().testeHql2(hql);
	}
	
}

package br.agrego.sys.acesscontrol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.SessionScoped;

import br.agrego.sys.exception.MyException;
import br.gov.frameworkdemoiselle.security.User;

import com.vaadin.ui.AbstractComponent;

@SessionScoped
public class Credenciais implements Serializable,User {

	public static String ACAO_ALTERAR 	= "ALTERAR";
	public static String ACAO_CRIAR 	= "CRIAR";
	public static String ACAO_EXCLUIR 	= "EXCLUIR";
	public static String ACAO_IMPRIMIR 	= "IMPRIMIR";
	
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	private String id;
	private Map<String,String> propriedades = new HashMap<String,String>();
	private Set<String> permissoes = new HashSet<String>();
    
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String getId() {
		return id;
	}
	@Override
	public Object getAttribute(Object key) {
		return propriedades.get(key);
	}
	@Override
	public void setAttribute(Object key, Object value) {
		propriedades.put(key+"", value+"");
	}
	public boolean getPermissao(String key) {
		return permissoes.contains(key);
	}
	public void putPermissao(String key, String value) {
		permissoes.add(key+"."+value);
	}
	public Set<String> getPermissoes(){
		return permissoes;
	}
	public void limpar() {
		login = null;
		senha = null;
		id = null;
		propriedades = new HashMap<String,String>();
	}
	
	public void autorizacao(AbstractComponent view, String acao) throws MyException{
		if (!getPermissao(view.getCaption()+"."+acao)){
		 	throw new MyException(2,"Você não tem permissão para executar esta ação!");
		}
	}
    
    
}

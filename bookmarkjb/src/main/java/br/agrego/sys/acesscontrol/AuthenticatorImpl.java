package br.agrego.sys.acesscontrol;

import java.util.Map;

import javax.inject.Inject;

import br.agrego.sys.business.PermissaoBC;
import br.agrego.sys.business.UsuarioBC;
import br.agrego.sys.domain.EnumMenu;
import br.agrego.sys.domain.EnumTipoPermissao;
import br.agrego.sys.domain.Usuario;
import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.security.User;
import br.gov.frameworkdemoiselle.transaction.Transactional;


public class AuthenticatorImpl implements Authenticator  {

	private static final long serialVersionUID = 1L;
	
	@Inject private Credenciais credenciais;
	@Inject	private UsuarioBC usuarioBC;
	@Inject private PermissaoBC permissaoBC;

	@Override
	@Transactional
	public boolean authenticate() {
		boolean authenticated = false;
		
		Usuario usuario = new Usuario();
		usuario.setLogin(credenciais.getLogin());
		usuario.setSenha(credenciais.getSenha());		
		Usuario retorno = usuarioBC.findByLoginAndPass(usuario);
		if( retorno!=null){
			credenciais.setId(retorno.getId().toString());
			
//			Map<EnumMenu,EnumTipoPermissao> pVisualizar = permissaoBC.getPermissoesVisualizacao(retorno);
			Map<EnumMenu,EnumTipoPermissao> pAlterar 	= permissaoBC.getPermissoesAlteracao(retorno);
			Map<EnumMenu,EnumTipoPermissao> pCriar 		= permissaoBC.getPermissoesCriacao(retorno);
			Map<EnumMenu,EnumTipoPermissao> pExcluir 	= permissaoBC.getPermissoesExclusao(retorno);
			Map<EnumMenu,EnumTipoPermissao> pImprimir 	= permissaoBC.getPermissoesImpressaoo(retorno);
			
			for (EnumMenu menu : EnumMenu.asList()) {

				if (pAlterar.containsKey(menu) ){
					if (pAlterar.get(menu).equals(EnumTipoPermissao.PERMITIDO)){
						credenciais.putPermissao(menu.getNome(), "ALTERAR");
					}
				}
				if (pCriar.containsKey(menu) ){
					if (pCriar.get(menu).equals(EnumTipoPermissao.PERMITIDO)){
						credenciais.putPermissao(menu.getNome(), "CRIAR");
					}
				}
				if (pExcluir.containsKey(menu) ){
					if (pExcluir.get(menu).equals(EnumTipoPermissao.PERMITIDO)){
						credenciais.putPermissao(menu.getNome(), "EXCLUIR");
					}
				}
				if (pImprimir.containsKey(menu) ){
					if (pImprimir.get(menu).equals(EnumTipoPermissao.PERMITIDO)){
						credenciais.putPermissao(menu.getNome(), "IMPRIMIR");
					}
				}
			}
//			credenciais.setAttribute("perfil", retorno.getPerfil().getDescricao());
//			credenciais.setSenha(retorno.getSenha());

			authenticated = true;
		}
		return authenticated;
	}

	@Override
	public void unAuthenticate() {
		System.out.println("TENTATIVA FRACASSADA DE AUTENTICAÇÃO");
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return credenciais;
	} 
}

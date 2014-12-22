package br.agrego.sys.acesscontrol;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.Authorizer;

public class AuthorizatorImpl implements Authorizer {

	private static final long serialVersionUID = 1L;

	@Inject private Credenciais credential;
//	@Inject	private SecurityContext securityContext;
	
	@Override
	public boolean hasRole(String role) {
		
//		String r = (String) credential.getAttribute(role);
//		
//		if(r!=null && r.equals("1"))return true;
		
		return false;
	}

	@Override
	public boolean hasPermission(String resource, String operation) {
		// TODO Auto-generated method stub
		return credential.getPermissao(resource+"."+operation);
	}

//    @RequiredPermission 
//    public void requiredPermissionWithoutDeclaredResourceAndOperation() { 
//
//    } 
//
//
//    @RequiredPermission(resource = "contact", operation = "insert") 
//    public void requiredPermissionWithDeclaredResourceAndOperation() { 
//
//    }
	
}

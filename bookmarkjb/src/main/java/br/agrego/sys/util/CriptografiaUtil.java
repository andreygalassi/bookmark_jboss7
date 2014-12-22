package br.agrego.sys.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptografiaUtil {
	
	/**
	 * Metodo que gera um Hash MD5 a partir de uma String.
	 * @param senha
	 * @return
	 */
	public static String criptografaString(String senha) {
		String sen = "";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
		sen = hash.toString(16);
		return sen;
	}
	
	public static void main(String args[]) {
		
	}
	
}

package br.agrego.sys.util;

import java.util.Collection;

final public class DomainUtil {
	private DomainUtil() {}
	
	public static <E> String collectionToString(Collection<E> colecao) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (colecao!=null){
			for (E g : colecao) {
				sb.append(g.toString());
				sb.append(", ");
			}
			if (sb.length()>=2){
				sb.deleteCharAt(sb.length()-1);
				sb.deleteCharAt(sb.length()-1);
			}
		}
		sb.append("]");
		return sb.toString();
		
	}
	
}

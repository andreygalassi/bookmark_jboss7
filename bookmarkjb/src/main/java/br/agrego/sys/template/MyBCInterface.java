package br.agrego.sys.template;

import br.agrego.sys.exception.MyException;

public interface MyBCInterface<T> {

	void save(T bean) throws MyException;
	
}

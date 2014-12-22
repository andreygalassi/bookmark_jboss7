package br.agrego.sys.util.components;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class MoedaField extends TextField{
	
	public MoedaField(){
		super();
		Validator validaValor = new RegexpValidator(
				"^(\\d{1,10}){1}(\\.\\d{3})*((\\,|\\.)(\\d{1}|\\d{2}|\\d{3}))?$", "Deve ser um Valor Válida");
		addValidator(validaValor);
		this.setImmediate(true);
	}
	
//	@Override
//	public Double getValue() { 
//		Object v = super.getValue();
//		try {
//			StringBuilder s = new StringBuilder();
//			s.append(v.toString());
//			return Double.parseDouble(s.toString().replace(',', '.'));
//        } catch (final IllegalArgumentException e) {
//            return null;
//		}
//	}
	
	public Double getValor() {
		Object v = getValue();
		try {
			StringBuilder s = new StringBuilder();
			s.append(v.toString());
			return Double.parseDouble(s.toString().replace(',', '.'));
        } catch (final IllegalArgumentException e) {
            return null;
		}
	}
	
	@Override
	public void setValue(Object newValue){
		if (newValue!=null){
			super.setValue(newValue.toString().replace('.', ','));
		} else {
			super.setValue(null);
		}
	}
}

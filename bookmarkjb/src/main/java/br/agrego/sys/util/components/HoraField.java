package br.agrego.sys.util.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class HoraField extends TextField{
	
	@Override
	public Date getValue() {
		SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm"); 
		Object v = super.getValue();
		try {
			return (Date)formatHora.parse(v.toString());
        } catch (final IllegalArgumentException e) {
            return null;
        } catch (final ParseException e) {
        	return null;
		}
	}
	
}

package br.agrego.sys.util.components;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public class DataHoraColumnGenerator implements ColumnGenerator {

	private static final long serialVersionUID = 1L;

	@Override
	public Component generateCell(Table source, Object itemId, Object columnId) {
		if (itemId!=null && columnId!=null && source.getItem(itemId)!=null){
	        Property prop = source.getItem(itemId).getItemProperty(columnId);
	        if (prop.getType().equals(Date.class) && prop.getValue() != null) {
	        	if (prop.getValue() != null) {
	        		return new Label(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(prop.getValue()));
	        	}
	        }
        }
        return null;
	}

}


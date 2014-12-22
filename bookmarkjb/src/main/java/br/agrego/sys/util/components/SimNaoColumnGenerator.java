package br.agrego.sys.util.components;

import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public class SimNaoColumnGenerator implements ColumnGenerator {

	private static final long serialVersionUID = 1L;

	@Override
	public Component generateCell(Table source, Object itemId, Object columnId) {
		if (itemId!=null && columnId!=null && source.getItem(itemId)!=null){
	        Property prop = source.getItem(itemId).getItemProperty(columnId);
	        if (prop.getType().equals(Boolean.class) && prop.getValue() != null) {
	        	if (prop.getValue() != null) {
	        		if ((Boolean)prop.getValue()){
	        			return new Label("S");
	        		}
	        		return new Label("N");
	        	}
	        }
        }
        return new Label("");
	}

}


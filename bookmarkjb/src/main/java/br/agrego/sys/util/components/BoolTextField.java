package br.agrego.sys.util.components;

import br.agrego.sys.util.FieldFactoryUtil;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class BoolTextField extends GridLayout{
	
	Label caption;
	TextField textField;
	CheckBox check;
	
	public BoolTextField(String caption) {
		this.setColumns(1);
		this.setRows(2);
		
		this.textField = FieldFactoryUtil.createTextField(null);
		this.check = FieldFactoryUtil.createCheckBox(caption);
		this.check.setValue(false);
		this.caption = new Label();
		this.caption.setValue(caption);
		this.caption.setHeight("100px");
		
		this.setHeight("39px");
		this.caption.setHeight("15px");
		this.check.setHeight("15px");
//		this.check.setWidth("16px");
		
		textField.setImmediate(true);
		check.setImmediate(true);
		
//		this.textField.setWidth("100%");
//		this.caption.setWidth("100%");
//		this.caption.setSizeFull();
		
		setImmediate(true);
//		this.setColumnExpandRatio(0, 1f);
//		this.setColumnExpandRatio(1, 0.2f);
		this.setRowExpandRatio(0, 0.4f);
		this.setRowExpandRatio(1, 0.6f);

//		addComponent(this.textField,0,1,1,1);
//		addComponent(this.caption,0,0);
//		addComponent(this.check,1,0);

		addComponent(this.check);
		addComponent(this.textField);
		
		this.textField.setEnabled(false);
		
		this.setComponentAlignment(this.check, Alignment.MIDDLE_LEFT);
		
		addListener2();
    }
	
	private void addListener2() {
		this.check.addListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					textField.setEnabled((Boolean) event.getProperty().getValue());
				}
			});
	}

	public void setAtivo(Boolean ativo){
		this.check.setValue(ativo);
	}
	public Boolean getAtivo(){
		return (Boolean) this.check.getValue();
	}
	public void setValue(String texto){
		this.textField.setValue(texto);
	}
	public String getValue(){
		return (this.textField.getValue()!=null?this.textField.getValue().toString():"");
	}
	public void setCaption(String camption){
		this.caption.setValue(caption);
	}

	public TextField getTextField() {
		return textField;
	}

	public void setTextField(TextField textField) {
		this.textField = textField;
	}

	public CheckBox getCheck() {
		return check;
	}

	public void setCheck(CheckBox check) {
		this.check = check;
	}
	
	
//	@Override
//	public Date getValue() {
//		SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm"); 
//		Object v = super.getValue();
//		try {
//			return (Date)formatHora.parse(v.toString());
//        } catch (final IllegalArgumentException e) {
//            return null;
//        } catch (final ParseException e) {
//        	return null;
//		}
//	}
	
}

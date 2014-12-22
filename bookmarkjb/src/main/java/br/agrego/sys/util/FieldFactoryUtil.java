package br.agrego.sys.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.util.AnnotationLiteral;

import br.agrego.sys.util.components.BoolTextField;
import br.agrego.sys.util.components.HoraField;
import br.agrego.sys.util.components.MoedaField;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Strings;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessItemSelection;
import br.gov.frameworkdemoiselle.vaadin.util.FieldFactory;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.PropertyFormatter;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

final public class FieldFactoryUtil {
	
	private FieldFactoryUtil() {

	}
	
	public static HoraField createHoraField(String caption) {
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);
		String inputPrompt = "HH:mm";
		HoraField field = new HoraField();
		field.setNullRepresentation("");
		setBasicProperties(field, caption);

		if (Strings.isResourceBundleKeyFormat(inputPrompt)) {
			field.setInputPrompt(bundle.getString(Strings.removeBraces(inputPrompt)));
		} else {
			field.setInputPrompt(inputPrompt);
		}

		return field;
	}
	
	private static void setBasicProperties(Field field, String caption) {
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);
		if (Strings.isResourceBundleKeyFormat(caption)) {
			try {
				field.setCaption(bundle.getString(Strings.removeBraces(caption)));
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			field.setCaption(caption);
		}
	}
	
	public static String getBundleFromReference(String caption){
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);
		if (Strings.isResourceBundleKeyFormat(caption)) {
			try {
				return bundle.getString(Strings.removeBraces(caption));
			} catch (Exception e) {
				return caption;
			}
		} else {
			return caption;
		}
	}
	
	public static MoedaField createMoedaField(String caption) {
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);
		String inputPrompt = "0,00";
		MoedaField field = new MoedaField();
		field.setNullRepresentation("");
		setBasicProperties(field, caption);

		if (Strings.isResourceBundleKeyFormat(inputPrompt)) {
			field.setInputPrompt(bundle.getString(Strings.removeBraces(inputPrompt)));
		} else {
			field.setInputPrompt(inputPrompt);
		}
//		Validator validaValor = new RegexpValidator(
//				"^(\\d{1,10}){1}(\\.\\d{3})*((\\,|\\.)(\\d{1}|\\d{2}|\\d{3}))?$", "Deve ser um Valor Válida");
//		field.addValidator(validaValor);
		return field;
	}
	
	public static BoolTextField createBoolTextField(String caption) {
//		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);
		
		BoolTextField field = new BoolTextField(caption);
		field.getTextField().setNullRepresentation("");
//		setBasicProperties(field.getTextField(), caption);

//		if (Strings.isResourceBundleKeyFormat(inputPrompt)) {
//			field.setInputPrompt(bundle.getString(Strings.removeBraces(inputPrompt)));
//		} else {
//			field.setInputPrompt(inputPrompt);
//		}
//		Validator validaValor = new RegexpValidator(
//				"^(\\d{1,10}){1}(\\.\\d{3})*((\\,|\\.)(\\d{1}|\\d{2}|\\d{3}))?$", "Deve ser um Valor Válida");
//		field.addValidator(validaValor);
		return field;
	}


	public static PasswordField createPasswordField(String caption) {
		return (PasswordField) FieldFactory.createPasswordField(caption, caption);
	}
	
	public static TextField createTextField(String inputPrompt, String caption) {
		return FieldFactory.createTextField(inputPrompt, caption);
	}
	public static TextField createTextField(String caption) {
		return createTextField(null,caption);
	}
	@SuppressWarnings("unchecked")
	public static TextField createTextFieldBigDecimal(String caption) {
		TextField field = createTextField(null,caption);
		field.setLocale(new Locale("pt","BR"));
		field.addStyleName("align-right");
		field.setImmediate(true);
		Property property = new Property() {
			private static final long serialVersionUID = 1L;
			private BigDecimal value;
			public Object getValue() {
			    return value;
			}
			public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
			    if (newValue == null) {
			        value = null;
			    } else if (newValue instanceof BigDecimal) {
			        value = (BigDecimal) newValue;
			    } else {
			    	value = new BigDecimal(0.0);
//			        throw new ConversionException();
			    }
			}
			public Class<?> getType() {
			    return BigDecimal.class;
			}
			public boolean isReadOnly() {
			    return false;
			}
			public void setReadOnly(boolean newStatus) {
			    // ignore
			}
		};
		field.setPropertyDataSource(new PropertyFormatter(property) {
			private static final long serialVersionUID = 1L;
			DecimalFormat df = new DecimalFormat("#,##0.00");
			public String format(Object value) {
				df.setParseBigDecimal(true);
				try {
//					System.out.println(value);
	                return df.format((BigDecimal)value);
				} catch (Exception e) {
//					return df.format(new BigDecimal(value.toString()));
					return df.format(null);
				}
            }

            public Object parse(String formattedValue) throws Exception {
				df.setParseBigDecimal(true);
                return df.parse(formattedValue);
            }

        });
		return field;
	}
	@SuppressWarnings("unchecked")
	public static TextField createTextFieldInteger(String caption) {
		TextField field = createTextField(null,caption);
		field.setLocale(new Locale("pt","BR"));
		field.addStyleName("align-right");
		field.setImmediate(true);
		Property property = new Property() {
			private static final long serialVersionUID = 1L;
			private Integer value;
			public Object getValue() {
			    return value;
			}
			public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
			    if (newValue == null) {
			        value = null;
			    } else if (newValue instanceof Integer) {
			        value = (Integer) newValue;
			    } else {
			        throw new ConversionException("");
			    }
			}
			public Class<?> getType() {
			    return Integer.class;
			}
			public boolean isReadOnly() {
			    return false;
			}
			public void setReadOnly(boolean newStatus) {
			    // ignore
			}
		};
		field.setPropertyDataSource(new PropertyFormatter(property) {
			private static final long serialVersionUID = 1L;
			public String format(Object value) {
				try {
	                return value.toString();
				} catch (Exception e) {
					return null;
				}
            }
            public Object parse(String formattedValue) throws Exception {
                return Integer.valueOf(formattedValue);
            }

        });
		return field;
	}
	@SuppressWarnings("unchecked")
	public static TextField createTextFieldLong(String caption) {
		TextField field = createTextField(null,caption);
		field.setLocale(new Locale("pt","BR"));
		field.addStyleName("align-right");
		field.setImmediate(true);
		Property property = new Property() {
			private static final long serialVersionUID = 1L;
			private Long value;
			public Object getValue() {
			    return value;
			}
			public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
			    if (newValue == null) {
			        value = null;
			    } else if (newValue instanceof Integer) {
			        value = (Long) newValue;
			    } else {
			        throw new ConversionException("");
			    }
			}
			public Class<?> getType() {
			    return Long.class;
			}
			public boolean isReadOnly() {
			    return false;
			}
			public void setReadOnly(boolean newStatus) {
			    // ignore
			}
		};
		field.setPropertyDataSource(new PropertyFormatter(property) {
			private static final long serialVersionUID = 1L;
			public String format(Object value) {
				try {
	                return value.toString();
				} catch (Exception e) {
					return null;
				}
            }
            public Object parse(String formattedValue) throws Exception {
                return Integer.valueOf(formattedValue);
            }

        });
		return field;
	}
	
	public static TextArea createTextArea(String inputPrompt, String caption, int rows, int columns) {
		return FieldFactory.createTextArea(inputPrompt, caption, rows, columns);
	}
	public static TextArea createTextArea(String caption, int rows, int columns) {
		return FieldFactory.createTextArea(caption, caption, rows, columns);
	}
		
	public static ComboBox createComboBox(String inputPrompt, String caption, String itemCaptionPropertyId) {
		return FieldFactory.createComboBox(inputPrompt, caption, itemCaptionPropertyId);
	}
	public static ComboBox createComboBox(String caption, String itemCaptionPropertyId) {
		return FieldFactory.createComboBox(null, caption, itemCaptionPropertyId);
	}
	public static ComboBox createComboBox(String caption) {
		return FieldFactory.createComboBox(null, caption, null);
	}
	
	public static PopupDateField createDateField(String inputPrompt, String caption, String format) {
		return FieldFactory.createDateField(inputPrompt, caption, format);
	}
	public static PopupDateField createDateField(String caption, String format) {
		return FieldFactory.createDateField("dd/mm/aa", caption, format);
	}
	public static PopupDateField createDateField(String caption) {
		return FieldFactory.createDateField("dd/mm/aa", caption, "dd/MM/yy");
	}

	public static CheckBox createCheckBox(String caption) {
		return FieldFactory.createCheckBox(null, caption);
	}

	public static Field createCPFField(String caption) {
		return FieldFactory.createCPFField("000.000.000-00", caption);
	}

	public static Field createCNPJField(String caption) {
		return FieldFactory.createFormattedField("00.000.000/0000-00", caption,"000.000.000-00",true);
	}
	
	public static Field createPhoneField(String caption) {
		return FieldFactory.createPhoneField("0000-0000", caption);
	}
	
	public static TextField createFormattedField(String prompt, String caption, final String formato, final boolean direcao) {
		return (TextField) FieldFactory.createFormattedField(prompt, caption, formato, direcao);
	}
	@SuppressWarnings("serial")
	public static TextField createNumberOnlyField(String caption) {
		final TextField listenedField = createTextField(caption);
		listenedField.setImmediate(true);
		listenedField.addListener(new ValueChangeListener() {

			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				if (listenedField.getValue() != null) {
					try {
//						String unidade1 = listenedField.getValue().toString().replaceAll("\\D", "");
						listenedField.setValue(listenedField.getValue().toString().replaceAll("\\D", ""));
					} catch (RuntimeException re) {
						// Notthing to be done!
					}
				}
			}

		});
		return listenedField;
	}
	
	public static TwinColSelect createTwinColSelect(String caption, String itemCaptionPropertyId) {
		TwinColSelect field = new TwinColSelect();
//		field.setNullRepresentation("");
//		field.setLeftColumnCaption(caption);
		
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);
		if (Strings.isResourceBundleKeyFormat(caption)) {
			field.setLeftColumnCaption(bundle.getString(Strings.removeBraces(caption)));
		} else {
			field.setLeftColumnCaption(caption);
		}
		
		field.setRightColumnCaption("SELECIONADOS");
		if (itemCaptionPropertyId!=null){
			field.setItemCaptionPropertyId(itemCaptionPropertyId);
//			field.setItemCaptionMode
		}
//		setBasicProperties(field, caption);
		return field;
	}
	public static TwinColSelect createTwinColSelect(String caption, String itemCaptionPropertyId, int rows, String width) {
		TwinColSelect field = createTwinColSelect(caption,itemCaptionPropertyId);

		field.setImmediate(true);
		field.setMultiSelect(true);
		
		field.setRows(rows);
		field.setWidth(width);
		
		return field;
	}
	
	public static ListSelect createListSelect(String caption) {
		ListSelect field = new ListSelect(caption);
//		field.setNullRepresentation("");
		setBasicProperties(field, caption);

		return field;
	}
	
	public static OptionGroup createOptionGroup(String caption, String itemCaptionPropertyId) {
		OptionGroup field = new OptionGroup(caption);
//		field.setNullRepresentation("");
		setBasicProperties(field, caption);
		if (itemCaptionPropertyId!=null){
			field.setItemCaptionPropertyId(itemCaptionPropertyId);
		}
		return field;
	}

	public static Button createButton(String caption) {
		Button field = new Button(caption);
//		field.setDisableOnClick(true);
		setBasicProperties(field, caption);
		return field;
	}
	public static Button createButton(String caption,Button.ClickListener view) {
		Button field = new Button(caption);
		field.addListener(view);
//		field.setDisableOnClick(true);
		setBasicProperties(field, caption);
		return field;
	}

	final public static SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yy");
	final public static SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");
	final public static SimpleDateFormat formatDataHora = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	public static Table createTabelaFormatada(AbstractComponent view){
		return createTabelaFormatada(view, null);
	}
	@SuppressWarnings("serial")
	public static Table createTabelaFormatada(final AbstractComponent view, String caption){
		Locale localeBR = new Locale("pt", "BR");
		final Table tabela = new Table(caption)
			{
				private static final long serialVersionUID = 1L;
	
				@Override
			    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
			        // Format by property type
			        if (property.getType() == Date.class && property.getValue()!=null) {
			        	if("data".equals(colId)) 		return formatData.format((Date)property.getValue());
			        	else if("hora".equals(colId)) 	return formatHora.format((Date)property.getValue());
			        	else 							return formatDataHora.format((Date)property.getValue());
			        	
			        }else if(property.getType() == Boolean.class && property.getValue()!=null){
			        	if ((Boolean)property.getValue()) return "SIM";
			        	else return "NÃO";
			        }
	
			        return super.formatPropertyValue(rowId, colId, property);
			    }
			};
			
			tabela.setLocale(localeBR);
			tabela.setImmediate(true);
			tabela.addListener(new ValueChangeListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
//					Object item = event.getProperty().getValue();
//					if (item != null) {
						Beans.getBeanManager().fireEvent(view, new AnnotationLiteral<ProcessItemSelection>() {});
//					}
				}

			});
			return tabela;
			
//			tabela.setCellStyleGenerator(new Table.CellStyleGenerator() {
//				@Override
//			      public String getStyle(Object itemId, Object propertyId) {
//					if ("veiculo".equals(propertyId)) {
//				          Item item = tabela.getItem(itemId);
//				          if (item!=null && item.getItemProperty("veiculoCor")!=null && item.getItemProperty("veiculoCor").getValue()!=null){
//					          return "veiculo " + item.getItemProperty("veiculoCor").getValue();
//				          }
//				    }else if ("servico".equals(propertyId)) {
//						Item row = tabela.getItem(itemId);
//						if (row!=null && (Boolean)row.getItemProperty("privativo").getValue()){
//							return "privativo";
//						}
//					}else if ("paxNome".equals(propertyId)) {
//						Item row = tabela.getItem(itemId);
//						if (row!=null && (Boolean)row.getItemProperty("vip").getValue()){
//							return "vip";
//						}
//					}
////					if (itemId!=null && propertyId!=null){
////						Item row = tabela.getItem(itemId);
////						if (row!=null && row.getItemProperty("statusFile")!=null && 
////								row.getItemProperty("statusFile").getValue()!=null ){
////							return "alerta";
////						}
////					}
//					else if (propertyId == null) { //para linha inteira
//						Item row = tabela.getItem(itemId);
//						if (row!=null && row.getItemProperty("statusFile")!=null && 
//								row.getItemProperty("statusFile").getValue()!=null &&
//								(EnumFileStatus)row.getItemProperty("statusFile").getValue()!=EnumFileStatus.APROVADO) {
//							if ((EnumFileStatus)row.getItemProperty("statusFile").getValue()==EnumFileStatus.PENDENTE_APROVACAO ||
//									(EnumFileStatus)row.getItemProperty("statusFile").getValue()==EnumFileStatus.PENDENTE_COMPROVANTE){
//								return "alerta";
//							}else if ((EnumFileStatus)row.getItemProperty("statusFile").getValue()==EnumFileStatus.RETIDO){
//								return "retido";
//							}else if ((EnumFileStatus)row.getItemProperty("statusFile").getValue()==EnumFileStatus.A_COBRAR){
//								return "acobrar";
//							}
//						}
////						if (row!=null){
////							return "alerta";
////						}
////						if (row!=null && (Boolean)row.getItemProperty("privativo").getValue()){
////							return "privativo";
////						}
//					}
//			        return null;
//			      }
//				
//			    });
	}
	
	
}

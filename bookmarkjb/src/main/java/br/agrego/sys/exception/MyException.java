package br.agrego.sys.exception;

import com.vaadin.ui.AbstractComponent;


public class MyException extends Exception   {
    /*public static final int TYPE_HUMANIZED_MESSAGE = 1;
    public static final int TYPE_WARNING_MESSAGE = 2;
    public static final int TYPE_ERROR_MESSAGE = 3;
    public static final int TYPE_TRAY_NOTIFICATION = 4;*/
	
	private static final long serialVersionUID = 1L;

	private int notification;
	private String subMessage;
	
	public static MyException insert() {return new MyException(1,"REGISTRO CRIADO COM SUCESSO!");}
	public static MyException update() {return new MyException(1,"REGISTRO SALVO COM SUCESSO!");}
	public static MyException delete() {return new MyException(1,"REGISTRO EXCLUIDO COM SUCESSO!");}
	public static MyException erro() {return new MyException(3,"ERRO AO EXECUTAR AÇÃO!");}
	
	public MyException(String message) {
		super(message);
	}

	public MyException(int notification, String message) {
		super(message);
		this.notification = notification;
	}
	public MyException(int notification, String caption, String message) {
		super(caption);
		subMessage = message;
		this.notification = notification;
	}

	public int getNotification() {
		return notification;
	}

	public void setNotification(int notification) {
		this.notification = notification;
	}
	
	public void showMessage(AbstractComponent view){
		if (subMessage!=null && subMessage.length()>0){
			view.getWindow().showNotification(getMessage(), "</br>"+subMessage, getNotification());
		}else{
			view.getWindow().showNotification(getMessage(), getNotification());
		}
	}

}
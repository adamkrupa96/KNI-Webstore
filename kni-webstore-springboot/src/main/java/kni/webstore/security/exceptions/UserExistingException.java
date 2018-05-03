package kni.webstore.security.exceptions;

public class UserExistingException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public UserExistingException(String msg) {
		super(msg);
	}
}

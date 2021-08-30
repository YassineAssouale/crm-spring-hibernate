package crm.hibernate.exception;

public class MyDaoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3394807140432681802L;
	
	public MyDaoException(String msg) {
		super(msg);
	}
	
	public MyDaoException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public MyDaoException(Throwable cause) {
		super(cause);
	}

}

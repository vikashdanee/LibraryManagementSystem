package librarysystem.util;

public class ServiceResponse extends Exception{

	private static final long serialVersionUID = 610864059724370848L;
	private Boolean success;
	private String message;
	private static final String RUNTIME_EXCEPTION = "Someting went wrong. Please contact to administrator";
	private Object data; 
	
	public ServiceResponse(Boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	public ServiceResponse(Boolean success, String message, Object data) {
		super();
		this.success = success;
		this.message = message;
		this.data = data; 
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static String getRuntimeException(){
		return RUNTIME_EXCEPTION;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	

}

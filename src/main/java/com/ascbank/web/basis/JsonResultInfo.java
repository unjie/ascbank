/**
 *
 */
package com.ascbank.web.basis;

/**
 * @author jie
 *
 */
public class JsonResultInfo {
	
	private Object	data;

	private Object	error;

	private String	message;

	private boolean	success	= false;
	
	private String	url;

	public Object getData() {
		return data;
	}
	
	public Object getError() {
		return error;
	}
	
	public String getMessage() {
		return message;
	}

	public String getUrl() {
		return url;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setError(Object error) {
		this.error = error;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "JsonResultInfo [data=" + data + ", error=" + error + ", message=" + message + ", success=" + success + "]";
	}

}

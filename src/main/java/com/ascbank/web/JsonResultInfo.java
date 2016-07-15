/**
 *
 */
package com.ascbank.web;

/**
 * @author jie
 *
 */
public class JsonResultInfo {

	private Object	data;

	private Object	error;

	private String	message;

	private boolean	success;

	public Object getData() {
		return data;
	}

	public Object getError() {
		return error;
	}

	public String getMessage() {
		return message;
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

}

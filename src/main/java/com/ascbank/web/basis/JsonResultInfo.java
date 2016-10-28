/**
 *
 */
package com.ascbank.web.basis;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author jie
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class JsonResultInfo {
	
	private Object				data;

	private Object				error;

	private String				message;

	private Map<String, Object>	other	= new HashMap<String, Object>();

	// private String url;

	private boolean				success	= false;
	
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

	@JsonAnySetter
	public void set(String name, Object value) {
		other.put(name, value);
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

	@Override
	public String toString() {
		return "JsonResultInfo [data=" + data + ", error=" + error + ", message=" + message + ", success=" + success + "]";
	}

}

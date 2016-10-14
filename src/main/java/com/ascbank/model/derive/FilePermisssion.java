/**
 *
 */
package com.ascbank.model.derive;

import com.ascbank.model.base.PKEntity;

/**
 * @author jie
 *
 */
public class FilePermisssion extends PKEntity<String> {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 1729754902791517448L;
	private String				permission;
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
}

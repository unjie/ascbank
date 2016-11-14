package com.ascbank.model.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Page<T> extends LinkedList<T> implements Serializable {

	/**
	 *
	 */
	private static final long	serialVersionUID	= -152119483848526939L;

	private int					total;

	public Page(Collection<T> c, int total) {
		super(c);
		this.total = total;
	}

	public List<T> getRows() {
		return this;
	}

	public int getTotal() {
		return total;
	}

}
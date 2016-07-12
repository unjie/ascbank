package com.ascbank.model.base;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.domain.Persistable;

public class PKEntity<PK extends Serializable> implements Persistable<PK> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7450672136743027882L;

	@Field
	private PK id;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		PKEntity<?> that = (PKEntity<?>) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.domain.Persistable#getId()
	 */
	@Override
	public PK getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		int hashCode = 17;

		hashCode += null == getId() ? 0 : getId().hashCode() * 31;

		return hashCode;
	}

	/**
	 * Must be {@link Transient} in order to ensure that no JPA provider
	 * complains because of a missing setter.
	 * 
	 * @see DATAJPA-622
	 * @see org.springframework.data.domain.Persistable#isNew()
	 */
	@Override
	public boolean isNew() {
		return null == getId();
	}

	/**
	 * Sets the id of the entity.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(final PK id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
	}
}
/**
 *
 */
package com.ascbank.web;

import java.io.Serializable;

import com.ascbank.model.base.TreeEntity;
import com.ascbank.web.basis.BaseInterfaceController;

/**
 * @author jie
 *
 */
public interface ProjectController<T extends Serializable, E extends TreeEntity<T, E>> extends BaseInterfaceController<T, E> {

}

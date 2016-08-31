/**
 *
 */
package com.ascbank.web;

import java.io.Serializable;

import com.ascbank.model.base.PKEntity;
import com.ascbank.web.basis.BaseInterfaceController;

/**
 * @author jie
 *
 */
public interface GanttController<T extends Serializable, E extends PKEntity<T>> extends BaseInterfaceController<T, E> {

}

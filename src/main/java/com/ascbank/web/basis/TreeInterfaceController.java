package com.ascbank.web.basis;

import java.io.Serializable;

import com.ascbank.model.base.TreeEntity;

public interface TreeInterfaceController<T extends Serializable, E extends TreeEntity<T, E>> extends BaseInterfaceController<T, E> {
	
	JsonResultInfo children(T stem);
	
	// JsonResultInfo destroy(String entity);

	JsonResultInfo destroyStemTree(String stem);
	
	JsonResultInfo stem(String stem);
	
	JsonResultInfo treeChildren(String stem);

	JsonResultInfo treeChildren(T parentId);
}

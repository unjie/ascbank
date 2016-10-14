package com.ascbank.service;

import java.io.Serializable;

import com.ascbank.model.base.PKEntity;
import com.ascbank.service.basis.BaseInterfaceService;

public interface ArticleService<T extends Serializable, E extends PKEntity<T>> extends BaseInterfaceService<T, E> {
	
	public E readMenuIdForArticle(T menuId);

}
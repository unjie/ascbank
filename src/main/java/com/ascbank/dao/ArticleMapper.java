package com.ascbank.dao;

import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.model.Article;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface ArticleMapper extends BaseInterfaceDao<Long, Article> {
	
	Article selelctByMenuId(Long menuId);

}
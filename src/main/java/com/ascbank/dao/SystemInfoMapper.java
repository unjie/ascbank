package com.ascbank.dao;

import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.model.SystemInfo;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface SystemInfoMapper extends BaseInterfaceDao<Long, SystemInfo> {

}
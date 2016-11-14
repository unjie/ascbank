package com.ascbank.service;

import java.io.Serializable;

import com.ascbank.model.Project;
import com.ascbank.model.base.TreeEntity;
import com.ascbank.service.basis.TreeInterfaceService;

public interface ProjectService<T extends Serializable, E extends TreeEntity<T, E>> extends TreeInterfaceService<Long, Project> {

}
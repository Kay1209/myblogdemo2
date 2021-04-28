package com.oracle.kays.service;



import com.oracle.kays.entity.Catalog;
import com.oracle.kays.mapper.CatalogMapper;
import com.oracle.kays.repository.CatalogRepository;

import java.util.List;
import java.util.Optional;

public interface CatalogService extends BaseService<Catalog,Integer, CatalogRepository, CatalogMapper> {

    //查询所有的课程版块返回课程版块列表
    Optional<List<Catalog>> queryAllCatalogList();
}

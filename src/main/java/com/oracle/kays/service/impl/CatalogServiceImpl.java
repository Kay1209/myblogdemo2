package com.oracle.kays.service.impl;


import com.oracle.kays.entity.Catalog;
import com.oracle.kays.mapper.CatalogMapper;
import com.oracle.kays.repository.CatalogRepository;
import com.oracle.kays.service.CatalogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogServiceImpl extends BaseServiceImpl<Catalog,Integer, CatalogRepository, CatalogMapper> implements CatalogService {


    @Override
    public Optional<List<Catalog>> queryAllCatalogList() {
       return selectList(null);
       //或者
       //return findAll();
    }
}

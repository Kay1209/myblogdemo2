package com.oracle.kays.service;


import com.oracle.kays.entity.Items;
import com.oracle.kays.mapper.ItemsMapper;
import com.oracle.kays.repository.ItemsRepository;

public interface ItemsService extends BaseService<Items,Integer, ItemsRepository, ItemsMapper> {
}

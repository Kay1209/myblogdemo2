package com.oracle.kays.service.impl;


import com.oracle.kays.entity.Items;
import com.oracle.kays.mapper.ItemsMapper;
import com.oracle.kays.repository.ItemsRepository;
import com.oracle.kays.service.ItemsService;
import org.springframework.stereotype.Service;

@Service
public class ItemsServiceImpl extends BaseServiceImpl<Items,Integer, ItemsRepository, ItemsMapper> implements ItemsService {
}

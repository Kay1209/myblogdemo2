package com.oracle.kays.service.impl;

import com.oracle.kays.entity.QuestionType;
import com.oracle.kays.mapper.QuestionTypeMapper;
import com.oracle.kays.repository.QuestionTypeRepository;
import com.oracle.kays.service.QuestionTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionTypeServiceImpl extends BaseServiceImpl<QuestionType,Integer, QuestionTypeRepository, QuestionTypeMapper> implements QuestionTypeService {


    @Override
    public Optional<List<QuestionType>> queryAllQuestionTypes() {
        return selectList(null);
    }
}

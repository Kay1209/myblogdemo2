package com.oracle.kays.service;



import com.oracle.kays.entity.QuestionType;
import com.oracle.kays.mapper.QuestionTypeMapper;
import com.oracle.kays.repository.QuestionTypeRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionTypeService extends BaseService<QuestionType,Integer, QuestionTypeRepository, QuestionTypeMapper> {

    //查询所有的文章类型列表
    Optional<List<QuestionType>> queryAllQuestionTypes();
}

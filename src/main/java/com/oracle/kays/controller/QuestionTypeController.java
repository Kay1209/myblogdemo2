package com.oracle.kays.controller;

import com.oracle.kays.entity.QuestionType;
import com.oracle.kays.json.JsonData;
import com.oracle.kays.json.JsonResult;
import com.oracle.kays.service.QuestionTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@Api("文章类型模块接口")
@RequestMapping("questiontype")
public class QuestionTypeController extends BaseController {

    @Resource
    private QuestionTypeService questionTypeService;

    @GetMapping("/")
    @ApiOperation("查询所有的文章类型列表")
    public JsonData queryAllQuestionTypes(){
        Optional<List<QuestionType>> optional =  questionTypeService.queryAllQuestionTypes();
        if(optional.isPresent()){
            return JsonResult.success(optional.get(),"查询文章类型列表成功!");
        }
        return JsonResult.fail("查询文章类型列表失败!");
    }
}

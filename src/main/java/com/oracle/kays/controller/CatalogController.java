package com.oracle.kays.controller;

import com.oracle.kays.entity.Catalog;
import com.oracle.kays.json.JsonData;
import com.oracle.kays.json.JsonResult;
import com.oracle.kays.service.CatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@Api("版块模块接口")
@RestController
@RequestMapping("catalog")
public class CatalogController extends BaseController {

    @Resource
    private CatalogService catalogService;

    //查询所有的版块列表
    @ApiOperation("查询所有课程版块列表")
    @GetMapping("/")
    public JsonData queryCatalogList(){

        List<Catalog> catalogList = null;
//        if(redisTemplate.opsForValue().get("catalogList")==null){
            Optional<List<Catalog>> optional = catalogService.queryAllCatalogList();
            //保存到redis缓存里面
            if(optional.isPresent()) {
                catalogList = optional.get();
//                redisTemplate.opsForValue().set("catalogList", catalogList);
            }else{
                return JsonResult.fail("查询课程版块列表失败！");
            }

//        }
        //缓存中有,直接取。
//        catalogList = (List<Catalog>) redisTemplate.opsForValue().get("catalogList");
        return JsonResult.success(catalogList,"查询课程版块列表成功！");

    }

}

package com.oracle.kays.controller;


import com.oracle.kays.json.JsonData;
import com.oracle.kays.json.JsonResult;
import com.oracle.kays.service.ChapterService;
import com.oracle.kays.view.ChapterTreeMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@Api("章节模块接口")
@RequestMapping("chapter")
public class ChapterController extends BaseController {

    @Resource
    private ChapterService chapterService;

    @ApiOperation("获取指定课程编号的章节树形菜单接口")
    @GetMapping("/{cid}")
    public JsonData queryCourseChapterTreeMenuByCid(@PathVariable("cid") String cid){
        List<ChapterTreeMenu> ChapterTreeMenuList = null;
        try {
//            if (redisTemplate.opsForValue().get("chaptermenu_" + cid) == null) {
                Optional<List<ChapterTreeMenu>> optional = chapterService.initChapterTreeMenuByCidAndPid(cid, -1);
                if (optional.isPresent()) {
//                    redisTemplate.opsForValue().set("chaptermenu_" + cid, optional.get());

                }else{
                    return JsonResult.fail("课程章节树形菜单初始化失败！");
                }
//            }
//            ChapterTreeMenuList = (List<ChapterTreeMenu>) redisTemplate.opsForValue().get("chaptermenu_"+cid);
            ChapterTreeMenuList=optional.get();
            return JsonResult.success(ChapterTreeMenuList, "课程章节树形菜单初始化成功！");
        }catch(Exception ex){
            return JsonResult.fail("课程章节树形菜单初始化失败！");
        }

    }
}

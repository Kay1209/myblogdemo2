package com.oracle.kays.controller;


import com.oracle.kays.entity.Course;
import com.oracle.kays.json.JsonData;
import com.oracle.kays.json.JsonResult;
import com.oracle.kays.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@Api("课程模块接口")
@RestController
@RequestMapping("course")
public class CourseController extends BaseController {

    @Resource
    private CourseService courseService;

    @ApiOperation("查询所有的课程列表")
    @GetMapping("/")
    public JsonData queryAllCourseList(){
        List<Course> courseList = null;
//        if(redisTemplate.opsForValue().get("coursegList")==null){
            Optional<List<Course>> optional = courseService.queryAllCourseList();
            //保存到redis缓存里面
            if(optional.isPresent()) {
                courseList = optional.get();
//                redisTemplate.opsForValue().set("coursegList", courseList);
            }else{
                return JsonResult.fail("查询所有课程列表失败！");
            }

//        }
        //缓存中有,直接取。
//        courseList = (List<Course>) redisTemplate.opsForValue().get("coursegList");
        return JsonResult.success(courseList,"查询所有课程列表成功！");
    }

    @ApiOperation("根据课程版块查询所有的课程列表")
    @GetMapping("/{catalogId}")
    public JsonData queryAllCourseList(@PathVariable("catalogId") String catalogId){
        List<Course> courseList = null;
//        if(redisTemplate.opsForValue().get("coursegList_"+catalogId)==null){
            Optional<List<Course>> optional = courseService.queryAllCourseListByCatalogId(catalogId);
            //保存到redis缓存里面
            if(optional.isPresent()) {
                courseList = optional.get();
//                redisTemplate.opsForValue().set("coursegList_"+catalogId, courseList);
            }else{
                return JsonResult.fail("查询指定版块的所有课程列表失败！");
            }

//        }
        //缓存中有,直接取。
//        courseList = (List<Course>) redisTemplate.opsForValue().get("coursegList_"+catalogId);
        return JsonResult.success(courseList,"查询指定版块的所有课程列表成功！");
    }

    @ApiOperation("根据课程编号查询课程资料")
    @GetMapping("/details/{cid}")
    public JsonData queryCourseByCid(@PathVariable("cid") int cid){
       Optional<Course> optional =  courseService.queryCourseByCid(cid);
       if(optional.isPresent()){
           return JsonResult.success(optional.get(),"查询课程资料成功！");
       }
       return JsonResult.fail("查询课程资料失败！");
    }

    @GetMapping("/top/{num}")
    @ApiOperation("查询最新发布的前num个教程")
    public JsonData queryTopNumberCourses(@PathVariable("num") int num){
        Optional<List<Course>> optional = courseService.queryTopNumberCourseList(num);
        if(optional.isPresent()){
            return JsonResult.success(optional.get(),"查询最新发布课程列表成功！");
        }
        return JsonResult.fail("查询最新发布课程列表失败！");
    }
}

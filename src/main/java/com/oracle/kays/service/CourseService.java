package com.oracle.kays.service;



import com.oracle.kays.entity.Course;
import com.oracle.kays.mapper.CourseMapper;
import com.oracle.kays.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

public interface CourseService extends BaseService<Course,Integer, CourseRepository, CourseMapper> {


    //查询所有的课程列表
    Optional<List<Course>> queryAllCourseList();

    //根据课程版块查询所有课程列表
    Optional<List<Course>> queryAllCourseListByCatalogId(String catalogId);

    //根据课程编号返回课程资料
    Optional<Course> queryCourseByCid(Integer cid);

    //查询最新的发布的前8个课程
    Optional<List<Course>> queryTopNumberCourseList(int number);
}

package com.oracle.kays.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.oracle.kays.entity.Course;
import com.oracle.kays.mapper.CourseMapper;
import com.oracle.kays.repository.CourseRepository;
import com.oracle.kays.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course,Integer, CourseRepository, CourseMapper> implements CourseService {


    @Override
    public Optional<List<Course>> queryAllCourseList() {

        return selectList(null);
        //或者
        //return findAll();
    }

    @Override
    public Optional<List<Course>> queryAllCourseListByCatalogId(String catalogId) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>();
        queryWrapper.eq("catalog_id",catalogId);
        return selectList(queryWrapper);
    }

    @Override
    public Optional<Course> queryCourseByCid(Integer cid) {

        return selectById(cid);
        //或者
        //return findById(cid);
    }

    @Override
    public Optional<List<Course>> queryTopNumberCourseList(int number) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit "+number);
        return selectList(queryWrapper);
    }
}

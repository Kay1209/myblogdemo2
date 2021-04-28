package com.oracle.kays.service;

/*
* BaseService里面，封装了所有服务层的一些公共的操作，CRUD。
*
* */

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.oracle.kays.entity.BaseEntity;
import com.oracle.kays.repository.BaseRepository;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


/**
 *
 * @param <T> 表示具体的类型
 * @param <I> 表示该类型的主键类型
 * @param <R> 表示该类型的Repository层的实现类型。
 */
public interface BaseService<T extends BaseEntity, I extends Serializable, R extends BaseRepository<T, I>, M extends BaseMapper> {

    /********************以下是MybatisPlus的方法**************************/
    /*
    * 以insert/update/select/delete开头的都是MybatisPlus的方法
    * */

    //查询单个对象
    Optional<T> selectById(I id);

    //添加单个对象
    //添加成功返回true, 添加失败返回false
    boolean insert(T obj);

    //批量添加多个对象
    boolean insertBatch(List<T> objs);

    //更新单个对象
    //这个对象一定要带主键 ,更新的主键不存在会返回false
    boolean updateById(T obj);

    //删除单个对象
    //这个对象一定要带主键, 删除的主键即便不存在也会返回true
    boolean deleteById(I id);

    //删除多个对象
    boolean deleteBatch(List<I> ids);

    //查询带条件的集合
    Optional<List<T>> selectList(Wrapper<T> wrapper);


    /***********************以下是JPA的方法******************************/
    /*
    * 以save/modify/find/remove开头的都是JPA的方法
    *
    * */
    //查询单个对象
    Optional<T> findById(I id);

    //添加单个对象
    //添加成功返回true, 添加失败返回false
    boolean save(T obj);

    //批量添加多个对象
    boolean saveBacth(List<T> objs);

    //更新单个对象
    //这个对象一定要带主键 ,更新的主键不存在会返回false
    boolean modifyById(I id, T obj);

    //删除单个对象
    //这个对象一定要带主键, 删除的主键即便不存在也会返回true
    boolean removeById(I id);

    boolean removeBatch(List<I> ids);


    //查询带条件的集合
    Optional<List<T>> findList(Specification<T> criteria);

}

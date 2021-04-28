package com.oracle.kays.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.oracle.kays.entity.BaseEntity;
import com.oracle.kays.repository.BaseRepository;
import com.oracle.kays.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class BaseServiceImpl<T extends BaseEntity, I extends Serializable, R extends BaseRepository<T, I>, M extends BaseMapper> implements BaseService<T, I, R, M> {

    @Autowired
    protected R dao;//传统Jpa

    @Autowired
    protected M mapper;//mybatisplus

    //for Mybatis////////////////////////////////////////////////////////////
    @Transactional
    @Override
    public boolean insert(T obj) {
        int result = mapper.insert(obj);
        if(result>0){
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean insertBatch(List<T> objs) {

        for(T obj: objs){
            if(!insert(obj)){
                return false;
            }
        }
        return true;
    }

    @Transactional
    @Override
    public boolean deleteById(I id) {
        int result = mapper.deleteById(id);
        return true;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean deleteBatch(List<I> ids) {

        for(I id: ids){
            deleteById(id);
        }
        return true;
    }



    @Transactional
    @Override
    public boolean updateById(T obj) {
        //updateById有bug,必须把modifyTime重新置为空，否是按照select查询出来的modifyTime值更新，置空后则按照自动填充的规则更新。
        obj.setModifyTime(null);
        int result = mapper.updateById(obj);
        if(result>0){
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Optional<T> selectById(I id) {
        T obj = (T)mapper.selectById(id);
        if(obj!=null){
            return Optional.of(obj);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<List<T>> selectList(Wrapper<T> wrapper) {

        List<T> list = mapper.selectList(wrapper);

        if(list!=null&&list.size()>0){
            return Optional.of(list);
        }
        return Optional.empty();
    }


    //for JPA/////////////////////////////////////////////////////
    @Transactional
    @Override
    public Optional<T> findById(I id) {

        Optional<T> optional  = dao.findById(id);
        if(optional.isPresent()){
            //判断是否是已经删除的记录
            T obj = optional.get();
            if(obj.isFlag()){
                return optional;
            }
        }
        return Optional.empty();
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean save(T obj) {
        System.out.println("开始执行BaseServiceImpl-->save()");
        Date d = new Date();
        DateTime dt = DateTime.of(d);
        obj.setCreateTime(dt.toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("createTime is :"+obj.getCreateTime());
        obj.setModifyTime(dt.toString("yyyy-MM-dd HH:mm:ss"));
        obj.setFlag(true);
        dao.save(obj);
        return true;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean saveBacth(List<T> objs) {
        for(T obj: objs){
            save(obj);
        }
        return true;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean modifyById(I id,T obj) {
        Optional<T> optional = findById(id);
        if(optional.isPresent()) {
            Date d = new Date();
            DateTime dt = DateTime.of(d);
            obj.setModifyTime(dt.toString("yyyy-MM-dd HH:mm:ss"));
            //更新乐观锁版本号
            obj.setVersion(obj.getVersion() + 1);
            dao.save(obj);
            return true;
        }
        return false;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean removeById(I id) {
        Optional<T> obj = findById(id);
        if(obj.isPresent()) {
            T target = obj.get();
            target.setFlag(false);
            //dao.deleteById(id); //注意这个deleteById(id)是真正的物理删除。
            dao.save(target);
            return true;
        }
        return true;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean removeBatch(List<I> ids) {
        for(I id: ids){
            removeById(id);
        }
        return true;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Optional<List<T>> findList(Specification<T> criteria) {

        //增加查询flag=true的查询条件
        Specification<T> deleteFlagCriteria = new Specification<T>(){
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.equal(root.get("flag"),true);
                return criteriaBuilder.and(p1);
            }
        };

        if(criteria==null){
            criteria = deleteFlagCriteria;
        }else{
            criteria = criteria.and(deleteFlagCriteria);
        }
        List<T> list =  dao.findAll(criteria);
        if(list!=null && list.size()>0){
            return Optional.of(list);
        }

        return Optional.empty();
    }
}

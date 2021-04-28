package com.oracle.kays.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.oracle.kays.entity.Members;
import com.oracle.kays.mapper.MembersMapper;
import com.oracle.kays.repository.MembersRepository;
import com.oracle.kays.service.MembersService;
import com.oracle.kays.util.MyUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MembersServiceImpl extends BaseServiceImpl<Members,Integer, MembersRepository, MembersMapper> implements MembersService {


    @Override
    public Optional<Members> login(String identify, String password) {
        //暂时考虑只使用手机号码登录。
        QueryWrapper<Members> queryWrapper = new QueryWrapper<Members>();

        log.info("原始密码是："+password);
        log.info("用户标识符是："+identify);

        if(MyUtils.isPhoneLegal(identify)){
            queryWrapper.eq("mobile",identify);
        }else if(MyUtils.isEmailLegal(identify)){
            queryWrapper.eq("email",identify);
        }else{
            queryWrapper.eq("username",identify);
        }
        //queryWrapper.eq("password",password);
        List<Members> membersList = mapper.selectList(queryWrapper);
        if(membersList!=null&&membersList.size()>0){

            log.info("找到登录用户资料...");

            Members temp = membersList.get(0);
            System.out.println("登录用户资料是："+temp);

            if(MyUtils.checkPassword(password,temp.getPassword())){
                return Optional.of(temp);
            }
            /*if(MyUtils.checkPassword(password,temp.getPassword())){
                return Optional.of(temp);
            }*/
        }
        return Optional.empty();
    }

    //管理员登录专用
    @Override
    public Optional<Members> adminlogin(String identify, String password) {
        QueryWrapper<Members> queryWrapper = new QueryWrapper<Members>();
        queryWrapper.eq("username",identify);
        List<Members> membersList = mapper.selectList(queryWrapper);
        if(membersList!=null&&membersList.size()>0){
            log.info("找到登录用户资料...");

            Members temp = membersList.get(0);
            System.out.println("登录用户资料是："+temp);

            if(MyUtils.checkPassword(password,temp.getPassword())){
                return Optional.of(temp);
            }
        }
        return Optional.empty();
    }


    //牵扯到数据更新的操作，都应该添加事务管理
    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean reg(Members member) {
        //对密码进行加密处理
        member.setPassword(MyUtils.encodePassword(member.getPassword()));
        //随机分配一个用户名
        member.setUsername(MyUtils.generateRandomUsername());
        //姓名默认是男
        member.setGender("男");
        //用户注册类型默认是0，表示学生
        member.setType("0");

        return insert(member);
    }


    @Override
    public boolean checkMobileOrEmailExist(Members member) {
        QueryWrapper<Members> queryWrapper = new QueryWrapper<Members>();
        //select * from members where mobile = xxx or email = xxxx
        //queryWrapper.eq("mobile",member.getMobile()).or().eq("email",member.getEmail());
        if(!"".equals(member.getMobile())){
            queryWrapper.eq("mobile",member.getMobile());
        }

        if(!"".equals(member.getEmail())){
            queryWrapper.eq("email",member.getEmail());
        }

        List<Members> membersList = mapper.selectList(queryWrapper);
        if(membersList!=null&&membersList.size()>0){
            return true; //说明该手机号码或者电子邮件已经被注册。
        }
        return false;
    }

    @Override
    public Optional<Members> queryUsersByOpenId(String openId) {
        QueryWrapper<Members> queryWrapper = new QueryWrapper<Members>();
        queryWrapper.eq("open_id",openId);
        Optional<List<Members>> membersList = selectList(queryWrapper);
        if(membersList.isPresent()){
            return Optional.of(membersList.get().get(0));
        }
        return Optional.empty();
    }
}



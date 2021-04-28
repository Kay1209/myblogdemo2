package com.oracle.kays.service;



import com.oracle.kays.entity.Members;
import com.oracle.kays.mapper.MembersMapper;
import com.oracle.kays.repository.MembersRepository;

import java.util.Optional;

public interface MembersService extends BaseService<Members,Integer, MembersRepository, MembersMapper> {

    //用户登录的方法。
    //identify有可能是手机，电子邮箱，用户名。
    Optional<Members> login(String identify, String password);

    //管理员登录
    Optional<Members> adminlogin(String identify, String password);

    //用户注册方法
    boolean reg(Members member);

    //检查手机号码或者电子邮箱是否已经被注册
    boolean checkMobileOrEmailExist(Members member);

    Optional<Members> queryUsersByOpenId(String openId);
}

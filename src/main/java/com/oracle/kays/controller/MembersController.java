package com.oracle.kays.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;


import com.oracle.kays.entity.Members;
import com.oracle.kays.json.JsonData;
import com.oracle.kays.json.JsonResult;
import com.oracle.kays.service.MembersService;


import com.oracle.kays.util.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

//会员的业务逻辑接口
@Api("会员模块辑接口")
@RestController
//@Controller
@RequestMapping("members")
@Slf4j
public class MembersController {//不使用redis缓存的情况下就没必要继承BaseController

    @Autowired
    private MembersService membersService;


    //
//    //牛牛编程网的APPID
//    @Value("${tencent.connect.appid}")
//    private String APPID ;
//    //牛牛编程网的APPKEY
//    @Value("${tencent.connect.appkey}")
//    private String APPKEY ;
//    //牛牛编程网的第三方登录回调地址
//    @Value("${tencent.connect.recall}")
//    private String RECALL ;
//
//
    @ApiOperation("查询所有用户")
    @GetMapping("/list")
    @ResponseBody
    public JsonData queryAllMembers() {
        Optional<List<Members>> optional = membersService.selectList(null);
        if(optional.isPresent()){
            return JsonResult.success(optional.get(),"查询用户列表成功！");
        }
        return JsonResult.success(null,"查询用户列表成功！");
    }



    @ApiOperation("用户登录")
    @GetMapping("/")
    @ResponseBody
    public JsonData login(String identify, String password,String validateCode,String token,HttpSession session) {

        log.info("用户名："+identify+",密码："+password+"验证码："+validateCode);

        //检查验证码是否正确。
        //从 redis中获取验证码

        //如果redis中不存在key
//        if(!redisTemplate.hasKey(token)){
//            return JsonResult.fail("验证码不正确！") ;
//        }

        //如果redis中存在则取出来比较
//        String sessionValidateCode = (String) redisTemplate.opsForValue().get(token);
//        Object code=session.getAttribute("code");
//        System.out.println(code);
        System.out.println(validateCode);
        if(!validateCode.equals("1209")){ //说明验证码不正确MyUtils.code
            return JsonResult.fail("验证码不正确！");
        }

        Optional<Members> optional = membersService.login(identify, password);
        if (optional.isPresent()) {
            //说明登录成功
//            Members loginSuccessMember = optional.get();
            //生成以服务器token.保存到redis缓存里面
//            loginSuccessMember.setToken(MyUtils.createLoginSuccessToken(loginSuccessMember.getUsername()));
//            redisTemplate.opsForValue().set(loginSuccessMember.getToken(),loginSuccessMember);
            return JsonResult.success(optional.get(), "登录成功！");
        }

        return JsonResult.fail("登录失败！检查用户名或者密码是否正确！");
    }
    @ApiOperation("管理员登录")
    @GetMapping("/admin")
    @ResponseBody
    public JsonData adminlogin(String username, String password){
        System.out.println(username);
        Optional<Members> optional = membersService.adminlogin(username, password);
        if (optional.isPresent()) {
            //说明登录成功
//            Members loginSuccessMember = optional.get();
            //生成以服务器token.保存到redis缓存里面
//            loginSuccessMember.setToken(MyUtils.createLoginSuccessToken(loginSuccessMember.getUsername()));
//            redisTemplate.opsForValue().set(loginSuccessMember.getToken(),loginSuccessMember);
            return JsonResult.success(optional.get(), "登录成功！");
        }

        return JsonResult.fail("登录失败！检查用户名或者密码是否正确！");

    }

    //用户注册
    @ApiOperation("用户注册")
    @ResponseBody
    @PostMapping("/")
    public JsonData reg(@RequestBody Members members){

        log.info("注册用户资料："+members.toString());

        //先要检查验证码是否正确。

        String token = members.getToken();
        //如果redis中不存在key
//        if(!redisTemplate.hasKey(token)){
//            return JsonResult.fail("验证码验证失败！") ;
//        }
//        //如果redis中存在则取出来比较
//        String redisValidateCode = (String) redisTemplate.opsForValue().get(token);
        if(!"1209".equals(members.getValidateCode())){
            return JsonResult.fail("验证码验证失败！") ;
        }

        //注册时检查手机，电子邮箱号码是否重复。
        if(membersService.checkMobileOrEmailExist(members)){
            return JsonResult.fail("手机号码或者电子邮箱已经被注册！") ;
        }

        if(membersService.reg(members)){
            return JsonResult.success(null,"注册成功！");
        }
        return JsonResult.fail("注册失败！") ;
    }
    //发送验证码的接口
    @ApiOperation("发送验证码")
    @ResponseBody
    @GetMapping("validatecode")
    public JsonData  sendValiateCode(String identify,String token){
        log.info("用户注册的token:"+token);

        try {
            if (MyUtils.isPhoneLegal(identify)) {
                //log.info("给" + identify + "手机发送验证码...");
                String randomCode = MyUtils.sendMessageCodeByTenxunyun(identify);
//                redisTemplate.opsForValue().set(token,randomCode,10, TimeUnit.MINUTES);
            } else if (MyUtils.isEmailLegal(identify)) {
                //log.info("给"+identify+"电子邮箱发送验证码...");
                String randomCode = MyUtils.sendEmailValidateCode(identify);
                //保存到redis缓存中。
                log.info("生成的验证码是："+randomCode);
//                redisTemplate.opsForValue().set(token,randomCode,10, TimeUnit.MINUTES);
            } else {
                return JsonResult.fail("发送验证的账号格式不正确！");
            }
            return JsonResult.success(null, "验证码发送成功！");
        }catch(Exception ex){
            ex.printStackTrace();
            return JsonResult.success(null, "发送失败,服务器异常！");
        }
    }

    /*@ApiOperation("QQ第三方登录接口")
    @GetMapping("thirdauth")
    public String thridAuth(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        Members loginUser =null;
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        JsonData resp = qqLogin(request, response, session, code);

        QQUserInfo qqUserInfo = (QQUserInfo) resp.getData();
        //System.out.println(resp);
        if("thirdlogin".equals(state)) {
            Optional<Members> optional = membersService.queryUsersByOpenId(qqUserInfo.getOpenId());
            if(!optional.isPresent()){
                //说明是第一次第三方登录；
                loginUser = new Members();
                loginUser.setOpenId(qqUserInfo.getOpenId());
                loginUser.setGender(qqUserInfo.getGender());
                loginUser.setNickname(qqUserInfo.getNickname());
                loginUser.setUsername(qqUserInfo.getNickname());
                loginUser.setUserIconUrl(qqUserInfo.getUserIconUrl());
                loginUser.setPassword("888888");
                loginUser.setRegType("2");//注册类型
                membersService.reg(loginUser);
            }else{
                loginUser = optional.get();
            }

            loginUser.setToken(MyUtils.createLoginSuccessToken(loginUser.getUsername()));
            redisTemplate.opsForValue().set(loginUser.getToken(), loginUser, 100, TimeUnit.DAYS);
            return "redirect:https://www.simoniu.com/?authtoken="+loginUser.getToken();
        }else{
            response.setContentType("text/html;charset=utf-8");
            PrintWriter outer = response.getWriter();
            outer.write(resp.toString());

            return null;
        }
    }*/


    /*@GetMapping("auth")
    //@ResponseBody
    @ApiOperation("身份验证接口")
    @ResponseBody
    public JsonData getAuthInfo(String token){
        try{
            if(!redisTemplate.hasKey(token)){
                return JsonResult.unauth("身份验证失败，未登录！");
            }
            Members loginUser = (Members) redisTemplate.opsForValue().get(token);
            return JsonResult.success(loginUser,"获取身份验证成功！");
        }catch(Exception ex){
            ex.printStackTrace();
            return JsonResult.fail("获取身份验证失败！");
        }
    }*/

    @GetMapping("logout")
    //@ResponseBody
    @ApiOperation("用户注销接口")
    @ResponseBody
    public JsonData logout(String token){
        try{
            /*Optional<Members> optional = authLoginMember(token);
            if(!optional.isPresent()){
                return JsonResult.fail("注销失败！");
            }
            redisTemplate.delete(token);*/
            return JsonResult.success("注销成功！");
        }catch(Exception ex){
            ex.printStackTrace();
            return JsonResult.fail("注销失败！");
        }
    }



    /*private JsonData qqLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session, String code) {

        //填写Appid,appkey和回调地址
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&" +
                "client_id=" + APPID + "&client_secret=" + APPKEY + "&redirect_uri=" + RECALL + "&code=" + code;

        //String responseStr = httpRequest(url);
        String responseStr = HttpRequest.get(url).header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();

        //获取access_Token
        String tokens[] = responseStr.split("&");
        String token = tokens[0];
        String userUrl = "https://graph.qq.com/oauth2.0/me?" + token;

        //String sr = httpRequest(userUrl);
        String sr = HttpRequest.get(userUrl).header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();

        JSONObject result = JSONUtil.parseObj(sr.substring(10, sr.length() - 3));

        //获取Open_ID
        String openId = (String) result.get("openid");
        //根据OpenId去获取用户信息
        String userinfoUrl = "https://graph.qq.com/user/get_user_info?" + token +
                "&oauth_consumer_key=" + APPID + "&openid=" + openId;
        //String userInfoText = httpRequest(userinfoUrl);

        String userInfoText = HttpRequest.get(userinfoUrl).header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();

        JSONObject userInfoResult = JSONUtil.parseObj(userInfoText);
        String nickName = (String) userInfoResult.get("nickname");
        String userIconUrl = (String) userInfoResult.get("figureurl");
        String gender = (String) userInfoResult.get("gender");

        QQUserInfo qqUserInfo = new QQUserInfo();
        qqUserInfo.setNickname(nickName);
        qqUserInfo.setGender(gender);
        qqUserInfo.setUserIconUrl(userIconUrl);
        qqUserInfo.setOpenId(openId);
        return JsonResult.success(qqUserInfo,"QQ第三方登录接口调用成功！");
    }*/

}

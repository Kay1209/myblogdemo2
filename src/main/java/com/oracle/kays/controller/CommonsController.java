package com.oracle.kays.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Console;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.gson.Gson;
import com.oracle.kays.json.JsonData;
import com.oracle.kays.json.JsonResult;
import com.oracle.kays.util.MyUtils;
import com.oracle.kays.view.UploadFileView;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Api("公共模块接口")
@RestController
@RequestMapping("commons")

public class CommonsController extends BaseController {

    @Autowired
    private DefaultKaptcha captchaProducer;

    //七牛云accessKey
//    @Value("${qiniuyun.accessKey}")
//    private String qiniuAccessKey;
//    @Value("${qiniuyun.secretKey}")
//    private String qiniuSecretKey;
//    @Value("${qiniuyun.bucket}")
//    private String qiniuBucket;
//    @Value("${qiniuyun.domain}")
//    private String qiniuDomain;

    //客户端获取sessionId
    @RequestMapping("getSessionId")
    public JsonData getSessionId(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String jSESSIONID = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    //System.out.println("请求的cookie:"+cookie.getValue());
                    jSESSIONID = cookie.getValue();
                    System.out.println("jSessionId:" + jSESSIONID);
                }
            }
        }
        System.out.println("getSessionId:" + jSESSIONID);
        return JsonResult.success(jSESSIONID);
    }

    //使用hutool工具类生成验证码，用于发博文时输入的验证码
    @GetMapping("hutoolRandomCodeImage")
    public JsonData createRandomCodeImage(String token, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        //response.setContentType("image/jpeg");
        System.out.println("token值是：" + token);
        try {
            //定义图形验证码的长和宽
            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 40, 4, 2);
            BufferedImage image = new BufferedImage(100, 40, BufferedImage.TYPE_INT_RGB);
            //图形验证码写出，可以写出到文件，也可以写出到流
            OutputStream out = response.getOutputStream();
            image = (BufferedImage) lineCaptcha.createImage(lineCaptcha.getCode());
            ImageIO.write(image, "JPEG", out);
            //输出code
            Console.log("hutool生成的验证码是：" + lineCaptcha.getCode());
            MyUtils.code=lineCaptcha.getCode();
            //验证图形验证码的有效性，返回boolean值
            //把验证码保存到redis中,默认半个小时内有效。
//            redisTemplate.opsForValue().set(token, lineCaptcha.getCode(), 10, TimeUnit.MINUTES);
//            System.out.println("验证码保存到redis成功！");

//            保存到sesions
//            request.getSession().setAttribute("IS_LOGIN",true);
//            request.getSession().setAttribute("code",lineCaptcha.getCode());

            System.out.println(MyUtils.code);
            out.flush();
            out.close();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //Google的kaptacha组件生成验证码的动作。
    @GetMapping("kaptchaRandomCodeImage")
    public void defaultKaptcha(String token, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        System.out.println("输出kaptcha验证码");
        byte[] captchaChallengeAsJpeg = null;
        OutputStream jpegOutputStream = httpServletResponse.getOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            System.out.println("输出kaptcha验证码字符串是：" + createText);

            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "JPEG", jpegOutputStream);

            //redisTemplate.opsForValue().set(token, createText, 10, TimeUnit.MINUTES);
            System.out.println("验证码保存到redis成功！");

            jpegOutputStream.flush();
            jpegOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    @GetMapping("Te")
    public void Te(HttpServletRequest request){
        //System.out.println("sucess");
        System.out.println(request);
        Object code=request.getSession().getAttribute("code");
        System.out.println(code);
    }

    //如果是使用ElementUI的upload组件要求必须是post请求实现上传
    //实现七牛云文件上传功能
//    @PostMapping("upload")
//    public JsonData loadAvatar(MultipartFile file, HttpServletRequest request) {
//        System.out.println("执行上传...");
//        try {
//            //构造一个带指定 Region 对象的配置类
//            Configuration cfg = new Configuration(Region.region1());
//            //...其他参数参考类注释
//            UploadManager uploadManager = new UploadManager(cfg);
//            InputStream inputStream = file.getInputStream();
//            System.out.println("上传字节个数：" + inputStream.available());
//            Auth auth = Auth.create(qiniuAccessKey, qiniuSecretKey);
//            String upToken = auth.uploadToken(qiniuBucket);
//            //默认不指定key的情况下，以文件内容的hash值作为文件名
//            String key = file.getOriginalFilename();
//            try {
//                Response response = uploadManager.put(inputStream, key, upToken, null, null);
//                //解析上传成功的结果
//                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
//                UploadFileView successFile = new UploadFileView();
//                successFile.setName(putRet.key);
//                successFile.setUrl(qiniuDomain + putRet.key);
//                return JsonResult.success(successFile, "文件上传成功！");
//            } catch (QiniuException ex) {
//                Response r = ex.response;
//                System.err.println(r.toString());
//
//                try {
//                    System.err.println(r.bodyString());
//                } catch (QiniuException ex2) {
//                    //ignore
//                }
//            }
//            return JsonResult.success(null, "文件上传成功！");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return JsonResult.fail("文件上传失败！");
//        }
//    }

    //文件下载

    @GetMapping("download")
    public JsonData downloadFileList(HttpServletRequest request) {
        String path = null;
        java.util.List<String> filesNameList = new ArrayList<String>();
        try {
            filesNameList = MyUtils.getDownloadFileList(path);
            return JsonResult.success(filesNameList, "获取下载文件列表成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return JsonResult.fail("获取下载文件列表失败！");
        }
    }


    //生成二维码
    /*
    @GetMapping("qrcode")

    public void generateQRCodeImg (String token, String url, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String path;
        try{
            if("windows".equals(MyUtils.getOperateSysName())){
                path = this.uploadWinPath;
            }else{
                path = this.uploadLinuxPath;
            }
            OutputStream jpegOutputStream = httpServletResponse.getOutputStream();
            //把生成的二维码图片保存到磁盘
            QrCodeUtil.generate(url, 300, 300, FileUtil.file(path,"qrcode.jpg"));
            //IO流读取这张图片
            BufferedImage image = ImgUtil.read(path+"qrcode.jpg");
            //IO流写到浏览器上。
            ImageIO.write(image, "JPEG", jpegOutputStream);
            jpegOutputStream.flush();
            jpegOutputStream.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }*/

    //生成短地址
    @GetMapping("shorturl")
    public JsonData generateShortUrl(String token, String url, HttpServletRequest request, HttpServletResponse response) {
        String shortUrl = null;
        try {
            //调用了新浪网的生成短地址的接口。
            //利用了糊涂的HttpRequest工具类，发送了get请求。
            String result = HttpRequest.get("https://www.98api.cn/api/sinaDwz.php?url=" + url)
                    .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                    .timeout(20000)//超时，毫秒
                    .execute().body();
            //糊涂JSONUtils自动，字符串转json对象。
            JSONObject jsonObject = JSONUtil.parseObj(result);

            if (jsonObject != null && jsonObject.containsKey("short_url")) {
                shortUrl = jsonObject.getStr("short_url");
            }
            return JsonResult.success(shortUrl, "生成地址成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return JsonResult.fail("生成地址失败！");
        }
    }
}

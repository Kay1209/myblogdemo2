package com.oracle.kays.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.mail.MailUtil;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.oracle.kays.config.MyConfig;
import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class MyUtils {


    //过期时间
    private static final long EXPIRE_TIME = 15 * 60 * 1000;
    //私钥
    private static final String TOKEN_SECRET = "www.simoniu.com";

    //生成验证码
    private static char[] ops = new char[]{'+', '-', '*'};
    public static String code=null;

    //调用腾讯云发送短信的接口实现用户注册时给用户发送验证码
    //腾讯云短信调用接口
    //3  短信的模板ID
    static final int templateID = 954719;
    //4  签名的名字
    static final String smsSign = "Kays编程";


    /**
     * + - *
     */

    //java.util.Date转换为 String
    public static String utilsDateToString(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }


    //生成一个六位的验证码
    public static String createRandomCode() {

        int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);  //随机数生成验证码
        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

        return mobile_code + "";
    }

    //生成一个验证码图片(数学公式)

    //使用画板，验证码生成图片，放入redis后返回
    public static BufferedImage createVerifyCode(HttpSession session) {

        //生成图片的尺寸，宽度和高度
        int width = 80;
        int height = 32;

        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);

        //把验证码存，计算结果保存到session;
        int rnd = calc(verifyCode);

        session.setAttribute("randomCodeResult", rnd);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode + "=", 8, 24);
        g.dispose();

        //输出图片
        return image;
    }

    //计算结果
    public static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer) engine.eval(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(9);//0-8,不想要0；
        int num2 = rdm.nextInt(9);
        num1++; //1-9
        num2++;
        //int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        //char op2 = ops[rdm.nextInt(3)];
        String exp = "" + num1 + op1 + num2;
        return exp;
    }

    public static String streamToStr(InputStream inputStream, String chartSet) {

        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, chartSet));
            String con;
            while ((con = br.readLine()) != null) {
                builder.append(con);
            }

            br.close();
            return builder.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    //给某个邮箱发送验证码
    public static String sendEmailValidateCode(String toEmail) {
        String randomCode = RandomUtil.randomNumbers(4);
        MailUtil.send(toEmail, "欢迎注册Kays编程", randomCode, false);
        return randomCode;
    }


    //使用阿里云发送短信验证码
    /*
     * randomCode,表示要发送的验证码
     * mobile,表示要发送的手机号码
     * */

    public static String sendMessageCodeByTenxunyun(String mobile) {
        String accessKeyId = MyConfig.getAccessKeyId();
        String accessKeySecret = MyConfig.getAccessKeySecret();
        System.out.println("accessKeyId:"+accessKeyId);
        System.out.println("accessKeySecret:"+accessKeySecret);
        SmsSingleSender ssender = new SmsSingleSender(Integer.parseInt(accessKeyId),accessKeySecret);
        String randomCode = RandomUtil.randomNumbers(4);
        String[] params = {randomCode};
        try {
            SmsSingleSenderResult result = ssender.sendWithParam("86",
                    mobile, templateID, params, smsSign, "", "");
            //输出一下返回值
            System.out.println(result);
            return randomCode;
        } catch (Exception ex) {
            return null;
        }

    }

    //实现密码加密功能，使用Bcrypt
    public static String encodePassword(String originPass) {
        return DigestUtil.bcrypt(originPass);
    }

    //检查解密密码相当于比较密码是否相等用于登录
    public static boolean checkPassword(String originPass, String hashPass) {
        return DigestUtil.bcryptCheck(originPass, hashPass);
    }

    public static boolean isEmailLegal(String email) {
        boolean tag = true;
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            tag = false;

        }

        return tag;
    }


    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);

        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);

        return m.matches();
    }

    //实现文件上传
    public static String uploadFile(MultipartFile file, String path) throws Exception {

        String fileName = file.getOriginalFilename(); //获得上传文件的文件名
        //使用UUID生成一个不重复的文件名字。
        String uploadFileName = createUUIDFileName(fileName);

        //先判断文件的父目录是否存在，如果不存在先创建目录
        File uploadFile = new File(path, uploadFileName);
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }
        //在判断文件是否存在，如果存在先删除，再创建。
        if (uploadFile.exists()) {
            uploadFile.delete();
        }
        uploadFile.createNewFile();

        file.transferTo(uploadFile);
        return uploadFile.getName();

    }

    //获取文件下载目录文件集合
    public static java.util.List<String> getDownloadFileList(String path) {
        File downloadDir = new File(path);
        java.util.List<String> filesNameList = new ArrayList<String>();
        File[] files = downloadDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                filesNameList.add(files[i].getName());
            }
        }
        return filesNameList;
    }

    //获得上传成功后返回的UUID的文件名，避免用户之间重名的文件覆盖问题
    public static String createUUIDFileName(String fileName) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid + fileName;
    }


    //获取操作系统的名称
    public static String getOperateSysName() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return "windows";
        } else {
            return "linux";
        }
    }

    //gbk字符串转utf-8字符串
    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }


    //运行windows命令
    /*public static String runWinCmd(String cmdpath, String cmd) {
        Console.log("cmdpath：" + cmdpath);
        Console.log("cmd：" + cmd);

        String result = "";
        File dir = new File(cmdpath);
        try {
            Process ps = Runtime.getRuntime().exec(cmd, null, dir);

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream(), Charset.forName("GBK")));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                result += line + "\n";
            }

            br.close();
            System.out.println("close ... ");
            ps.waitFor();
            System.out.println("wait over ...");

            return result;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("child thread donn");
        return null;
    }*/

    //运行linux命令
    public static String runLinuxCmd(String cmdpath, String[] cmd) {
        System.out.println("cmdPath-->" + cmdpath);

        System.out.println("cmd-->" + cmd);

        String result = "";
        File dir = new File(cmdpath);
        try {
            Process ps = Runtime.getRuntime().exec(cmd, null, dir);

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                result += line + "\n";
            }

            br.close();
            System.out.println("close ... ");
            ps.waitFor();
            System.out.println("wait over ...");

            return result;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("child thread donn");
        return null;
    }


    public static String generateRandomUsername(){
        return IdUtil.simpleUUID();
    }


    /*public static String createLoginSuccessToken(String username){
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("userId", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public static void main(String[] args) throws Exception {


    }

}

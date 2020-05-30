package com.example.eatscent.service.Impl;

import com.example.eatscent.dao.UserMapper;
import com.example.eatscent.service.LoginService;
import com.example.eatscent.until.SendEmail;
import com.example.eatscent.until.info.Email_info;
import com.example.eatscent.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
/**
 * @author 11397
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private Email_info email_info;
    @Autowired
    private SendEmail sendEmail;


    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public List<User> login(User user) {
        List<User> loginList = userMapper.selectByLogin(user);
        return loginList;
    }

    @Override
    public User selectById(int id) {
        return userMapper.selectByLoginID(id);
    }

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void insertByPrimaryKey(User user) {
        userMapper.insertByPrimaryKey(user);
    }

    /**
     * 测试AOP
     * @param id
     * @return
     */
    @Override
    public String aopTest(int id) {
        Calendar galen = Calendar.getInstance();
        String send_Date = galen.get(Calendar.YEAR) + "年" + (galen.get(Calendar.MONTH) + 1) + "月" + galen.get(Calendar.DATE) + "日";
        email_info.setEmailMessage("<p style=\"text-align:left;\">尊敬的客户：</p>" +
                "<p style=\"text-align:left;\">&nbsp;&nbsp;您好！</p>" +
                "<p style=\"text-align:left;\">&nbsp;&nbsp;感谢您选择中国银行养老金服务！</p>" +
                "<p style=\"text-align:left;\">&nbsp;&nbsp;根据相关流程，现将相关业务表单发送给您，见附件。</p>" +
                "<p style=\"text-align:left;\">1、中国银行\n2、中国银行股份有限公司\n3、中国银行股份有限公司\n4、中国银行股份有限公司</p>" +
                "<p style=\"text-align:left;\">&nbsp;&nbsp;如有疑问，请电话咨询：XXXXXXXXXXXXXXXXX</p>" +
                "<p style=\"text-align:left;\">&nbsp;&nbsp;或邮件咨询：XXXXXXXXXXXXXXX</p>" +
                "<p style=\"text-align:right;\">中国银行股份有限公司</p>" +
                "<p style=\"text-align:right;\">中行北京总行养老金公共邮箱</p>" +
                "<p style=\"text-align:right;\">"+send_Date+"</p>");
        email_info.setTtl("邮件测试");
        email_info.setRecvEmail("1139778430@qq.com");
        email_info.setFileName("2019java面试题.pdf");
        email_info.setFilePath("D:\\BaiduNetdiskDownload");
        sendEmail.Email_Send_HTML(email_info);
        return "index";
    }
}

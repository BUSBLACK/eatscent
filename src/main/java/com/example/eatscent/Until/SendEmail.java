package com.example.eatscent.Until;

import com.example.eatscent.Until.info.Email_info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
@Configuration
@EnableAspectJAutoProxy
public class SendEmail {
    Logger logger = LoggerFactory.getLogger(SendEmail.class);
    private final String WY = "smtp.163.com";//网易邮箱服务器
    private final String WY_PORT = "994";//网易服务器端口
    private final String WY_USER = "*****************";//发送方邮箱
    private final String WY_PASSWORD = "*********************";//发送发授权码
    private final String QQ = "smtp.qq.com";//QQ邮箱服务器
    private final String QQ_PORT = "465"; //QQ服务器端口
    private final String QQ_USER = "*******************";//发送方邮箱
    private final String QQ_PASSWORD = "*******************";//发送发授权码
    /**
     * 邮件发送，文本
     * @param email
     * @return
     */
    @Bean
    public boolean Email_Send_Text(Email_info email){
        Properties properties = new Properties();
        //指定连接邮件服务器的名称
        properties.setProperty("mail.smtp.host",QQ);
        //客户端是否要向邮件服务器提交验证
        properties.setProperty("mail.smtp.auth","false");
        //mail.transport.protocol：指定邮件发送协议：smtp：发邮件；pop3：收邮件
        properties.setProperty("mail.transport.protocol", "smtp");
        //设置通讯端口
        properties.setProperty("mail.smtp.socketFactory.port", QQ_PORT);
        //设置通讯方式，采用SSL进行通讯
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //是否需要回执
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");

        //获取Session对象
        Session session = Session.getInstance(properties);
        //向控制台输出日志
        session.setDebug(true);
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            //设置邮件发送方
            mimeMessage.setFrom(new InternetAddress(QQ_USER));
            //设置邮件主题
            mimeMessage.setSubject(email.getTtl());
            //设置邮件显示收件人及收件人类别
            // Message.RecipientType.TO 收件
            // Message.RecipientType.CC 抄送
            // Message.RecipientType.BCC 密送
            mimeMessage.setRecipients(Message.RecipientType.TO,email.getRecvEmail());
            //设置邮件发送内容 setText 纯文本 setConten 较复杂的内容，例如html
            mimeMessage.setText(email.getEmailMessage(),"text/html;charset=utf-8");
            //创建连接对象
            Transport transport = session.getTransport();
            //连接发送方邮箱
            transport.connect(QQ_USER,QQ_PASSWORD);
            //发送邮件
            transport.sendMessage(mimeMessage,new Address[] {new InternetAddress(email.getRecvEmail())});
            transport.close();
            logger.info("发送邮件成功！");
        } catch (MessagingException e) {
            logger.error("发送邮件失败"+e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 邮件发送，HTML格式
     * @param email
     * @return
     */
    @Bean
    public boolean Email_Send_HTML(Email_info email){
        Properties properties = new Properties();
        //指定连接邮件服务器的名称
        properties.setProperty("mail.smtp.host",WY);
        //客户端是否要向邮件服务器提交验证
        properties.setProperty("mail.smtp.auth","false");
        //mail.transport.protocol：指定邮件发送协议：smtp：发邮件；pop3：收邮件
        properties.setProperty("mail.transport.protocol", "smtp");
        //设置通讯端口
        properties.setProperty("mail.smtp.socketFactory.port", WY_PORT);
        //设置通讯方式，采用SSL进行通讯
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //是否需要回执
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");

        //获取Session对象
        Session session = Session.getInstance(properties);
        //向控制台输出日志
        session.setDebug(true);
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            //设置邮件发送方
            mimeMessage.setFrom(new InternetAddress(WY_USER));
            //设置邮件主题
            mimeMessage.setSubject(email.getTtl());
            //设置邮件显示收件人及收件人类别
            // Message.RecipientType.TO 收件
            // Message.RecipientType.CC 抄送
            // Message.RecipientType.BCC 密送
            mimeMessage.setRecipients(Message.RecipientType.TO,email.getRecvEmail());
            //设置邮件发送内容
            //multipart格式的数据会将一个表单拆分为多个部分（part），每个部分对应一个输入域。
            Multipart multipart = new MimeMultipart();
            BodyPart html = new MimeBodyPart();
            //设置HTML内容
            html.setContent(email.getEmailMessage(),"text/html;charset=utf-8");
            multipart.addBodyPart(html);
            //获取附件路径
            String attachFileNames = email.getFilePath()+File.separator+email.getFileName();
            if (attachFileNames != null && !attachFileNames.equals("")) {

                // 根据附件文件创建文件数据源
                File file = new File(attachFileNames);
                // 存放邮件附件的MimeBodyPart
                //初始化MimeBodyPart对象 否则会出现附件重复出出现的情况,原有html对象中的数据会被覆盖
                html = new MimeBodyPart();
                //FileDataSource 类实现一个封装文件的简单 DataSource 对象。它通过 FileTypeMap 对象提供数据分类服务
                FileDataSource fds = new FileDataSource(file);
                //将附件的文件源存到MimeBodyPart对象中
                html.setDataHandler(new DataHandler(fds));
                // 为附件设置文件名
                try {
                    html.setFileName(MimeUtility.encodeWord(
                            email.getFileName(), "GBK", null));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return false;
                }
                //
                multipart.addBodyPart(html);
            }
            mimeMessage.setContent(multipart);
            //创建连接对象
            Transport transport = session.getTransport();
            //连接发送方邮箱
            transport.connect(WY_USER,WY_PASSWORD);
            //发送邮件
            transport.sendMessage(mimeMessage,new Address[] {new InternetAddress(email.getRecvEmail())});
            transport.close();
            logger.info("发送邮件成功！");
        } catch (MessagingException e) {
            logger.error("发送邮件失败"+e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static void main (String[] args){
        Email_info email_info = new Email_info();
        email_info.setEmailMessage("llll<br/>222222");
        email_info.setTtl("邮件测试");
        email_info.setRecvEmail("***************@qq.com");
        email_info.setFileName("2019java面试题.pdf");
        email_info.setFilePath("D:\\BaiduNetdiskDownload");
        boolean fa = new SendEmail().Email_Send_HTML(email_info);
        System.out.println(fa);
    }
}

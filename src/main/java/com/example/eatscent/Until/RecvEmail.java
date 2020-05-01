package com.example.eatscent.Until;

import com.example.eatscent.Until.info.Email_info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.*;
import java.util.HashMap;
import java.util.Properties;

import static java.lang.Thread.sleep;

@Configuration
@EnableTransactionManagement
public class RecvEmail {
    Logger logger = LoggerFactory.getLogger(RecvEmail.class);
    private final String WY = "pop3.163.com";//网易邮箱服务器
    private final String WY_PORT = "110";//网易服务器端口
    private final String WY_USER = "XXXXXXXXXX@163.com";//邮箱
    private final String WY_XY = "pop3";//邮箱
    private final String WY_PASSWORD = "XXXXXXXXXXXXXXX";//授权码
    private final String QQ = "imap.qq.com";//QQ邮箱服务器
    private final String QQ_PORT = "993"; //QQ服务器端口
    private final String QQ_USER = "XXXXXXXXXXXXX@qq.com";//邮箱
    private final String QQ_PASSWORD = "XXXXXXXXXXXXXXX";//授权码
    private final String QQ_XY = "imap";//QQ邮箱服务器

    /**
     * 接收邮件
     *
     * @return
     */
    @Bean
    public HashMap recvEmail_All() {
        HashMap map = new HashMap();
        Properties props = new Properties();
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//ssl加密,jdk1.8无法使用
        props.setProperty("mail.imap.socketFactory.fallback", "false");//是否回执
        props.setProperty("mail.transport.protocol", QQ_XY); // 使用的协议
        props.setProperty("mail.imap.port", QQ_PORT);
        props.setProperty("mail.imap.socketFactory.port", QQ_PORT);
        Message[] messages = null;//存放邮件信息
        Session session = Session.getDefaultInstance(props);//创建会话
        try {
            Store store = session.getStore(QQ_XY);//存储对象
            store.connect(QQ, QQ_USER, QQ_PASSWORD);//登录认证
            Folder folder = store.getFolder("inbox");//获取用户的邮件账户
            folder.open(Folder.READ_WRITE);//设置对邮件账户的访问权限

            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);//false代表未读，true代表已读
            messages = folder.search(ft);
            logger.info("共有" + messages.length + "新邮件");
            map.put("num",messages.length);
            for (int i = 0; i < messages.length; i++) {
                sleep(1000);
                String subject = messages[i].getSubject();//获取邮件主题
                String from = (messages[i].getFrom()[0]).toString();//获取发送人
                String content = messages[i].getContent().toString();//获取内容需要进一步解析
                String size = messages[i].getSize() * 1024 + "KB";
                logger.info("第 " + (i + 1) + " 封邮件的发件人地址---->>>" + from);
                map.put("from"+i,from);
                logger.info("第 " + (i + 1) + " 封邮件的主题--->>>" + subject);
                map.put("subject"+i,subject);
                logger.info("第 " + (i + 1) + " 封邮件是否已读--->>>" + messages[i].getFlags().contains(Flags.Flag.SEEN));
                map.put("SEEN"+i,messages[i].getFlags().contains(Flags.Flag.SEEN));
                logger.info("第 " + (i + 1) + " 封邮件是否需要回执--->>>" + String.valueOf(messages[i].getHeader("Disposition-Notification-To") != null ? true : false));
                map.put("Disposition"+i,String.valueOf(messages[i].getHeader("Disposition-Notification-To") != null ? true : false));
                logger.info("第 " + (i + 1) + " 封邮件的大小--->>>" + size);
                map.put("size"+i,size);
                logger.info("第 " + (i + 1) + " 封邮件发送时间--->>>" + messages[i].getSentDate());
                map.put("SentDate"+i,messages[i].getSentDate());
                logger.info("第 " + (i + 1) + " 封邮件内容--->>>" + getFile(messages[i]));
                map.put("file"+i,getFile(messages[i]));
                logger.info("-----------------------------------------");
                messages[i].setFlag(Flags.Flag.SEEN, true);//imap读取后邮件状态变为已读
                //messages[i].setFlag(Flags.Flag.DELETED,true);//删除邮件
            }
            folder.close(false);
            store.close();
        } catch (MessagingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 获取邮件内容
     * @param part
     * @return
     */
    public String getFile(Part part) throws Exception {
        String message = "";
        String contentType = part.getContentType();
        int index = contentType.indexOf("name");
        boolean conName = false;
        if (index != -1) {
            conName = true;
        }
        //判断part类型
        if (part.isMimeType("text/*") && !conName) {//纯文本或HTNL类型
            message = (String) part.getContent();
            return message;
        } else if (part.isMimeType("multipart/*")) {//复杂类型，例如图片、附件等
            Multipart multipart = (Multipart) part.getContent();//获取邮件中复杂类型的数据
            int counts = multipart.getCount();//数据数量
            for (int i = 0; i < counts; i++) {
                //递归获取数据
                getFile(multipart.getBodyPart(i));
                //附件可能是截图或上传的(图片或其他数据)
                if (multipart.getBodyPart(i).getDisposition() != null) {
                    //附件为截图
                    if (multipart.getBodyPart(i).isMimeType("image/*")) {//图片附件
                        InputStream is = multipart.getBodyPart(i)
                                .getInputStream();
                        String name = multipart.getBodyPart(i).getFileName();
                        String fileName;
                        //截图图片
                        if (name.startsWith("=?")) {
                            fileName = name.substring(name.lastIndexOf(".") - 1, name.lastIndexOf("?="));
                        } else {
                            //上传图片
                            fileName = name;
                        }
                        //下载附件
                        FileOutputStream fos = new FileOutputStream("D:\\WorkApp\\File\\" + fileName);
                        int len = 0;
                        byte[] bys = new byte[1024];
                        while ((len = is.read(bys)) != -1) {
                            fos.write(bys, 0, len);
                        }
                        fos.close();
                        message = "D:\\WorkApp\\File\\111.pdf";
                        return message;
                    } else {
                        //其他附件
                        InputStream is = multipart.getBodyPart(i)
                                .getInputStream();
                        String name = multipart.getBodyPart(i).getFileName();
                        FileOutputStream fos = new FileOutputStream("D:\\WorkApp\\File\\111.pdf");
                        int len = 0;
                        byte[] bys = new byte[1024];
                        while ((len = is.read(bys)) != -1) {
                            fos.write(bys, 0, len);
                        }
                        fos.close();
                        message = "D:\\WorkApp\\File\\111.pdf";
                        return message;
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {//多重附件，递归解析
            getFile((Part) part.getContent());
        }
        return message;
    }

    public static void main(String[] args){
        HashMap messages = new RecvEmail().recvEmail_All();
        System.out.println(1111);
    }

}

package com.example.eatscent.aop.inface;

import com.example.eatscent.until.info.Email_info;

/**
 * @author 11397
 */
public interface emailInfoIntroduction {
    /**
     * 对邮件内容引入处理方法
     * @param email_info
     */
    Email_info sendIntroduction(Email_info email_info);
}

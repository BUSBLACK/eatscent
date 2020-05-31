package com.example.eatscent.aop;

import com.example.eatscent.aop.inface.emailInfoIntroduction;
import com.example.eatscent.until.info.Email_info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 11397
 */
public class emailInfoIntroductionimpl implements emailInfoIntroduction {
    Logger logger = LoggerFactory.getLogger(emailInfoIntroductionimpl.class);
    private static String ss = "\n";
    @Override
    public Email_info sendIntroduction(Email_info email_info) {
        logger.info("This is AOP Introduction");
        if(email_info.getEmailMessage() != null && email_info.getEmailMessage().contains(ss)) {
            String str = email_info.getEmailMessage().replaceAll("\n", "<br/>");
            email_info.setEmailMessage(str);
        }
        return email_info;
    }
}

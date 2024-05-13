package com.example.training.utils;

import com.example.training.common.exceptions.BizException;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MailUtils {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MailUtils.class);

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    private void checkMail(String to, String subject, String text) {
        if (StringUtils.isEmpty(to)) {
            throw new BizException("邮件收信人不能为空");
        }
        if (StringUtils.isEmpty(subject)) {
            throw new BizException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(text)) {
            throw new BizException("邮件内容不能为空");
        }
    }

    public void sendTextMailMessage(String to, String subject, String text){
        this.checkMail(to, subject, text);
        try {
            //true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(),true);
            //邮件发信人
            mimeMessageHelper.setFrom(sendMailer);
            //邮件收信人  1或多个
            mimeMessageHelper.setTo(to.split(","));
            //邮件主题
            mimeMessageHelper.setSubject(subject);
            //邮件内容
            mimeMessageHelper.setText(text);
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());

            //发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            System.out.println("发送邮件成功："+sendMailer+"->"+to);

        } catch (MessagingException e) {
            e.printStackTrace();
            logger.info("发送邮件失败：{}", e.getMessage());
        }
    }

}

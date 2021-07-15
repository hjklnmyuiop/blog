package com.blog;

import com.blog.utils.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@SpringBootTest
public class MailServiceTest {
    @Autowired
    private MailUtil mailService;
    private TemplateEngine templateEngine;
//    @Test
//    public void sendSimpleMail() {
//        mailService.sendSimpleMail("Dev_guo@patazon.net","测试spring boot imail-主题","测试spring boot imail - 内容");
//    }
//
//    @Test
//    public void sendHtmlMail() throws  MessagingException {
//
//        String content = "<html>\n" +
//                "<body>\n" +
//                "<h3>hello world</h3>\n" +
//                "<h1>html</h1>\n" +
//                "<body>\n" +
//                "</html>\n";
//
//        mailService.sendHtmlMail("Dev_guo@patazon.net","这是一封HTML邮件",content);
//    }

//    @Test
//    public void sendAttachmentsMail() throws MessagingException {
//        String filePath = "新建 DOC 文档.doc";
//        String content = "<html>\n" +
//                "<body>\n" +
//                "<h3>hello world</h3>\n" +
//                "<h1>html</h1>\n" +
//                "<h1>附件传输</h1>\n" +
//                "<body>\n" +
//                "</html>\n";
//        mailService.sendAttachmentsMail("Dev_guo@patazon.net","这是一封HTML邮件",content, filePath);
//    }

//    @Test
//    public void sendInlinkResourceMail() throws MessagingException {
//        //TODO 改为本地图片目录
//        String imgPath = "4.png";
//        String rscId = "admxj001";
//        String content = "<html>" +
//                "<body>" +
//                "<h3>hello world</h3>" +
//                "<h1>html</h1>" +
//                "<h1>图片邮件</h1>" +
//                "<img src='cid:"+rscId+"'></img>" +
//                "<body>" +
//                "</html>";
//
//        mailService.sendInlinkResourceMail("Dev_guo@patazon.net","这是一封图片邮件",content, imgPath, rscId);
//    }

//    @Test
//    public void testTemplateMailTest() throws MessagingException {
//        Context context = new Context();
//        context.setVariable("id","ispringboot");
//        String emailContent = templateEngine.process("emailTeplate", context);
//        mailService.sendHtmlMail("Dev_guo@patazon.net","这是一封HTML模板邮件",emailContent);
//    }
}

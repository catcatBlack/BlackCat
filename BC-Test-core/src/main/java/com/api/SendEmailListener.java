package com.api;

import com.Model.ConfigModel.Mail;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.testng.IExecutionListener;

public class SendEmailListener implements IExecutionListener {
    private String filename = RepoterListener.getFileName();
    public void onExecutionStart(){
        //System.out.println("");
    }
    public void onExecutionFinish(){
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName("smtp.qq.com");
        htmlEmail.setAuthentication("2811083971@qq.com","fimidcuesuwhdcig");
        try {
            htmlEmail.addTo("hitszfjw@163.com");
            htmlEmail.setFrom("2811083971@qq.com");

            htmlEmail.setSubject("ApiAutoTest");
            htmlEmail.setCharset("UTF-8");

            htmlEmail.setHtmlMsg("<a href=\"\"> 测试报告");

            EmailAttachment emailAttachment = new EmailAttachment();
            //emailAttachment.setPath("D:\\CODE1\\t2\\2021年-08月-08日-14时01分10秒report.html");
            emailAttachment.setPath("D:\\测试\\BlackCat\\BC-Test-core\\test-output\\" + filename);
            emailAttachment.setName("usertest report.html");
            emailAttachment.setDescription(EmailAttachment.ATTACHMENT);
            htmlEmail.attach(emailAttachment);
            htmlEmail.send();
        } catch (EmailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

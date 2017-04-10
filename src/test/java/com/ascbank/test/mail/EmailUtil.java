/**
 * 
 */
package com.ascbank.test.mail;

import javax.mail.MessagingException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author jie
 *
 */
public class EmailUtil {
	private static EmailUtil emailUtil = null;   
    
    public ApplicationContext ctx = null;    
       
    public  EmailUtil() {   
        //获取上下文            
        ctx = new ClassPathXmlApplicationContext("classpath:/email/SpringContext-mail.xml");      
    }   
    /**  
     *   
     * @function:获得单例  
     */  
    public static EmailUtil getInstance()   
    {   
        if(emailUtil==null)   
        {   
            synchronized (EmailUtil.class)    
            {   
                if(emailUtil==null) {   
                    emailUtil = new EmailUtil();   
                }   
            }   
        }   
        return emailUtil;   
    }   
  
    public void sentEmails(String emails,String subject,String text)   
    {              
        //获取JavaMailSender bean      
        JavaMailSender sender = (JavaMailSender) ctx.getBean("mailSender");     
       
        //SimpleMailMessage只能用来发送text格式的邮件     
        SimpleMailMessage mail = new SimpleMailMessage();    
        
        String email[] = emails.split(";");   
        for(int i=0;i<email.length;i++) {   
            try {      
                mail.setTo(email[i]);//接受者   
                mail.setFrom("wujie@ascbank.com");    
                mail.setSubject(subject);//主题      
                mail.setText(text);//邮件内容      
                sender.send(mail);//send(mail);      
            } catch (Exception e) {      
                e.printStackTrace();     
            }   
        }   
    }   



    @Test
    public void test() throws MessagingException{
    	 //获取JavaMailSender bean      
    	JavaMailSenderImpl sender = (JavaMailSenderImpl) ctx.getBean("mailSender");     
         sender. testConnection() ;
    }
    /**  
     * @function:测试邮件发送  
     */  
    public static void main(String[] args) {       
    	
        String  mail_title  = "注册成功";   
        String  mail_content    = "恭喜!您已经注册成功.<BR>欢迎使用本网站,主页地址:<a href='http://www.baidu.com'/>";   
        EmailUtil email = EmailUtil.getInstance();      
        email.sentEmails("446321091@qq.com",mail_title,mail_content);   
    }      
}

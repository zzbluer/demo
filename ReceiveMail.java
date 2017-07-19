package com.it.gui.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

public class ReceiveMail {
	public static String myEmailAccount = "bboyzxgui@163.com";
	public static String myEmailPassword = "181856zou";

	public static void main(String[] args) throws MessagingException, IOException {
		
		Properties props = new Properties();  
        props.setProperty("mail.store.protocol", "pop3");  
        props.setProperty("mail.pop3.host", "pop.163.com");
        
        // 使用Properties对象获得Session对象  
        Session session = Session.getInstance(props);  
        session.setDebug(true);
        // 利用Session对象获得Store对象，并连接pop3服务器  
        Store store = session.getStore();  
        store.connect("pop.163.com", myEmailAccount, myEmailPassword);
        // 获得邮箱内的邮件夹Folder对象，以"只读"打开  
        Folder folder = store.getFolder("inbox");//邮件夹名称只能指定为"INBOX"  
        folder.open(Folder.READ_ONLY);
        // 获得邮件夹Folder内的所有邮件Message对象  
        Message [] messages = folder.getMessages();
        
        int mailCounts = messages.length;
        System.out.println("邮件数量====》》" + mailCounts);
        for(int i = mailCounts-1; i > mailCounts-3; i--) {  
              
            String subject = messages[i].getSubject();  
            String from = (messages[i].getFrom()[0]).toString(); 
              
            System.out.println("第 " + (i+1) + "封邮件的主题：" + subject);  
            System.out.println("第 " + (i+1) + "封邮件的发件人地址：" + from);  
              
            System.out.println("是否打开该邮件(yes/no)?：");  
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
            String input = br.readLine();  
            if("yes".equalsIgnoreCase(input)) {  
                // 直接输出到控制台中  
                messages[i].writeTo(System.out);  
            }             
        }  
        folder.close(false);  
        store.close();
	}

}

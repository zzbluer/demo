package com.it.gui.mail;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {
	public static String myEmailAccount = "bboyzxgui@163.com";
	public static String myEmailPassword = "181856zou";
	public static String receiveMailAccount = "";
	public static String subject = "MailDemo";
	public static String content = "你好啊！";
	// 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
	// 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
	public static String myEmailSMTPHost = "smtp.163.com";

	public static void main(String[] args) throws Exception {
		// 创建参数配置, 用于连接邮件服务器的参数配置（发送邮件时才需要用到）
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）,为什么是mail.transport.protocol？
																	// 在Session.class中getProperty("mail.transport.protocol")保持一致
		properties.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的 SMTP 服务器地址
		properties.setProperty("mail.smtp.auth", "false"); // 需要请求认证

		// 使用javaMail发送邮件的5个步骤
		// 1、创建session
		//    开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		Session session = Session.getDefaultInstance(properties); // 根据参数配置，创建会话对象（为了发送邮件准备的）
		session.setDebug(true);
		// 2、 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();
		// 3、连接上服务器
		transport.connect(myEmailSMTPHost, myEmailAccount, myEmailPassword);
		// transport.connect(myEmailAccount, myEmailPassword);
		// 4、 创建邮件
		MimeMessage message = mailContent(session);
		// 5、发送邮件、 关闭连接
		//transport.sendMessage(message, message.getAllRecipients());
		transport.close();

		// 保存设置
		message.saveChanges();
	}

	public static MimeMessage mailContent(Session session) throws Exception {
		// 1、创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 2、设置邮件发送人
		message.setFrom(new InternetAddress(myEmailAccount));
		// 3、设置邮件的接受者
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(myEmailAccount));
		// 4、设置邮件的主题
		message.setSubject(subject);
		// 5、邮件的文本内容
		message.setContent(content, "text/html;charset=UTF-8");
		imageMail(message);
		
		// 设置发送时间
		message.setSentDate(new Date());
		return message;
	}

	public static MimeMessage imageMail(MimeMessage message) throws Exception {
		//创建邮件正文
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("这是一封邮件正文带图片<img src='cid:123.jpg'>的邮件", "text/html;charset=UTF-8");
		
		//创建邮件附件，准备图片数据
		MimeBodyPart image = new MimeBodyPart();
		//??如果使用上面的text对象，在邮件的正文将不会显示：这是一封邮件正(省略)...，为什么?
		DataHandler dh = new DataHandler(new FileDataSource("src\\123.jpg"));
		image.setDataHandler(dh);
		image.setContentID("scene.jpg");
		// 描述数据关系
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text);
		mm.addBodyPart(image);
		mm.setSubType("related");
		
		message.setContent(mm);
		message.saveChanges();
		//将创建好的邮件写入到E盘以文件的形式进行保存
		message.writeTo(new FileOutputStream("F:\\ImageMail.eml"));
		//返回创建好的邮件
		return message;
	}
	
	public static MimeMessage attachMail(MimeMessage message) throws Exception {
		//创建邮件正文
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("使用JavaMail创建的带附件的邮件", "text/html;charset=UTF-8");
		//创建邮件附件，准备图片数据
		MimeBodyPart attach = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource("src\\321.xlsx"));
		attach.setDataHandler(dh);
		attach.setContentID(dh.getName());
		
		// 描述数据关系
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text);
		mm.addBodyPart(attach);
		mm.setSubType("mixed");
		
		message.setContent(mm);
		message.saveChanges();
		return message;
	}

}

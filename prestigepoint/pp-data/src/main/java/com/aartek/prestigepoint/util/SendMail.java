package com.aartek.prestigepoint.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendMail {

	@Autowired
	private static JavaMailSenderImpl javaMailSender;

	public static String sendEmail(String toEmail, String userPassword, String firstName) {
		try {
			Message message = new MimeMessage(SendMailProperty.mailProperty());
			message.setFrom(new InternetAddress(IConstant.FROM_EMAIL_ID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("Regards:registration confirmation");
			String msg = "Dear " + firstName + ",<br>";
			msg += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You have been registered with prestigepoint below are your user id and password:<br>";
			msg += " <br>";
			msg += "<b>User Name:</b>" + toEmail;
			msg += " <br>";
			msg += "<b>Password:</b>" + userPassword;
			msg += " <br>";
			msg += "Regards,<br>";
			msg += "Prestige Point";
			message.setContent(msg, "text/html");
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	/* Confirmation link after student registration */

	public static String confirmationMail(String toEmail, String userPassword, String firstName, int regId) {
		try {
			Message message = new MimeMessage(SendMailProperty.mailProperty());
			
			message.setFrom(new InternetAddress(IConstant.FROM_EMAIL_ID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("Regards:registration confirmation");
			String msg = "Dear " + firstName + ",<br>";
			msg += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You have been registered with prestigepoint below are confirmation link click on link and access prestigepoint account:<br>";
			msg += " <br>";
			msg += "<b>User Name:</b>" + toEmail;
			msg += " <br>";
			msg += "<b>Password:</b>" + userPassword;
			msg += " <br>";
		/*	msg += "http://localhost:9090/pp-web/verify.do?registrationId=" + regId;*/ // used
        	msg += "http://prestigepoint.in/verify.do?registrationId=" + regId; //used on the server
			msg += " <br>";
			msg += "Regards,<br>";
			msg += "Prestige Point";
			message.setContent(msg, "text/html");
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	/* Forgot password block start */
	public static String forgotPassword(String toEmail, String userPassword, String firstName) {

		try {
			Message message = new MimeMessage(SendMailProperty.mailProperty());
			message.setFrom(new InternetAddress(IConstant.FROM_EMAIL_ID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("Regards:Forgot password");
			String msg = "Dear " + firstName + ",<br>";
			msg += "&nbsp;&nbsp;&nbsp;We have received a request to email your password .Please find your log-in credentials below:<br>";
			msg += " <br>";
			msg += "Email ID /Username : " + toEmail + "<br>";
			msg += "Password : " + userPassword + "<br>";
			msg += " <br>";
			msg += "If you have not raised this request , please contact us email at hrd@prestigepoint.in<br>";
			msg += " <br>";
			msg += "Regards,<br>";
			msg += "Prestige Point";
			message.setContent(msg, "text/html");
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	/* End */

	public static void emiMail(String emailId, Integer submittedFee, String firstName, Double totalFee, Integer amount,
			String date) {

		try {
			Message message = new MimeMessage(SendMailProperty.mailProperty());
			message.setFrom(new InternetAddress(IConstant.FROM_EMAIL_ID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
			message.setSubject("Regards:Acknowledgement letter for submitting pending amount");
			String msg = "Dear " + "<b>" + firstName + ",<br>";
			msg += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This letter is to acknowledge for submitting  of your EMI amount is-: "
					+ amount + "&nbsp;&nbsp;Your Total fee is:" + totalFee;
			msg += " <br>";
			msg += "Your submitted fee is:" + "<b>" + submittedFee;
			msg += " <br>";
			msg += " <br>";
			msg += "Regards & Thanks <br>";
			msg += "Prestige Point";
			message.setContent(msg, "text/html");
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return;

	}

	public static void editEmiMail(String emailId, Integer submittedFee, String firstName, Double totalFee,
			Integer amount, String date) {

		try {
			Message message = new MimeMessage(SendMailProperty.mailProperty());
			message.setFrom(new InternetAddress(IConstant.FROM_EMAIL_ID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
			message.setSubject("Regards:Acknowledgement letter for Editting your Previous Emi amount");
			String msg = "Dear " + "<b>" + firstName + ",<br>";
			msg += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sorry This letter is to acknowledge for editting  your EMI Amount because some of reason we add wrong EMI. Your Submmited EMI Amount is-: "
					+ amount + "&nbsp;&nbsp;Your Total fee is:" + totalFee;
			msg += " <br>";
			msg += "Your submitted fee is:" + "<b>" + submittedFee;
			msg += " <br>";
			msg += " <br>";
			msg += "Regards & Thanks <br>";
			msg += "Prestige Point";
			message.setContent(msg, "text/html");
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return;
	}

	public static String forgotPasswordAdmin(String toEmail, String userPassword) {

		try {
			Message message = new MimeMessage(SendMailProperty.mailProperty());
			message.setFrom(new InternetAddress(IConstant.FROM_EMAIL_ID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("Regards:Forgot password");
			String msg = "Dear  Admin,<br>";
			msg += "&nbsp;&nbsp;&nbsp;We have received a request to email your password .Please find your log-in credentials below:<br>";
			msg += " <br>";
			msg += "Email ID /Username : " + toEmail + "<br>";
			msg += "Password : " + userPassword + "<br>";
			msg += " <br>";
			msg += "If you have not raised this request , please contact us email at hrd@prestigepoint.in<br>";
			msg += " <br>";
			msg += "Regards,<br>";
			msg += "Prestige Point";
			message.setContent(msg, "text/html");
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	/*public static String sendMail(String emailTo, String subject, String message) {
		try {
			Message message1 = new MimeMessage(SendMailProperty.mailProperty());
			message1.setFrom(new InternetAddress(IConstant.FROM_EMAIL_ID));
			message1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
			message1.setSubject("Regards:registration confirmation");
			message1.setContent(message, "text/html");
			Transport.send(message1);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return null;
	}*/

	/*public static void sendMail(String emailId, String subject, String message, String originalFilename) {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(IConstant.FROM_EMAIL_ID);
			helper.setTo(emailId);
			helper.setSubject(subject);
			helper.setText(message);
			FileSystemResource file = new FileSystemResource(originalFilename);
			helper.addAttachment(file.getFilename(), file);
			javaMailSender.send(mimeMessage);
			System.out.println("Mail sent successfully.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
			
		}
*/
	
	}

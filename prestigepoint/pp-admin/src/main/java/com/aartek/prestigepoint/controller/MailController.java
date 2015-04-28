package com.aartek.prestigepoint.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aartek.prestigepoint.model.AdminLogin;
import com.aartek.prestigepoint.model.Registration;
import com.aartek.prestigepoint.service.RegistrationService;
import com.aartek.prestigepoint.validator.SendValidator;

@Controller
public class MailController {

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private SendValidator sendValidator;


	private static Logger logger = LoggerFactory
			.getLogger(MailController.class);

	/**
	 * Display mail sending page
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @param emailId
	 * @return
	 */
	@RequestMapping("/sendMail")
	public String showSendMail(Map<String, Object> map, Model model,
			HttpServletRequest request,
			@RequestParam(required = false) String emailId) {
		HttpSession session = request.getSession();
		logger.info("Hello Mayank");
		AdminLogin loginMember = (AdminLogin) session.getAttribute("login");
		if (loginMember != null) {
			map.put("Registration", new Registration());
			model.addAttribute("emailId", emailId);
			return "sendMail";
		} else {
			return "redirect:/login.do";
		}
	}

	/**
	 * Use for send mail to particular address or all students or all enquires.
	 * 
	 * @param registration
	 * @param result
	 * @param model
	 * @param map
	 * @param request
	 * @param attachFile
	 * @return
	 */
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public String sendEmail(
			@ModelAttribute("Registration") Registration registration,
			BindingResult result, ModelMap model, Map<String, Object> map,
			HttpSession session, HttpServletRequest request,
			final @RequestParam CommonsMultipartFile attachFile) {
		// reads form input
		List<String> emailList = new ArrayList<String>();
		final String emailTo = registration.getEmailId();
		final String subject = registration.getSubject();
		final String message = registration.getMessage();
		if (emailTo == null || emailTo.isEmpty()) {
			if (registration.getAllStudent() != null) {
				if (registration.getAllStudent().equals("allstudent")) {
					emailList = registrationService.getallStudentEmailId();
					final String[] email = emailList
							.toArray(new String[emailList.size()]);

					mailSender.send(new MimeMessagePreparator() {
						public void prepare(MimeMessage mimeMessage)
								throws Exception {
							MimeMessageHelper messageHelper = new MimeMessageHelper(
									mimeMessage, true, "UTF-8");
							InternetAddress[] addressTo = new InternetAddress[email.length];
							for (int i = 0; i < email.length; i++) {
								addressTo[i] = new InternetAddress(email[i]);
							}
							messageHelper.setFrom("emailTo");
							messageHelper.setTo(email);
							messageHelper.setSubject(subject);
							messageHelper.setText(message);
							// determines if there is an upload file, attach it
							// to the
							// e-mail
							String attachName = attachFile
									.getOriginalFilename();
							if (!attachFile.equals("")) {
								messageHelper.addAttachment(attachName,
										new InputStreamSource() {
											public InputStream getInputStream()
													throws IOException {
												return attachFile
														.getInputStream();
											}
										});
							}
						}

					});
				}
			}
			if (registration.getAllEnquiry() != null) {
				if (registration.getAllEnquiry().equals("allenquiry")) {
					emailList = registrationService.getallEnquiryEmailId();
					final String[] email = emailList
							.toArray(new String[emailList.size()]);
					mailSender.send(new MimeMessagePreparator() {
						public void prepare(MimeMessage mimeMessage)
								throws Exception {
							MimeMessageHelper messageHelper = new MimeMessageHelper(
									mimeMessage, true, "UTF-8");
							InternetAddress[] addressTo = new InternetAddress[email.length];
							for (int i = 0; i < email.length; i++) {
								addressTo[i] = new InternetAddress(email[i]);
							}
							messageHelper.setFrom("emailTo");
							messageHelper.setTo(email);
							messageHelper.setSubject(subject);
							messageHelper.setText(message);
							String attachName = attachFile
									.getOriginalFilename();
							if (!attachFile.equals("")) {
								messageHelper.addAttachment(attachName,
										new InputStreamSource() {
											public InputStream getInputStream()
													throws IOException {
												return attachFile
														.getInputStream();
											}
										});
							}
						}

					});
					// System.out.println("attachFile: " +
					// attachFile.getOriginalFilename());
					/*
					 * mailSender.send(new MimeMessagePreparator() { public void
					 * prepare(MimeMessage mimeMessage) throws Exception {
					 * MimeMessageHelper messageHelper = new
					 * MimeMessageHelper(mimeMessage, true, "UTF-8");
					 * messageHelper.setTo(emailTo); InternetAddress[] addressTo
					 * = new InternetAddress[email.length]; for (int i = 0; i <
					 * email.length; i++) { addressTo[i] = new
					 * InternetAddress(email[i]); }
					 * messageHelper.setFrom("emailTo");
					 * messageHelper.setTo(email);
					 * messageHelper.setSubject(subject);
					 * messageHelper.setText(message); // determines if there is
					 * an upload file, attach it // to // the // e-mail String
					 * attachName = attachFile.getOriginalFilename(); if
					 * (!attachFile.equals("")) {
					 * messageHelper.addAttachment(attachName, new
					 * InputStreamSource() { public InputStream getInputStream()
					 * throws IOException { return attachFile.getInputStream();
					 * } }); } }
					 * 
					 * });
					 */
				}
			}
		}
		if (!emailTo.isEmpty() && emailTo != null) {
			final String[] email2 = emailTo.split(",");
			mailSender.send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper messageHelper = new MimeMessageHelper(
							mimeMessage, true, "UTF-8");
					InternetAddress[] addressTo = new InternetAddress[email2.length];
					for (int i = 0; i < email2.length; i++) {
						addressTo[i] = new InternetAddress(email2[i]);
					}
					messageHelper.setFrom("emailTo");
					messageHelper.setTo(email2);
					messageHelper.setSubject(subject);
					messageHelper.setText(message);
					// determines if there is an upload file, attach it to the
					// e-mail
					String attachName = attachFile.getOriginalFilename();
					if (!attachFile.equals("")) {
						messageHelper.addAttachment(attachName,
								new InputStreamSource() {
									public InputStream getInputStream()
											throws IOException {
										return attachFile.getInputStream();
									}
								});
					}
				}

			});
		}
		return "redirect:/mailSuccess.do";
	}

	/**
	 * Use for show mail sending success message.
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/mailSuccess")
	public String showSuccessPage(Map<String, Object> map, Model model,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminLogin loginMember = (AdminLogin) session.getAttribute("login");
		if (loginMember != null) {
			return "mailSuccess";
		} else {
			return "redirect:/login.do";
		}
	}

	/**
	 * Use for get email address for sending mail.
	 * 
	 * @param model
	 * @param map
	 * @param request
	 * @param emailId
	 * @return
	 */
	@RequestMapping(value = "/getEmailId", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String sendSingleMail(ModelMap model, Map<String, Object> map,
			HttpServletRequest request,
			@RequestParam(required = false) String emailId) {
		model.addAttribute("emailId", emailId);
		return "redirect:/sendMail.do";
	}
}

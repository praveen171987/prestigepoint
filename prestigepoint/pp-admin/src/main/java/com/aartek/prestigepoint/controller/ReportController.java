package com.aartek.prestigepoint.controller;
/* sandeep jariya
 this controller class is design for enquiry view*/


import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aartek.prestigepoint.model.AdminLogin;
import com.aartek.prestigepoint.model.Enquiry;
import com.aartek.prestigepoint.model.Year;
import com.aartek.prestigepoint.service.CourseService;
import com.aartek.prestigepoint.service.EnquiryService;
import com.aartek.prestigepoint.util.IConstant;


@Controller
public class ReportController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private EnquiryService enquiryService;
	
	@RequestMapping("/viewEnquiryByMonth")
	public String showEnquiryPage(@ModelAttribute("Enquiry") Enquiry enquiry, Map<String, Object> map, Model model,
			HttpServletRequest request) {
		 HttpSession session = request.getSession();
		    AdminLogin loginMember = (AdminLogin) session.getAttribute("login");
		    if (loginMember != null) {
		    	map.put("Enquiry", new Enquiry());
		 List<Year> yearList = null;
		 yearList = courseService.getAllYearName();
		 model.addAttribute("year", yearList);
		 
		  return "viewEnquiryByMonth";
			}else {
			      return "redirect:/login.do";
		    }
		  }
	
	@RequestMapping("/addAdminEnquiry")
	  public String showaddAddEnquiryPage(@ModelAttribute("Enquiry")Enquiry enquiry, Model model, Map<String, Object> map,
			  @RequestParam(required = false) String message,
	      HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    AdminLogin loginMember = (AdminLogin) session.getAttribute("login");
	    if (loginMember != null) {
	    	 map.put("Enquiry", new Enquiry());
	       return "addAdminEnquiry";
	    } else {
	      return "redirect:/login.do";
	    }
	  }
	
	@RequestMapping(value = "/addAdminEnquiryAction", method = { RequestMethod.GET, RequestMethod.POST })
	  public String addEnquiryByAdmin(@ModelAttribute("Enquiry")Enquiry enquiry, BindingResult result, Model model,Map<String, Object> map,
			  @RequestParam(required = false) String message) throws ParseException {
	    boolean status = false;
	    status = enquiryService.addAdminEnquiry(enquiry);
	        if (status) {
	        		model.addAttribute("message", IConstant.ENQUIRY_BY_ADMIN_SUCCESS);
	        }else{
	        	model.addAttribute("message", IConstant.ENQUIRY_BY_ADMIN_FAIL);
	        	}
	        map.put("Enquiry", new Enquiry());
	    return "addAdminEnquiry";
	  }
	
	
	@RequestMapping(value = "/getEnquiryDetails", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String viewEnquiryReport(
			@ModelAttribute("Enquiry") Enquiry enquiry,
			BindingResult result, ModelMap model, Map<String, Object> map,
			HttpServletRequest request,
			@RequestParam(required = false) Integer enquiryId) throws ParseException {
		HttpSession session = request.getSession();
		AdminLogin loginMember = (AdminLogin) session.getAttribute("login");
		List enquirylist = null;
		String method = request.getMethod();
		if (loginMember != null) {
			List<Year> yearList = null;
			
			yearList = courseService.getAllYearName();
			
			if (!enquiry.getMonth().equals("0") && !enquiry.getYear().equals("0")) {
				enquirylist = enquiryService
						.getMonthAndYearWiseEnquiryDetails(enquiry.getMonth(), enquiry.getYear());
				if(enquirylist!=null){
				model.addAttribute("enquirylist", enquirylist);
				}
			}else{
			if (enquiry.getMonth().equals("0")) {
				enquirylist = enquiryService
						.getYearWiseEnquiry(enquiry.getYear());
				if(enquirylist!=null){
				model.addAttribute("enquirylist", enquirylist);
				}
			}else{
				enquirylist = enquiryService
						.getMonthWiseEnquiry(enquiry.getMonth());
				if(enquirylist!=null){
				model.addAttribute("enquirylist", enquirylist);
					}
				}
			}
			model.addAttribute("year", yearList);
		}else{
			model.addAttribute("message", "Please select atleast one");
		}
		 return "viewEnquiryByMonth";
	}

	
}
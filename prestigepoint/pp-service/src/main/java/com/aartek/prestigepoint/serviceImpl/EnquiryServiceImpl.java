package com.aartek.prestigepoint.serviceImpl;

//import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aartek.prestigepoint.model.Enquiry;
import com.aartek.prestigepoint.repository.EnquiryRepository;
import com.aartek.prestigepoint.service.EnquiryService;
import com.aartek.prestigepoint.util.CamelCase;
import com.aartek.prestigepoint.util.DateFormat;
import com.aartek.prestigepoint.util.IConstant;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	@Autowired
	private EnquiryRepository enquiryRepository;

	public void addEnquiryMessage(Enquiry enquiry) throws ParseException {
		java.text.DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		enquiry.setIsDeleted(IConstant.IS_DELETED);
		enquiry.setDate(DateFormat.getYYYYMMDDDate(dateFormat1.format(date)));
		enquiry.setEnquiryBy("online");
		enquiry.setQualification("N/A");
		enquiry.setHandledBy("N/A");
		enquiry.setReferenceMobileNo("N/A");
		enquiry.setReferenceName("N/A");/*
										 * enquiry.setTrainingType("N/A");
										 */
		enquiry.setCollage("N/A");
		enquiry.setStatus(0);
		enquiry.setName(CamelCase.produceCamelCase(enquiry.getName()));
		enquiryRepository.addEnquiryMessage(enquiry);
	}

	public boolean addAdminEnquiry(Enquiry enquiry) throws ParseException {
		enquiry.setDate(DateFormat.getMMDDYYYYDateFormat((enquiry.getDate())));
		enquiry.setName(CamelCase.produceCamelCase(enquiry.getName()));
		enquiry.setLastName(CamelCase.produceCamelCase(enquiry.getLastName()));
		enquiry.setHandledBy(CamelCase.produceCamelCase(enquiry.getHandledBy()));
		enquiry.setIsDeleted(IConstant.IS_DELETED);
		enquiry.setEnquiryBy("admin");
		enquiry.setStatus(0);
		boolean status = false;
		if (enquiry != null) {
			enquiry.setIsDeleted(IConstant.IS_DELETED);
			status = enquiryRepository.addAdminEnquiry(enquiry);

		}

		return status;
	}

	@SuppressWarnings("rawtypes")
	public List getYearWiseEnquiry(String year) {
		List enquirylist = null;
		enquirylist = enquiryRepository.getYearWiseEnquiry(year);
		return enquirylist;

	}

	@SuppressWarnings("rawtypes")
	public List getMonthAndYearWiseEnquiryDetails(String month, String year) {
		List enquirylist = new ArrayList();
		enquirylist = enquiryRepository.getMonthAndYearWiseEnquiryDetails(
				month, year);
		return enquirylist;
	}

	@SuppressWarnings("rawtypes")
	public List getMonthWiseEnquiry(String month) {
		List enquirylist = new ArrayList();
		enquirylist = enquiryRepository.getMonthWiseEnquiry(month);
		return enquirylist;
	}
}

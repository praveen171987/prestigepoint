package com.aartek.prestigepoint.repository;

import java.util.List;

import com.aartek.prestigepoint.model.Enquiry;

@SuppressWarnings("rawtypes")
public interface EnquiryRepository {

	public void saveEnquiryMessage(Enquiry enquiry);

	public boolean saveAdminEnquiry(Enquiry enquiry);

	public List getMonthAndYearWiseEnquiryDetails(String month, String year);

	public List getYearWiseEnquiry(String year);

	public List getMonthWiseEnquiry(String month);

	public List<Object> editEnquiry(Integer enquiryId);

	public void deleteEnquiry(Integer enquiryId);

}

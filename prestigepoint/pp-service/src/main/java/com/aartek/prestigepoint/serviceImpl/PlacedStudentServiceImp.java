package com.aartek.prestigepoint.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aartek.prestigepoint.model.AddPlacedStudent;
import com.aartek.prestigepoint.repository.PlacedStudentRepository;
import com.aartek.prestigepoint.service.PlacedStudentService;
import com.aartek.prestigepoint.util.DateFormat;
import com.aartek.prestigepoint.util.IConstant;

@Service
public class PlacedStudentServiceImp implements PlacedStudentService {

    @Autowired
    private PlacedStudentRepository placedStudentRepository;

    public java.util.List<AddPlacedStudent> viewAllPlacedStudent() {
        List<AddPlacedStudent> list = new ArrayList<AddPlacedStudent>();
        list = placedStudentRepository.viewAllPlacedStudent();
        return list;
    }

    public AddPlacedStudent updatePlacedStudentInformation(Integer studentId) {
        List<Object> list = new ArrayList<Object>();
        AddPlacedStudent addPlacedStudent = null;
        list = placedStudentRepository.updatePlacedStudentInformation(studentId);
        for (Object object : list) {
            addPlacedStudent = (AddPlacedStudent) object;
        }
        list = null;// it is used for memory save
        return addPlacedStudent;

    }

    /*
     * getPlacedStudentDetails is used for change the format of date
     */
    public List<AddPlacedStudent> generatePlacedStudentReportMonthlyWise(String fromDate, String toDate) {
        List<AddPlacedStudent> placedStudentList = placedStudentRepository.generatePlacedStudentReportMonthlyWise(
                        DateFormat.getMMDDYYYYDateFormat(fromDate), DateFormat.getMMDDYYYYDateFormat(toDate));
        return placedStudentList;

    }

    public boolean addStudentInformation(AddPlacedStudent addPlacedStudent) {
        addPlacedStudent.setIsDeleted(IConstant.IS_DELETED);
        addPlacedStudent.setReleaseDateFromAartek(DateFormat.getMMDDYYYYDateFormat(addPlacedStudent
                        .getReleaseDateFromAartek()));
        placedStudentRepository.addStudentInformation(addPlacedStudent);
        return true;

    }

    public void deletePlacedStudentInformation(Integer studentId) {
        placedStudentRepository.deletePlacedStudentInformation(studentId);
    }

}
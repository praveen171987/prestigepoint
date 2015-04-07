package com.aartek.prestigepoint.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aartek.prestigepoint.model.PhotoInFooter;
import com.aartek.prestigepoint.repository.FooterPhotoRepository;
import com.aartek.prestigepoint.service.FooterPhotoService;
import com.aartek.prestigepoint.util.IConstant;
import com.aartek.prestigepoint.util.ImageFormat;

@Service
public class FooterPhotoServiceImpl implements FooterPhotoService {
  @Autowired
  private FooterPhotoRepository footerPhotoRepository;

  @Value("${pp.imagePath}")
  private String imagePath;
  /**
   * Method get all batch name form data base and display in grid
   */
  public List<PhotoInFooter> getAllStudentName() {
    List<PhotoInFooter> list = new ArrayList<PhotoInFooter>();
    list = footerPhotoRepository.getAllStudentName();
   
    return list;
  }

  /**
   * Get batch information for edit batch details.
   * 
   * @param batchId
   */
  public PhotoInFooter editSingleStudentDetail(Integer studentId) {
    List<Object> list = new ArrayList<Object>();
    PhotoInFooter photoInFooter = null;
    list = footerPhotoRepository.editSingleStudentDetail(studentId);
    PhotoInFooter photoInFooter2=(PhotoInFooter) list.get(0);
    BufferedImage img = null;
	  try {
	
		img = ImageIO.read(new File(imagePath + "/" + photoInFooter2.getStudentId()+ ".png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  String imageFormat="png";
	  String imageString =null;
      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      try {
          ImageIO.write(img, imageFormat, bos);
          byte[] imageBytes = bos.toByteArray();

          BASE64Encoder encoder = new BASE64Encoder();
          imageString = encoder.encode(imageBytes);

          bos.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
    for (Object object : list) {
    	photoInFooter = (PhotoInFooter) object;
    }
    photoInFooter.setImgPath(imageString);
    
    
    return photoInFooter;
  }

  /**
   * Method use for save batch information.
   * 
   * @param batch
   */
  public boolean addFooterPhoto(PhotoInFooter photoInFooter) {
    boolean status = false;
    if (photoInFooter != null) {
    	photoInFooter.setIsDeleted(IConstant.IS_DELETED);
    	photoInFooter.setIsStatusActive(IConstant.IS_STATUS_ACTIVE);
      status = footerPhotoRepository.addFooterPhoto(photoInFooter);
      BufferedImage newImg;
      String imageData = photoInFooter.getImgPath().replaceFirst("^data:image/[^;]*;base64,?", "");
      newImg = ImageFormat.decodeToImage(imageData);
      if (newImg != null) {
        try {
          File f = new File(imagePath);
          f.mkdirs();
          ImageIO.write(newImg, "png", new File(imagePath + "/" + photoInFooter.getStudentId() + ".png"));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return status;
    } else {
      return status;
    }
  }

public boolean changeStatusByStudentId(PhotoInFooter photoInFooter) {
	// TODO Auto-generated method stub
	boolean status = false;
	if(photoInFooter!=null){
		photoInFooter.setIsStatusActive(1);
		status=footerPhotoRepository.changeStatusByStudentId(photoInFooter);
		status=true;
	}
	return status;
}

public boolean uncheckStatusByStudentId(PhotoInFooter photoInFooter) {
	// TODO Auto-generated method stub
	
	boolean status = false;
	if(photoInFooter!=null){
		photoInFooter.setIsStatusActive(0);
		status=footerPhotoRepository.changeStatusByStudentId(photoInFooter);
		status=true;
	}
	return status;
}

public List<PhotoInFooter> getSingleStudentDetail(String studentId) {
	// TODO Auto-generated method stub
	List<PhotoInFooter> photoInFooter=null;
	if(studentId!=null){
		 photoInFooter=footerPhotoRepository.getSingleStudentDetail(studentId);
		
	}
	return photoInFooter;
}

public List<PhotoInFooter> listOfSelectedStudent() {
	// TODO Auto-generated method stub
	List<PhotoInFooter> photoInFooters=null;
	photoInFooters=footerPhotoRepository.listOfSelectedStudent();
	return photoInFooters;
}


  /**
   * Delete batch information.
   * 
   * @param batchId
   */
  public void deleteStudentData(Integer studentId) {
	  footerPhotoRepository.deleteStudentData(studentId);
  }

/*public boolean addBatch(PhotoInFooter photoInFooter) {
	// TODO Auto-generated method stub
	return false;
}*/

}
package com.bilel.TunisiaGate.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bilel.TunisiaGate.domains.Document;
import com.bilel.TunisiaGate.domains.User;
import com.bilel.TunisiaGate.repositories.DocumentRepository;
import com.bilel.TunisiaGate.repositories.UserRepository;

@Service
public class DocumentService {
  
@Autowired
private UserRepository userRepository;
	  
@Transactional
public void saveImageFile(Long userId, MultipartFile file) {

  try {
    User user = userRepository.findById(userId).get();

    byte[] byteObjects = new byte[file.getBytes().length];

    int i = 0;

    for (byte b : file.getBytes()) {
      byteObjects[i++] = b;
      user.setImage(byteObjects);

    }

    user.setImage(byteObjects);

    userRepository.save(user);
  } catch (IOException e) {
    // todo handle better
  //  log.error("Error occurred", e);

    e.printStackTrace();
  }


}

public User saveFile(Long userId,MultipartFile file) {
	  try {
		  
		    User user = new User();
		    byte[] byteObjects = new byte[file.getBytes().length];

		    int i = 0;

		    for (byte b : file.getBytes()) {
		      byteObjects[i++] = b;
		      user.setImage(byteObjects);

		    }
		 // return userRepository.save(doc);
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	  return null;
}

	 
		
  
		
		
		
		
		
		
		
}

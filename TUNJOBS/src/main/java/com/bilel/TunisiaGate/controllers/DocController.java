package com.bilel.TunisiaGate.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import com.bilel.TunisiaGate.domains.Document;
import com.bilel.TunisiaGate.domains.User;
import com.bilel.TunisiaGate.repositories.DocumentRepository;
import com.bilel.TunisiaGate.repositories.UserRepository;
import com.bilel.TunisiaGate.services.DocumentService;
import com.bilel.TunisiaGate.services.UserService;

@Controller
public class DocController {

	@Autowired
	private DocumentService documentService;
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	
	 
	
	 public DocController(DocumentService documentService, UserService userService) {
		super();
		this.documentService = documentService;
		this.userService = userService;
	}

	@GetMapping("user/{id}/image")
	  public String showUploadForm(@PathVariable String id, Model model ) {
	  model.addAttribute("user", userService.findById(Long.valueOf(id)).get());
	 // System.out.println(.getId());
	  return "imageuploadform";
	  } 

	  @PostMapping("user/{id}/image")
	  public String handleImagePost(@PathVariable String id,
			  @RequestParam("imagefile") MultipartFile file) {

	    documentService.saveImageFile(Long.valueOf(id), file);

	    return "redirect:/users";
	  }

		
		@GetMapping("/downloadFile/{id}")
		public ResponseEntity<byte[]> getFile(@PathVariable String id, HttpServletResponse response)
				throws IOException {
			Optional<User> fileOptional = userRepository.findById(Long.valueOf(id));
			String contentType = null;
			if (fileOptional.isPresent()) {
				//System.out.println("gello");
				User file = fileOptional.get();
				if (contentType == null) {
					contentType = "application/octet-stream";
				}
				return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/**"))
						.body(file.getImage());
			}
			//System.out.println("mello");
			return ResponseEntity.status(404).body(null);
		}
		 
	
}

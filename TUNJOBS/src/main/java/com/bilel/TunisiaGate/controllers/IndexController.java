package com.bilel.TunisiaGate.controllers;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bilel.TunisiaGate.domains.Role;
import com.bilel.TunisiaGate.domains.User;
import com.bilel.TunisiaGate.exceptions.NotFoundException;
import com.bilel.TunisiaGate.repositories.AnnonceRepository;
import com.bilel.TunisiaGate.repositories.UserRepository;
import com.bilel.TunisiaGate.services.DocumentService;
import com.bilel.TunisiaGate.services.UserService;

@Controller
public class IndexController {

	@Autowired
	private UserService userService ;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AnnonceRepository annonceRepository;
	
	@Autowired
	private DocumentService documentService;
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		
		model.addAttribute("users", userService.findAll());
		
		return"list";
	}
	
	/*
	 * //find user by id
	 * 
	 * @GetMapping("/user/{id}")
	 * 
	 * @ResponseBody public Optional<User> getUserByID(@PathVariable Long id) { //
	 * Optional<User> optUser = userRepository.findById(id); //
	 * model.addAttribute("userbyid", userRepository.findById(id)); return
	 * userService.findById(id); }
	 * 
	 * 
	 * 
	 * //delete student by id
	 * 
	 * @DeleteMapping("/user/{id}") public String deleteStudent(@PathVariable Long
	 * id) { return userRepository.findById(id) .map(student -> {
	 * userRepository.delete(student); return "Delete Successfully!";
	 * }).orElseThrow(() -> new NotFoundException("Student not found with id " +
	 * id)); }
	 * 
	 */
	@GetMapping("/getusername")
	@ResponseBody
	public Object currentUserName(Authentication principal)  {
		
		return principal.getName();
	}

    
    @GetMapping("/bilel")
    
    public String bilel (Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
    	model.addAttribute("bilou", userRepository.findByUsername(currentPrincipalName));
    	
    	
    	return "user/monprofile";
    }
    
    
    
    
  
    
    
}

package com.bilel.TunisiaGate.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bilel.TunisiaGate.domains.Annonces;
import com.bilel.TunisiaGate.domains.ConfirmationToken;
import com.bilel.TunisiaGate.domains.Doc;
import com.bilel.TunisiaGate.domains.Document;
import com.bilel.TunisiaGate.domains.Role;
import com.bilel.TunisiaGate.domains.User;
import com.bilel.TunisiaGate.repositories.AnnonceRepository;
import com.bilel.TunisiaGate.repositories.ConfirmationTokenRepository;
import com.bilel.TunisiaGate.repositories.RoleRepository;
import com.bilel.TunisiaGate.repositories.UserRepository;
import com.bilel.TunisiaGate.services.DocumentService;
import com.bilel.TunisiaGate.services.EmailService;
import com.bilel.TunisiaGate.services.UserService;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
	private AnnonceRepository annonceRepository;
	
	/*
	 * @InitBinder public void initBinder(WebDataBinder dataBinder) {
	 * StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(false);
	 * dataBinder.registerCustomEditor(String.class, stringTrimmerEditor); }
	 */

	@RequestMapping("/index")
	public String home() {
		return "index";
	}

	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/logout")
	public String logout() {
		return "login";
	}

	 
	
	
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("roless", new Role());
		//model.addAttribute("doc", new Document());
		
		// model.addAttribute("UserTypes", Gender.values());

		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid User user , BindingResult bindingResult,Role role,
			@RequestParam("files") MultipartFile files, Doc doc,Model model) throws IOException {

		if (bindingResult.hasErrors()) {
			return "register";
		}

		if (userService.isPresent(user.getUsername())) {
			model.addAttribute("exist", true);
			return "register";
		}
		if (userService.isPresentt(user.getEmail())) {
			model.addAttribute("exist1", true);
			return "register";
		}
		
		String docname = files.getOriginalFilename();
		Doc docum;
		docum = new Doc(docname, files.getContentType(), files.getBytes());
		
		user.setDoc(docum);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));	
		Role userRole = new Role(role.getRoleName());
		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
		//userService.createUser(user,role);
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		confirmationTokenRepository.save(confirmationToken);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setFrom("bileljarrahi6@gmail.com");
		mailMessage.setText("To confirm your account, please click here : "
				+ "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
		emailService.sendEmail(mailMessage);
		model.addAttribute("email", user.getEmail());
		return "login";
	}

	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		if (token != null) {
			User user = userRepository.findByEmailIgnoreCase(token.getUserEntity().getEmail());
			user.setIsEnabled(true);
			userRepository.save(user);
			modelAndView.setViewName("accountVerified");
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("success");
		}
		return modelAndView;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

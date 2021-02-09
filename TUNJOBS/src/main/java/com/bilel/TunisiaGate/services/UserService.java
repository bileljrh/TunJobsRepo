package com.bilel.TunisiaGate.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bilel.TunisiaGate.domains.Document;
import com.bilel.TunisiaGate.domains.Role;
import com.bilel.TunisiaGate.domains.User;
import com.bilel.TunisiaGate.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	DocumentService documentService;

	/*
	 * public void createUser(User user,Role rol) { BCryptPasswordEncoder encoder =
	 * new BCryptPasswordEncoder();
	 * user.setPassword(encoder.encode(user.getPassword()));
	 * 
	 * 
	 * Document document = new Document(doc.getData());
	 * 
	 * 
	 * user.setUserpic(document);
	 * 
	 * 
	 * document.setUser(user);
	 * 
	 * Role userRole = new Role(rol.getRoleName()); Set<Role> roles = new
	 * HashSet<>(); roles.add(userRole);
	 * 
	 * 
	 * 
	 * user.setRoles(roles); userRepository.save(user); }
	 * 
	 * 
	 * public void createAdmin(User user) { BCryptPasswordEncoder encoder = new
	 * BCryptPasswordEncoder();
	 * user.setPassword(encoder.encode(user.getPassword())); Role userRole = new
	 * Role("ADMIN"); List<Role> roles = new ArrayList<>(); roles.add(userRole);
	 * user.setRoles(roles); userRepository.save(user); }
	 */

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Boolean isPresent(String username) {
		User u = userRepository.findByUsername(username);
		if (u != null)
			return true;
		return false;
	}

	public User findByEmailIgnoreCase(String email) {
		return userRepository.findByEmailIgnoreCase(email);
	}

	public Boolean isPresentt(String email) {
		User u = userRepository.findByEmailIgnoreCase(email);
		if (u != null)
			return true;
		return false;
	}

	public List<User> findAll() {

		return userRepository.findAll();
	}

	public Optional<User> findById(Long id) {

		return userRepository.findById(id);
	}

}

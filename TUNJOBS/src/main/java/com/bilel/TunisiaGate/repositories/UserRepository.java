package com.bilel.TunisiaGate.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bilel.TunisiaGate.domains.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	User findByEmailIgnoreCase(String email);
	
   Optional<User> findById(Long id);
}

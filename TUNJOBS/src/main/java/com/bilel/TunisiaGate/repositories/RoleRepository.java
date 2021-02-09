package com.bilel.TunisiaGate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bilel.TunisiaGate.domains.Role;
import com.bilel.TunisiaGate.domains.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long>{

	
	String findByRoleName(RoleName roleName);
               
	
}

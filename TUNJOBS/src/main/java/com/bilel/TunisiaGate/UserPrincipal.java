package com.bilel.TunisiaGate;
import com.bilel.TunisiaGate.domains.Role;
import com.bilel.TunisiaGate.domains.User;
import com.bilel.TunisiaGate.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

	@Autowired
	UserRepository userRepository;

	private User user;
	
	public UserPrincipal(User user) {

		this.user = user;
		
		
		
	}
	

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().name()));
        }
         
        return authorities;
    }
	
	
	
	/*
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
	 * return Collections.singleton(new SimpleGrantedAuthority("CANDIDAT")); }
	 */

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

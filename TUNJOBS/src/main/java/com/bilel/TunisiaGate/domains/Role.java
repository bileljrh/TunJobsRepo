package com.bilel.TunisiaGate.domains;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	@Column(name ="name")

	
	@Enumerated(value = EnumType.STRING)
    private RoleName roleName;
	
	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	public Role() {
		
	}




	public Role(RoleName roleName) {
		super();
		this.roleName = roleName;
	}




	public Role(RoleName roleName, List<User> users) {
		super();
		this.roleName = roleName;
		this.users = users;
	}







	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}




	public RoleName getRoleName() {
		return roleName;
	}




	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}

	
	

}

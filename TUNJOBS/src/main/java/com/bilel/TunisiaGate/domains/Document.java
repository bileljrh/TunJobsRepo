package com.bilel.TunisiaGate.domains;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class Document {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
	

	@Lob
	private byte[] data;

	@OneToOne(cascade = CascadeType.ALL)   
	private User user;
	
	public Document() {}


	public Document( byte[] data) {
		super();
	
		this.data = data;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	


	public byte[] getData() {
		return data;
	}
	/*
	 * public String getDataEncoded() {s; }
	 */


	public void setData(byte[] data) {
		this.data = data;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}



	
}

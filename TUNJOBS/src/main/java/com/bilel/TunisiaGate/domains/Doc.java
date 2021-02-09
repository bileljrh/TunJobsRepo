package com.bilel.TunisiaGate.domains;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="docs")
public class Doc {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String docName;
	private String docType;
	
	@Lob
	private byte[] data;
	  @OneToOne(mappedBy = "doc")
	  private PostUserAnnonce postuserannonce;
	  
	  
	  @OneToOne(mappedBy = "doc")
	  private Annonces annonces;
	  
	  @OneToOne(mappedBy = "doc")
	  private User user;

	
	public Doc() {}

	
	
	public Doc(byte[] data) {
		super();
		this.data = data;
	}



	public Doc(String docName, String docType, byte[] data) {
		super();
		this.docName = docName;
		this.docType = docType;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}



	public PostUserAnnonce getPostuserannonce() {
		return postuserannonce;
	}



	public void setPostuserannonce(PostUserAnnonce postuserannonce) {
		this.postuserannonce = postuserannonce;
	}



	public Annonces getAnnonces() {
		return annonces;
	}



	public void setAnnonces(Annonces annonces) {
		this.annonces = annonces;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}


	

	
	
}

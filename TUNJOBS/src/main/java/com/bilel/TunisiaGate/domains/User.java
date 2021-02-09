package com.bilel.TunisiaGate.domains;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Size(max = 15)
	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@NotBlank(message = "first name may not be empty")
	@Size(max = 20)
	@Column(name = "first_name", nullable = false)
	private String firstname;

	@NotBlank(message = "last name may not be empty")
	@Size(max = 20)
	@Column(name = "last_name", nullable = false)
	private String lastname;

	@Size(max = 40)
	@Email
	@NotEmpty
	@Column(name = "email", nullable = false)
	private String email;
	
	

	@Column(name = "password")
	private String password;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;
     
    @Lob
    private byte[] image;

	@Column(name = "isEnabled")
	private Boolean isEnabled;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateNais;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "user")
	@JsonManagedReference
	private Set<Annonces> annonces = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "user")
	@JsonManagedReference
	private Set<PostUserAnnonce> postUserAnnonce = new HashSet<>();


	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "doc_id")
	
    private Doc doc;
	
	public User() {
        
        isEnabled = false;

	}
	
	public User(@NotEmpty @Size(max = 15) String username,
			@NotBlank(message = "first name may not be empty") @Size(max = 20) String firstname,
			@NotBlank(message = "last name may not be empty") @Size(max = 20) String lastname,
			@Size(max = 40) @Email @NotEmpty String email, String password, Gender gender, byte[] image,
			Boolean isEnabled, LocalDate dateNais, Set<Annonces> annonces, Set<Role> roles) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.image = image;
		this.isEnabled = isEnabled;
		this.dateNais = dateNais;
		this.annonces = annonces;
		this.roles = roles;
	}












	public byte[] getImage() {
		return image;
	}





















	public void setImage(byte[] image) {
		this.image = image;
	}





















	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	
	

	public Set<Role> getRoles() {
		return roles;
	}








	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}





	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public LocalDate getDateNais() {
		return dateNais;
	}

	public void setDateNais(LocalDate dateNais) {
		this.dateNais = dateNais;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Set<Annonces> getAnnonces() {
		return annonces;
	}

	public void setAnnonces(Set<Annonces> annonces) {
		this.annonces = annonces;
	}

	public Set<PostUserAnnonce> getPostUserAnnonce() {
		return postUserAnnonce;
	}

	public void setPostUserAnnonce(Set<PostUserAnnonce> postUserAnnonce) {
		this.postUserAnnonce = postUserAnnonce;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}
	
	
	
	
}

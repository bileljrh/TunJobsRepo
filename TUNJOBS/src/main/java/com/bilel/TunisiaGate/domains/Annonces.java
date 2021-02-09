package com.bilel.TunisiaGate.domains;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Annonces{


	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	
	@Size(min = 2, max = 30, message = "Product name must be between 2 and 30 characters.")
	@Column(name = "titre")
	private String titre;
	
	@Size(min = 31, max = 1000, message = "Product description must be between 31 and 1000 characters.")
	@Lob
	@Column(name = "description")
	private String description;	
	
	//@Size(min = 2, max = 10)
	@Column(name = "price", precision = 10, scale = 2)
    private double price;
	
	@Column(name = "nombrecandidat")
	private int nombreCandidat;
	
	@Enumerated(value = EnumType.STRING)
    private TypeEmploi typeEmploi;
	
	@Enumerated(value = EnumType.STRING)
    private VilleEmploi villeEmploi;
	
	@Enumerated(value = EnumType.STRING)
    private CategorieEmploi categorieEmploi;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user ;  
    
    

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "annonces")
	@JsonManagedReference
	private Set<PostUserAnnonce> postUserAnnonce = new HashSet<>();

    
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "doc_id")
  private Doc doc;

	
    
	public Annonces() {
		super();
	}
	


	public Annonces(
			@Size(min = 2, max = 30, message = "Product name must be between 2 and 30 characters.") String titre,
			String description, double price) {
		super();
		this.titre = titre;
		this.description = description;
		this.price = price;
		
		
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}




	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}



	public String getTitre() {
		return titre;
	}



	public void setTitre(String titre) {
		this.titre = titre;
	}



	public int getNombreCandidat() {
		return nombreCandidat;
	}



	public void setNombreCandidat(int nombreCandidat) {
		this.nombreCandidat = nombreCandidat;
	}



	public TypeEmploi getTypeEmploi() {
		return typeEmploi;
	}



	public void setTypeEmploi(TypeEmploi typeEmploi) {
		this.typeEmploi = typeEmploi;
	}



	public VilleEmploi getVilleEmploi() {
		return villeEmploi;
	}



	public void setVilleEmploi(VilleEmploi villeEmploi) {
		this.villeEmploi = villeEmploi;
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



	public CategorieEmploi getCategorieEmploi() {
		return categorieEmploi;
	}



	public void setCategorieEmploi(CategorieEmploi categorieEmploi) {
		this.categorieEmploi = categorieEmploi;
	}




	
	
}

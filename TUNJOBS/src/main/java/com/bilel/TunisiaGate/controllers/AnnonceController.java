package com.bilel.TunisiaGate.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bilel.TunisiaGate.domains.Annonces;
import com.bilel.TunisiaGate.domains.CategorieEmploi;
import com.bilel.TunisiaGate.domains.Doc;
import com.bilel.TunisiaGate.domains.PostUserAnnonce;
import com.bilel.TunisiaGate.domains.Role;
import com.bilel.TunisiaGate.domains.TypeEmploi;
import com.bilel.TunisiaGate.domains.User;
import com.bilel.TunisiaGate.domains.VilleEmploi;
import com.bilel.TunisiaGate.exceptions.NotFoundException;
import com.bilel.TunisiaGate.repositories.AnnonceRepository;
import com.bilel.TunisiaGate.repositories.DocRepository;
import com.bilel.TunisiaGate.repositories.PostUserAnnonceRepository;
import com.bilel.TunisiaGate.repositories.UserRepository;
import com.bilel.TunisiaGate.services.AnnonceService;
import com.bilel.TunisiaGate.services.DocStorageService;
import com.bilel.TunisiaGate.services.UserService;

@Controller

public class AnnonceController {

	@Autowired
	private AnnonceRepository annonceRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	
	@Autowired
	private DocStorageService docStorageService ;
	
	@Autowired
	private DocRepository docRepository;
	
	
	@Autowired
	private AnnonceService annonceService;

	@Autowired
	private PostUserAnnonceRepository postUserAnnonceRepository;

	@GetMapping("/user/{userId}/annonce")

	public String getContactByUserId(@PathVariable Long userId, Model model) {

		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("Student not found!");
		}
		System.out.println(annonceRepository.findByUserId(userId));
		model.addAttribute("byid", annonceRepository.findByUserId(userId));
		model.addAttribute("user", userService.findById(userId).get());
		return "monannonce";
	}

	@GetMapping("/user/{userId}/annonce/{annonceId}")
	public String showById(@PathVariable Long userId, @PathVariable Long annonceId, Model model) {
		// model.addAttribute("usr", userRepository.findById(userId));
		// if(annonceRepository.findByUserIdAndId(userId, annonceId))

		/*
		 * Authentication authentication =
		 * SecurityContextHolder.getContext().getAuthentication();
		 * 
		 * String currentPrincipalName = authentication.getName();
		 * 
		 * model.addAttribute("currentUser",
		 * userRepository.findByUsername(currentPrincipalName).getId());
		 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));


		model.addAttribute("annc", annonceRepository.findByUserIdAndId(userId, annonceId));
		model.addAttribute("annonceIid", annonceRepository.findById(annonceId).get());
		model.addAttribute("userIid", userService.findById(userId).get());
		return "articles/findbyid";
	}

	// les annonces de user conecter
	@GetMapping("/user/annonce/me")
	public String showByCurrentUser(Model model) {
		// model.addAttribute("usr", userRepository.findById(userId));
		// if(annonceRepository.findByUserIdAndId(userId, annonceId))

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));

		model.addAttribute("annc",
				annonceRepository.findByUserId(userRepository.findByUsername(currentPrincipalName).getId()));

		return "articles/findcurentannonce";
	}

	@GetMapping("/user/{userId}")

	public String addAnnonce(@PathVariable Long userId, Model model, User user) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));

		
		
		model.addAttribute("user", userService.findById(userRepository.findByUsername(currentPrincipalName).getId()));

		model.addAttribute("add", new Annonces());

		return "annonce";
	}

	@PostMapping("/user/{userId}")

	public String addAnnonce(@PathVariable Long userId, @Valid Annonces annonce,BindingResult result, 
			 @RequestParam("files") MultipartFile files, Doc doc,
			
			Model model) {
		
	
		// model.addAttribute("user", userService.findById(userId).get());
		return userRepository.findById(userId).map(user -> {
			String docname = files.getOriginalFilename();
			Doc docum;
			try {
				
			      
			docum = new Doc(docname, files.getContentType(), files.getBytes());

			annonce.setDoc(docum);
			
			annonce.setUser(user);
			annonceRepository.save(annonce);
			
			} catch (Exception e) {
				// TODO: handle exception
			}
			return "redirect:/index";
		}).orElseThrow(() -> new NotFoundException("user not found!"));
	}
	
	//affichage de l'image a partir de la table Doc (yekhdedh id_doc eli fi table annonce 
	// w ylawej fi table Doc el id hedheka w yaffichi taswira:

	  @GetMapping("annonce/{id}/annonceimage")
	  public void renderImageFromDB(@PathVariable Integer id, HttpServletResponse response)
	      throws IOException {
	    Doc doc = docRepository.findById(id).get();

	    if (doc.getData() != null) {
	      byte[] byteArray = new byte[doc.getData().length];
	      int i = 0;

	      for (Byte wrappedByte : doc.getData()) {
	        byteArray[i++] = wrappedByte; // auto unboxing
	      }

	      response.setContentType("image/jpeg");
	      InputStream is = new ByteArrayInputStream(byteArray);
	      IOUtils.copy(is, response.getOutputStream());
	    }
	  }

	
	
	

	// mise a jour d'une annonce

	@GetMapping("/edit/{userId}/{annonceId}")
	public String showUpdateForm(@PathVariable Long userId, @PathVariable Long annonceId, Model model) {
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));
		
		
		model.addAttribute("add", annonceRepository.findByUserIdAndId(userId, annonceId));
		model.addAttribute("user", userService.findById(userId).get());
		model.addAttribute("item", annonceRepository.findById(annonceId).get());

		return "update";
	}

	@RequestMapping(value = "/update/{userId}/annonce/{annonceId}", method = { RequestMethod.GET, RequestMethod.PUT })
	public String updateAssignment(@PathVariable Long userId, @PathVariable Long annonceId, Annonces annonceUpdated,
			Model model) {

		model.addAttribute("add", annonceRepository.findByUserIdAndId(userId, annonceId));
		model.addAttribute("user", userService.findById(userId).get());
		model.addAttribute("item", annonceRepository.findById(annonceId).get());
		return annonceRepository.findById(annonceId).map(annonce -> {
			annonce.setTitre(annonceUpdated.getTitre());
			annonce.setDescription(annonceUpdated.getDescription());
			annonce.setPrice(annonceUpdated.getPrice());
			annonce.setTypeEmploi(annonceUpdated.getTypeEmploi());
			annonce.setVilleEmploi(annonceUpdated.getVilleEmploi()); // NombreCandidat
			annonce.setCategorieEmploi(annonceUpdated.getCategorieEmploi());
			annonce.setNombreCandidat(annonceUpdated.getNombreCandidat());
			annonceRepository.save(annonce);
			return "redirect:/index";
		}).orElseThrow(() -> new NotFoundException("Assignment not found!"));
	}

	@RequestMapping(value = "/delete/{userId}/annonce/{annonceId}", method = { RequestMethod.GET,
			RequestMethod.DELETE })
	@ResponseBody
	public String deleteAssignment(@PathVariable Long userId,

			@PathVariable Long annonceId,Model model) {
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));
		

		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("Student not found!");
		}

		return annonceRepository.findById(annonceId).map(annonce -> {
			annonceRepository.delete(annonce);
			return "Deleted Successfully!";
		}).orElseThrow(() -> new NotFoundException("Contact not found!"));
	}

	// postuler

	@GetMapping("/postuler/{userId}/{annonceId}")

	public String postuler(@PathVariable Long userId, @PathVariable Long annonceId, Model model, User user,
			Annonces annonces) {

		model.addAttribute("user", userService.findById(Long.valueOf(userId)).get());
		model.addAttribute("annc", annonceRepository.findById(Long.valueOf(annonceId)).get());

		model.addAttribute("add", new PostUserAnnonce());
		model.addAttribute("bilel", new Doc());

		return "postule";
	}

	@PostMapping("/postuler/{userId}/{annonceId}")

	public String postuleDansAnnonce(@PathVariable Long userId, @PathVariable Long annonceId,
			@Valid PostUserAnnonce post, @RequestParam("files") MultipartFile files, Doc doc, Model model) {
		// model.addAttribute("user", userService.findById(userId).get());

		return userRepository.findById(userId).map(user -> {
			String docname = files.getOriginalFilename();
			Doc docum;
			try {
				docum = new Doc(docname, files.getContentType(), files.getBytes());

				post.setDoc(docum);

				docum.setPostuserannonce(post);

				post.setUser(user);
				// post.setCv(cv);
				Annonces annonces = annonceRepository.findById(annonceId).get();
				post.setAnnonces(annonces);
				postUserAnnonceRepository.save(post);
			} catch (IOException e) {

				e.printStackTrace();
			}
			return "redirect:/index";
		}).orElseThrow(() -> new NotFoundException("user not found!"));

	}

	@GetMapping("/offres")
	public String showAllAnnonces(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));

		model.addAttribute("offre", annonceRepository.findAll());
		return "articles/offres";
	}
	
	
	  
	/*
	 * @GetMapping("/index") public String showAllAnnc(Model model) { Authentication
	 * authentication = SecurityContextHolder.getContext().getAuthentication();
	 * 
	 * String currentPrincipalName = authentication.getName();
	 * 
	 * model.addAttribute("currentUser",
	 * userRepository.findByUsername(currentPrincipalName));
	 * 
	 * model.addAttribute("emploi", annonceRepository.findAll()); return "index"; }
	 */
	    
    @GetMapping({ "", "/", "/index" })
    public String annoncespage(HttpServletRequest request, Model model) {
        
        int page = 0; //default page number is 0 (yes it is weird)
        int size = 3; //default page size is 10
        
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));

        
        model.addAttribute("emploi", annonceRepository.findAll(PageRequest.of(page, size)));
        return "index";
    }

    
	/*
	 * @GetMapping("/search") public String serachAnnonces(Model
	 * model, @RequestParam(required=false,name="name") String name,
	 * 
	 * @RequestParam(required=false,name="desc") String desc ) {
	 * 
	 * model.addAttribute("annonce", annonceService.findByName(name)); return
	 * "user/search"; }
	 */

    //recherche dans la table annonce 
	
	@GetMapping("/search")
	public String getAllProjects(Model model, @RequestParam(value = "search", required = true) String search) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));

		
		model.addAttribute("emploi", ResponseEntity.ok().body(annonceService.getAll(search)).getBody());
		return "user/search";
	}
	
	
	@RequestMapping(value="/candidats/{annonceId}",method = RequestMethod.GET)
	public String findbyannonce(@PathVariable Long annonceId, Model model) {
		
				model.addAttribute("ListCandidats", postUserAnnonceRepository.findByAnnoncesId(annonceId));
		return"user/candidats";
	}
	
	
	
	
	
	// afficher le CV de candidat
	 @GetMapping("candidat/{id}/cv")
	  public void renderImage(@PathVariable Integer id, HttpServletResponse response)
	      throws IOException {
	    Doc doc = docRepository.findById(id).get();

	    if (doc.getData() != null) {
	      byte[] byteArray = new byte[doc.getData().length];
	      int i = 0;

	      for (Byte wrappedByte : doc.getData()) {
	        byteArray[i++] = wrappedByte; // auto unboxing
	      }

	      response.setContentType("application/pdf");
	      InputStream is = new ByteArrayInputStream(byteArray);
	      IOUtils.copy(is, response.getOutputStream());
	    }
	  }

	
	
	
	//j'ai postuler dans !!
	 
	/*
	 * @RequestMapping(value="/offres/{userId}",method = RequestMethod.GET) public
	 * String findbyusers(@PathVariable Long userId, Model model) {
	 * 
	 * model.addAttribute("ListOffres",
	 * postUserAnnonceRepository.findByUserId(userId)); return"user/mesoffres"; }
	 */
	 
	 @RequestMapping(value="/candidatures/{userId}",method = RequestMethod.GET)
	 public String myposts(@PathVariable Long userId,Model model) {
		 
		 
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			String currentPrincipalName = authentication.getName();

			model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));
			
			model.addAttribute("ListOffres", postUserAnnonceRepository.findByUserId(userId));
		 return "user/mesoffres";
	 }
	
	//afficher par type :
	 
	 
	 @RequestMapping(value="/type/{type}",method = RequestMethod.GET)
	 public String showbytype(@PathVariable TypeEmploi type,Model model) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			String currentPrincipalName = authentication.getName();

			model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));

		 
		 model.addAttribute("typeEmp", annonceRepository.findByTypeEmploi(type));
		 return "articles/type";
	 }
	 
	 //afficher par ville
	 
	 @RequestMapping(value="/ville/{ville}",method = RequestMethod.GET)
	 public String showbyRegion(@PathVariable VilleEmploi ville,Model model) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			String currentPrincipalName = authentication.getName();

			model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));

		 
		 model.addAttribute("villeEmp", annonceRepository.findByVilleEmploi(ville));
		 return "articles/ville";
	 }
	 //afficher par categorie
	 
	 @RequestMapping(value="/categorie/{categorie}",method = RequestMethod.GET)
	 public String showbyCategorie(@PathVariable CategorieEmploi categorie,Model model) {
		 
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			String currentPrincipalName = authentication.getName();

			model.addAttribute("currentUser", userRepository.findByUsername(currentPrincipalName));

		 
		
		 model.addAttribute("categEmpl", annonceRepository.findByCategorieEmploi(categorie));
		 return "articles/categorie";
	 }
	 
	 
}

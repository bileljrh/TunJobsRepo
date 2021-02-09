package com.bilel.TunisiaGate.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bilel.TunisiaGate.domains.Annonces;
import com.bilel.TunisiaGate.domains.CategorieEmploi;
import com.bilel.TunisiaGate.domains.TypeEmploi;
import com.bilel.TunisiaGate.domains.VilleEmploi;

public interface AnnonceRepository extends JpaRepository<Annonces, Long> {

	  List<Annonces> findByUserId(Long userId);  
	  List<Annonces> findByTypeEmploi(TypeEmploi type);  
	  List<Annonces> findByVilleEmploi(VilleEmploi ville); 
    //  Annonces findByUserIdAndAnnonceId(Long user_id,Long id);
	  List<Annonces> findByCategorieEmploi(CategorieEmploi categorie); 
	Object findByUserIdAndId(Long userId, Long annonceId);

	List<Annonces> findAll(Specification<Annonces> spec);	

	
	List<Annonces> findAllByUserId(Long userId,PageRequest pageRequest);
	

	
}

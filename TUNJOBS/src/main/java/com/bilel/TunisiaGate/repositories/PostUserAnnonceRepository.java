package com.bilel.TunisiaGate.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.bilel.TunisiaGate.domains.Annonces;
import com.bilel.TunisiaGate.domains.PostUserAnnonce;

public interface PostUserAnnonceRepository extends JpaRepository<PostUserAnnonce, Long>{
	
	List<PostUserAnnonce> findByAnnoncesId(Long annonceId);
	List<PostUserAnnonce> findByUserId(Long userId);


}

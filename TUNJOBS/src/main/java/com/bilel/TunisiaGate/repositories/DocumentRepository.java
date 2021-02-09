package com.bilel.TunisiaGate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bilel.TunisiaGate.domains.Document;

@Repository("documentRepository")
public interface DocumentRepository extends JpaRepository<Document, Long>{

	
	
	
}

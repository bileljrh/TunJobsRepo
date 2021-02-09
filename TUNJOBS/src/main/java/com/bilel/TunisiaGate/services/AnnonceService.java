package com.bilel.TunisiaGate.services;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bilel.TunisiaGate.domains.Annonces;
import com.bilel.TunisiaGate.repositories.AnnonceRepository;

@Service
public class AnnonceService {

@Autowired
private AnnonceRepository annonceRepository;


public List<Annonces> getAll(String text) {
	List<String> attributes = Arrays.asList("titre","description","price","nombreCandidat"); 
    if (!text.contains("%")) {
        text = "%" + text + "%";
    }
    String finalText = text;
    Specification<Annonces> spec = (root, query, builder) -> 
    		builder.or(root.getModel().getDeclaredSingularAttributes().stream()
    				.filter(a -> attributes.contains(a.getName()))
    				.map(a -> {
    					if(a.getJavaType().getSimpleName().equalsIgnoreCase("int")) {
    						return builder.like(root.get(a.getName()).as(String.class), finalText);
    					} else if(a.getJavaType().getSimpleName().equalsIgnoreCase("int")) {
    						return builder.like(root.get(a.getName()).as(String.class), finalText);
    					}
    					if(a.getJavaType().getSimpleName().equalsIgnoreCase("Double")) {
    						return builder.like(root.get(a.getName()).as(String.class), finalText);
    					} else if(a.getJavaType().getSimpleName().equalsIgnoreCase("Double")) {
    						return builder.like(root.get(a.getName()).as(String.class), finalText);
    					}
    					
    					
    					return builder.like(root.get(a.getName()), finalText);
    				})
    				.toArray(Predicate[]::new)
    );       
    return annonceRepository.findAll(Specification.where(spec));
}


}

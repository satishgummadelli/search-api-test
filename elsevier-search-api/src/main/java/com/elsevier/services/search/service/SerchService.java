package com.elsevier.services.search.service;

import java.util.Set;

import com.elsevier.services.search.model.SearchResponse;

public interface SerchService {
	
	public SearchResponse searchFile(Set<String> words);

}

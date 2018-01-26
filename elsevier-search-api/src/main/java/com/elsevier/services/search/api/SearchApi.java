package com.elsevier.services.search.api;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elsevier.services.search.model.SearchResponse;
import com.elsevier.services.search.service.SerchService;

/**
 * Search REST API class
 * @author satishgummadelli
 *
 */
@RequestMapping("/elsevier/api/test")
@RestController
public class SearchApi {
	
	@Autowired
	private SerchService service;
	
	@RequestMapping(value="/search",method = RequestMethod.GET)
	public SearchResponse searchWords(@RequestParam(value="words", required = true) String words){
		return service.searchFile(new HashSet<>(Arrays.asList(words.split(","))));
	}

}

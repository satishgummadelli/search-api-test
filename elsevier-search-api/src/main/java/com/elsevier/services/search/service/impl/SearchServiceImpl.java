package com.elsevier.services.search.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.elsevier.services.search.model.SearchResponse;
import com.elsevier.services.search.service.SerchService;

/**
 * Search API Service class, it initiates the search in the given path and returns the file paths in result
 * @author satishgummadelli
 *
 */
@Component
public class SearchServiceImpl implements SerchService {
	
	@Value("${search-file-path}")
	private String filePath;
	
	@Autowired
    private ResourceLoader resourceLoader;
	
	@Value("${search-file-extensions}")
	private String validFileExtensions;

	@Override
	public SearchResponse searchFile(Set<String> words) {
		
		FileSearchPattern searchPattern = new FileSearchPattern(words, new HashSet<String>(Arrays.asList(validFileExtensions.split(FileSearchPattern.COMMA))));
		try {
			if(filePath.startsWith("classpath:")){
				Resource res = resourceLoader.getResource(filePath);
				Files.walkFileTree(Paths.get(res.getURI()), searchPattern);
			}else {
				Files.walkFileTree(Paths.get(filePath), searchPattern);
			}
		} catch (IOException e) {
			throw new RuntimeException("failed to search words in the file path");
		}
		return new SearchResponse(searchPattern.getFilePaths());
	}
}

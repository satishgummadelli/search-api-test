package com.elsevier.services.search.model;

import java.util.Set;

public class SearchResponse {
	
	private Set<String> filePaths;

	public SearchResponse(Set<String> filePaths) {
		super();
		this.filePaths = filePaths;
	}

	public Set<String> getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(Set<String> filePaths) {
		this.filePaths = filePaths;
	}

}

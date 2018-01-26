package com.elsevier.services.search.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceImplTest {
	
	@Autowired
	private SearchServiceImpl searchService;
	
	@Test
	public void shoudlReturnCorrectFilePath() {
		assertThat("stemming should present only in one file", searchService.searchFile(new HashSet<String>(Arrays.asList("stemming"))).getFilePaths().size(),is(1));
		assertThat("stemming and satish should present in a file should return empty response ",searchService.searchFile(new HashSet<String>(Arrays.asList("stemming","satish"))).getFilePaths().size(),is(0));
		assertThat("The should return 2 responses",searchService.searchFile(new HashSet<String>(Arrays.asList("The"))).getFilePaths().size(),is(2));
		assertThat("THE should return 2 responses",searchService.searchFile(new HashSet<String>(Arrays.asList("THE"))).getFilePaths().size(),is(2));
		assertThat("England should return zero responses",searchService.searchFile(new HashSet<String>(Arrays.asList("England"))).getFilePaths().size(),is(0));
	}
	
}

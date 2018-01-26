package com.elsevier.services.search.service.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anySet;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.elsevier.services.search.api.SearchApi;
import com.elsevier.services.search.model.SearchResponse;
import com.elsevier.services.search.service.impl.SearchServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchApi.class)
public class SearchApiTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SearchServiceImpl searchService;

	private JacksonTester<SearchResponse> jsonSearchResponse;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSearchWords() throws Exception {
		Set<String> words = new HashSet<String>(Arrays.asList("one", "two"));
		SearchResponse sResponse = new SearchResponse(words);
		given(searchService.searchFile(anySet())).willReturn(sResponse);
		MockHttpServletResponse response = mvc
				.perform(get("/elsevier/api/test/search?words=stemming").accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
		assertThat(response.getContentAsString()).isEqualTo(jsonSearchResponse.write(sResponse).getJson());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSearchMultipleWords() throws Exception {
		Set<String> words = new HashSet<String>(Arrays.asList("one", "two"));
		SearchResponse sResponse = new SearchResponse(words);
		given(searchService.searchFile(anySet())).willReturn(sResponse);
		MockHttpServletResponse response = mvc
				.perform(get("/elsevier/api/test/search?words=stemming,test").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
		System.out.println("*******");
		System.out.println(response.getContentAsString());
		System.out.println("*******");
		assertThat(response.getContentAsString()).isEqualTo(jsonSearchResponse.write(sResponse).getJson());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldThrowBadRequestForEmptySearchWords() throws Exception {
		Set<String> words = new HashSet<String>(Arrays.asList("one", "two"));
		SearchResponse sResponse = new SearchResponse(words);
		given(searchService.searchFile(anySet())).willReturn(sResponse);
		MockHttpServletResponse response = mvc
				.perform(get("/elsevier/api/test/search").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
	}
}
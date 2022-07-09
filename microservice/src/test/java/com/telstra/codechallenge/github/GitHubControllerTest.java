package com.telstra.codechallenge.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telstra.codechallenge.github.controller.GitHubController;
import com.telstra.codechallenge.github.model.HottestRepositories;
import com.telstra.codechallenge.github.model.HottestRepository;
import com.telstra.codechallenge.github.service.GitHubService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(GitHubController.class)
public class GitHubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitHubService gitHubService;

    @Test
    public void getHottestRepositories_test_200_OK() throws Exception {

        HottestRepository repo = new HottestRepository();
        repo.setHtml_url("testurl");
        repo.setWatchers_count(Long.valueOf(1000));
        repo.setLanguage("Java");
        repo.setDescription("Git");
        repo.setName("Test");

        ObjectMapper mapper = new ObjectMapper();
        List items = new ArrayList<HottestRepository>();
        items.add(repo);
        HottestRepositories repos = new HottestRepositories();
        repos.setItems(items);
        when(gitHubService.getHottestRepos(1)).thenReturn(repos);
        RequestBuilder request = MockMvcRequestBuilders.get("/hottest-repos?repos_limit=1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals("{\"items\":[{\"html_url\":\"testurl\",\"watchers_count\":1000,\"language\":\"Java\",\"description\":\"Git\",\"name\":\"Test\"}]}",
                result.getResponse().getContentAsString());
    }

    @Test
    public void getHottestRepositories_test_400_BAD_REQ_when_string_is_paases_as_param() throws Exception {

        RequestBuilder request =
                MockMvcRequestBuilders.get("/hottest-repos?repos_limit=\"ONE\"").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        Assertions.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void getHottestRepositories_test_400_BAD_REQ_when_req_param_is_less_than_given_property_value() throws Exception {

        RequestBuilder request =
                MockMvcRequestBuilders.get("/hottest-repos?repos_limit=0").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        Assertions.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void getHottestRepositories_test_400_BAD_REQ_when_req_param_is_greater_than_given_property_value() throws Exception {

        RequestBuilder request =
                MockMvcRequestBuilders.get("/hottest-repos?repos_limit=1999").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        Assertions.assertEquals(result.getResponse().getStatus(), 400);
    }
}

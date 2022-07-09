package com.telstra.codechallenge.github;


import com.telstra.codechallenge.github.model.HottestRepositories;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GithubApiRestTemplateUnitTest {


    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testGitApiWithRestTemplate() {

        String url = "https://api.github.com" + "/search/repositories?q=created:>{strSevenDaysEarlierDate}&sort=stars" +
                "&per_page" +
                "={repos_limit}&order=desc";
        Map<String, String> uriVariables = new HashMap<>();
        LocalDate today = LocalDate.now();
        String strSevenDaysEarlierDate = today.minusDays(7).toString();
        uriVariables.put("repos_limit", "1");
        uriVariables.put("strSevenDaysEarlierDate", strSevenDaysEarlierDate);

        HottestRepositories hottestRepositoriesResponse = restTemplate.getForObject(url, HottestRepositories.class,
                uriVariables);

        Assertions.assertEquals(1, hottestRepositoriesResponse.getItems().size());
    }
}

package com.telstra.codechallenge.github.service;

import com.telstra.codechallenge.github.model.HottestRepositories;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class GitHubService {

    @Value("${github.base.url}")
    private String githubBaseUrl;

    public HottestRepositories getHottestRepos(Integer repos_limit) {
        RestTemplate restTemplate = new RestTemplate();

        String url = githubBaseUrl+"/search/repositories?q=created:>{strSevenDaysEarlierDate}&sort=stars" +
                "&per_page" +
                "={repos_limit}&order=desc";
        Map<String, String> uriVariables = new HashMap<>();
        LocalDate today = LocalDate.now();
        String strSevenDaysEarlierDate = today.minusDays(7).toString();
        uriVariables.put("repos_limit", repos_limit.toString());
        uriVariables.put("strSevenDaysEarlierDate", strSevenDaysEarlierDate);

        HottestRepositories hottestRepositoriesResponse = restTemplate.getForObject(url, HottestRepositories.class,
                uriVariables);



        return hottestRepositoriesResponse;
    }

}






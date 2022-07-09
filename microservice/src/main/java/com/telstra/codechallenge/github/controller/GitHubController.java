package com.telstra.codechallenge.github.controller;

import com.telstra.codechallenge.github.constants.Constants;
import com.telstra.codechallenge.github.service.GitHubService;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GitHubController {

    @Value("${repos.limit.min}")
    private Integer repo_limit_min;

    @Value("${repos.limit.max}")
    private Integer repo_limit_max;

    @Autowired
    private GitHubService gitHubService;

    @GetMapping("/hottest-repos")
    @Retry(name ="default")
    public ResponseEntity getHottestRepositories(@RequestParam Integer repos_limit)
    {

        if(repos_limit < repo_limit_min || repos_limit > repo_limit_max)
            return  new ResponseEntity(Constants.REQ_PARAM_VALIDATION_MESSAGE+" "+repo_limit_min+" and "+ repo_limit_max,
                    HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(gitHubService.getHottestRepos(repos_limit), HttpStatus.OK);
    }


}

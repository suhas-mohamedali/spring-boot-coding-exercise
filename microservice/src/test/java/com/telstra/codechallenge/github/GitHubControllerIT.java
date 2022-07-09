package com.telstra.codechallenge.github;


import com.telstra.codechallenge.github.model.HottestRepositories;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubControllerIT {

    @Autowired
    private TestRestTemplate restTemplate1;

    @Test
    public void contextLoads(){
        HottestRepositories resp = this.restTemplate1.getForObject("/hottest-repos?repos_limit=2",
                HottestRepositories.class);
        Assertions.assertEquals(2, resp.getItems().size());
    }


}

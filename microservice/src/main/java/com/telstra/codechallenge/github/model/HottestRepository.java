package com.telstra.codechallenge.github.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class HottestRepository {


    private String html_url;
    private Long watchers_count;
    private String language;
    private String description;
    private String name;


}

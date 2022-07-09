package com.telstra.codechallenge.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class HottestRepositories {
    private List<HottestRepository> items;

}

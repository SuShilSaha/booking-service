package com.paypal.bfs.test.bookingserv.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "pageSize",
    "page",
    "results"
})
public class ApiPaginatedResponse {

    /**
     * Page size
     * (Required)
     * 
     */
    @JsonProperty("pageSize")
    @JsonPropertyDescription("Page size")
    private Integer pageSize;
    /**
     * Page
     * (Required)
     * 
     */
    @JsonProperty("page")
    @JsonPropertyDescription("Page")
    private Integer page;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("results")
    private List<?> results = new ArrayList<>();

    /**
     * Page size
     * (Required)
     * 
     */
    @JsonProperty("pageSize")
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Page size
     * (Required)
     * 
     */
    @JsonProperty("pageSize")
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Page
     * (Required)
     * 
     */
    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    /**
     * Page
     * (Required)
     * 
     */
    @JsonProperty("page")
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("results")
    public List<?> getResults() {
        return results;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("results")
    public void setResults(List<?> results) {
        this.results = results;
    }

}

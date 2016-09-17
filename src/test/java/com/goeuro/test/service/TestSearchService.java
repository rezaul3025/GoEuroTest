/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goeuro.test.service;



import com.goeuro.test.domain.Location;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rkarim
 */
public class TestSearchService {
    
    private final static Integer REQUEST_SUCCESS_CODE = 200;
    
    private final static Integer REQUEST_NOT_FOUND_CODE = 400;
    
    private final static String SEARCH_URL = "http://api.goeuro.com/api/v2/position/suggest/en";
    
    SearchService searchService = new SearchServiceHandler();

    @Test(expected = NullPointerException.class)
    public void testUrlForNull(){
        searchService.setSearchUrl(null);
    }
    
    @Test(expected = NullPointerException.class)
    public void testSearchWithoutUul(){
        searchService.search("berlin");
    }
    
    @Test
    public void testSearchWrongUrl(){
        searchService.setSearchUrl("http://api.goeuro.com/apiwrong/v2/position/suggest/en");
        searchService.search("berlin");
        Assert.assertEquals(REQUEST_NOT_FOUND_CODE, searchService.getSearchStatus());
    }
    
    @Test
    public void testSearchSuccess(){
        searchService.setSearchUrl(SEARCH_URL);
        searchService.search("berlin");
        Assert.assertEquals(REQUEST_SUCCESS_CODE, searchService.getSearchStatus());
    }
    
    @Test
    public void testSearchResult(){
        searchService.setSearchUrl(SEARCH_URL);
        searchService.search("comilla");
        List<Location> results= searchService.getResult();
        results.stream().forEach((location) -> {
            String name = location.getName().toLowerCase();
            Assert.assertTrue(name.contains("c"));
        });
    }
    
    @Test
    public void testSearchResultEmpty(){
        searchService.setSearchUrl(SEARCH_URL);
        searchService.search("testempty");
        List<Location> results= searchService.getResult();
        Assert.assertTrue(results.isEmpty());
    }
    
}

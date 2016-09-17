/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goeuro.test;

import com.goeuro.test.domain.Location;
import com.goeuro.test.service.SearchService;
import com.goeuro.test.service.SearchServiceHandler;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rkarim
 */
public class GoEuroTest {

    private final static Logger LOG = Logger.getLogger(SearchServiceHandler.class.getName());

    private final static int REQUEST_SUCCESS_CODE = 200;
    
    private final static String SEARCH_URL = "http://api.goeuro.com/api/v2/position/suggest/en";


    public static void main(String[] arg) {
        SearchService searchService = new SearchServiceHandler();

        if (arg.length > 0) {
            String query = arg[0];
            searchService.setSearchUrl(SEARCH_URL);
            searchService.search(query);
            if (searchService.getSearchStatus() == REQUEST_SUCCESS_CODE) {
                List<Location> locations = searchService.getResult();
                searchService.exportAsCSV(locations, query);
            }
        } else {
            LOG.log(Level.INFO, "Search query missing!");
        }
    }

}

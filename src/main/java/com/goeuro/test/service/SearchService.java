/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goeuro.test.service;

import com.goeuro.test.domain.Location;
import java.util.List;

/**
 *
 * @author rkarim
 */
public interface SearchService {
    
    void setSearchUrl(String url);
    void search(String query);
    List<Location> getResult();
    Integer getSearchStatus();
    void exportAsCSV(List<Location> result, String exportUrl);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goeuro.test.service;

import com.goeuro.test.domain.Location;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import static org.apache.commons.lang3.Validate.notNull;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author rkarim
 */
public class SearchServiceHandler implements SearchService {

    private final static Logger LOG = Logger.getLogger(SearchServiceHandler.class.getName());

    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String SEMICOLON_DELIMITER = ";";

    private static final String EXPORT_CSV_HEADER = "id;name;type;latitude;longitude";

    private static final String DEFAULT_EXPORT_PATH = "default.csv";

    private List<Location> searchResult = new ArrayList<>();
    
    private int responseStatusCode;
    
    private String searchUrl;
    
    @Override
    public void setSearchUrl(String url) {
        notNull(url);
        searchUrl = url;
    }

    @Override
    public void search(String query) {

        HttpClient client = HttpClientBuilder.create().build();

        if (StringUtils.isNotBlank(query)) {

            //Validate url
            notNull(searchUrl);
            
            HttpGet request = new HttpGet(searchUrl + "/" + query);

            try {

                HttpResponse response = client.execute(request);

                responseStatusCode = response.getStatusLine().getStatusCode();

                if (responseStatusCode == HttpStatus.SC_OK) {

                    HttpEntity getResponseEntity = response.getEntity();

                    Gson gson = new Gson();
                    Reader reader = new InputStreamReader(getResponseEntity.getContent());
                    Type listType = new TypeToken<List<Location>>() {}.getType();
                    List<Location> result = gson.fromJson(reader, listType);

                    searchResult = result;
                }

            } catch (IOException ex) {
                request.abort();
                LOG.log(Level.SEVERE, "Error in search", ex);
            }
        }

    }

    @Override
    public List<Location> getResult() {
        return searchResult;
    }

    @Override
    public Integer getSearchStatus() {
        return responseStatusCode;
    }
    
    @Override
    public void exportAsCSV(List<Location> locations, String exportUrl) {

        if (locations != null && !locations.isEmpty()) {

            try {
                exportUrl = StringUtils.isNotBlank(exportUrl) ? exportUrl + ".csv" : DEFAULT_EXPORT_PATH;

                File file = new File(exportUrl);
                file.createNewFile();
                OutputStream outputStream = new FileOutputStream(file);
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, "ISO-8859-1"), true);

                printWriter.print(EXPORT_CSV_HEADER);
                printWriter.print(NEW_LINE_SEPARATOR);

                locations.stream().forEach((city) -> {
                    printWriter.print(city.getId());
                    printWriter.print(SEMICOLON_DELIMITER);
                    printWriter.print(city.getName());
                    printWriter.print(SEMICOLON_DELIMITER);
                    printWriter.print(city.getType());
                    printWriter.print(SEMICOLON_DELIMITER);
                    printWriter.print(city.getGeoPosition() != null && city.getGeoPosition().getLatitude() != null ? city.getGeoPosition().getLatitude() : "");
                    printWriter.print(SEMICOLON_DELIMITER);
                    printWriter.print(city.getGeoPosition() != null && city.getGeoPosition().getLongitude() != null ? city.getGeoPosition().getLongitude() : "");
                    printWriter.print(NEW_LINE_SEPARATOR);
                });

                printWriter.flush();
                printWriter.close();

                LOG.log(Level.INFO, "Result saved into {0} under your present working directory", exportUrl);

            } catch (IOException ex) {
                LOG.log(Level.SEVERE, "Error in CSV export : {0}", ex);

            }
        } else {
            LOG.log(Level.INFO, "Nothing to export, No result found!!");
        }
    }
}

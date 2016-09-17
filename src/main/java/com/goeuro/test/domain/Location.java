/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goeuro.test.domain;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author rkarim
 */
public class Location {

    @SerializedName("_id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("geo_position")
    private GeoPosition geoPosition;

    public Location() {

    }

    public Location(Long id, String name, String type, GeoPosition geoPosition) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.geoPosition = geoPosition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }
}

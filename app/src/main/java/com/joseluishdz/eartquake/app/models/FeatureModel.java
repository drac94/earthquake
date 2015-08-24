package com.joseluishdz.eartquake.app.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by drac94 on 8/23/15.
 */
public class FeatureModel {
    @SerializedName("type")
    private String type;

    @SerializedName("id")
    private String id;

    @SerializedName("properties")
    private PropertiesModel properties;

    @SerializedName("geometry")
    private GeometryModel geometry;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PropertiesModel getProperties() {
        return properties;
    }

    public void setProperties(PropertiesModel properties) {
        this.properties = properties;
    }

    public GeometryModel getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryModel geometry) {
        this.geometry = geometry;
    }
}

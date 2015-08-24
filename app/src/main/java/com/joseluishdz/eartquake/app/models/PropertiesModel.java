package com.joseluishdz.eartquake.app.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by drac94 on 8/23/15.
 */
public class PropertiesModel {

    @SerializedName("mag")
    private double magnitude;

    @SerializedName("place")
    private String place;

    @SerializedName("title")
    private String title;

    @SerializedName("time")
    private long time;

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

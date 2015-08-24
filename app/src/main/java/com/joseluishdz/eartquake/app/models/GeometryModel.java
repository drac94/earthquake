package com.joseluishdz.eartquake.app.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by drac94 on 8/23/15.
 */
public class GeometryModel {

    @SerializedName("coordinates")
    private double[] coordinates;

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}

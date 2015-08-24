package com.joseluishdz.eartquake.app.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by drac94 on 8/23/15.
 */
public class MetadataModel {

    @SerializedName("title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

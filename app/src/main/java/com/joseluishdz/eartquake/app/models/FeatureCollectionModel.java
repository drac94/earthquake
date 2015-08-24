package com.joseluishdz.eartquake.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by drac94 on 8/23/15.
 */
public class FeatureCollectionModel {
    @SerializedName("metadata")
    private MetadataModel metadata;

    @SerializedName("features")
    private ArrayList<FeatureModel> features;

    public MetadataModel getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataModel metadata) {
        this.metadata = metadata;
    }

    public ArrayList<FeatureModel> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<FeatureModel> features) {
        this.features = features;
    }
}

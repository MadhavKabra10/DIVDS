package com.btp.ccproject.Model;

public class SoftwarePackage {
    private String index;
    private String layerID;
    private String name;
    private String version;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getLayerID() {
        return layerID;
    }

    public void setLayerID(String layerID) {
        this.layerID = layerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SoftwarePackage(String index, String layerID, String name, String version) {
        this.index = index;
        this.layerID = layerID;
        this.name = name;
        this.version = version;
    }
}

package com.btp.ccproject.Model;

public class DetectionResult {
    private String softwareIndex;
    private String vulnerabilityIndex;

    public DetectionResult(String softwareIndex, String vulnerabilityIndex) {
        this.softwareIndex = softwareIndex;
        this.vulnerabilityIndex = vulnerabilityIndex;
    }

    public String getSoftwareIndex() {
        return softwareIndex;
    }

    public void setSoftwareIndex(String softwareIndex) {
        this.softwareIndex = softwareIndex;
    }

    public String getVulnerabilityIndex() {
        return vulnerabilityIndex;
    }

    public void setVulnerabilityIndex(String vulnerabilityIndex) {
        this.vulnerabilityIndex = vulnerabilityIndex;
    }
}
package com.btp.ccproject.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@RedisHash("Image")
public class Image implements Serializable {
    @Id
    private String name;
    private double vulnerabilityScore;
    @TimeToLive
    private long expiration;


    public Image(String name, double vulnerabilityScore) {
        this.name = name;
        this.vulnerabilityScore = vulnerabilityScore;
        this.expiration = 86400L;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVulnerabilityScore() {
        return vulnerabilityScore;
    }

    public void setVulnerabilityScore(int vulnerabilityScore) {
        this.vulnerabilityScore = vulnerabilityScore;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}

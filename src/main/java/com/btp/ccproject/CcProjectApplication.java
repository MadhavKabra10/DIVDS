package com.btp.ccproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class CcProjectApplication implements CommandLineRunner {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String CLAIR_URL = "http://localhost:6060";
    @Autowired
    DockerLayerExtractor dockerLayerExtractor;

    public static void main(String[] args) {
        SpringApplication.run(CcProjectApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java -jar clair-cli.jar <docker-image-name>");
            System.exit(1);
        }

        String imageName = args[0];
        System.out.println("Scanning Docker image: " + imageName);

        List<String> layers = Objects.requireNonNull(dockerLayerExtractor.inspectImage(imageName).getRootFS()).getLayers();
        assert layers != null;
        for(String it : layers) {
            submitLayerToClair(it);
        }
        Thread.sleep(10000);
        List<JsonNode> reportList = new ArrayList<>();
        for (String it : layers) {
            JsonNode report = getVulnerabilityReport(it);
            reportList.add(report);
        }
        double vulnerabilityScore = 0;
        for(JsonNode it : reportList) {
            vulnerabilityScore += calculateVulnerabilityScore(it);
        }
        System.out.println("Vulnerability Score for image " + imageName + ": " + vulnerabilityScore);

        double threshold = 200;
        if (vulnerabilityScore > threshold) {
            System.out.println("Image is vulnerable and should be blocked.");
        } else {
            System.out.println("Image passed the vulnerability check.");
        }
    }

    private void submitLayerToClair(String layerName) {
        String url = CLAIR_URL + "/v1/layers";
        String payload = "{ \"Layer\": { \"Name\": \"" + layerName + "\", "
                + "\"Path\": \"/path/to/" + layerName + ".tar\", "
                + "\"Format\": \"Docker\" } }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        System.out.println("Submitted layer " + layerName + " to Clair. Response: " + response.getStatusCode());
    }

    private JsonNode getVulnerabilityReport(String layerName) throws Exception {
        String url = CLAIR_URL + "/v1/layers/" + layerName + "?vulnerabilities";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println("Retrieved vulnerability report for layer " + layerName);
        return objectMapper.readTree(response.getBody());
    }

    private double calculateVulnerabilityScore(JsonNode report) {
        double score = 0;
        if (report.has("Layer") && report.get("Layer").has("Features")) {
            for (JsonNode feature : report.get("Layer").get("Features")) {
                if (feature.has("Vulnerabilities")) {
                    for (JsonNode vuln : feature.get("Vulnerabilities")) {
                        String severity = vuln.get("Severity").asText();
                        double weight = getWeightForSeverity(severity);
                        score += weight;
                    }
                }
            }
        }
        return score;
    }

    private double getWeightForSeverity(String severity) {
        switch (severity) {
            case "Negligible": return 0.5;
            case "Low": return 2;
            case "Medium": return 5.45;
            case "High": return 7.95;
            case "Critical": return 9.5;
            default: return 0;
        }
    }
}


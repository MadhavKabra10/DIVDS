package com.btp.ccproject.config;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConfigFile {
    private String imageName;

    public void readInputFromConsole() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Docker image name: ");
        this.imageName = sc.next();
    }

    public String[] getCommand() {
        return new String[] {
                "docker", "run", "--rm",
                "-v", "/var/run/docker.sock:/var/run/docker.sock",
                "aquasec/trivy", "--quiet", "-f", "json", "image", imageName
        };
    }
    public String getImageName(){
        return imageName;
    }
}

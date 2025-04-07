package com.btp.ccproject;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DockerLayerExtractor {
    @Autowired
    DockerClient dockerClient;

    public InspectImageResponse inspectImage(String imageId){
        return dockerClient.inspectImageCmd(String.valueOf(imageId)).exec();
    }
}

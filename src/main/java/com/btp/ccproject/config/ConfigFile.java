//package com.btp.ccproject.config;
//
//import com.github.dockerjava.api.DockerClient;
//import com.github.dockerjava.core.DockerClientBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ConfigFile {
//    @Bean
//    DockerClient dockerClientDefault(){
//        return DockerClientBuilder.getInstance().build();
//    }
//
////    @Bean
////    DockerClient dockerClientCustom(){
////        return DockerClientBuilder.getInstance(
////                        DefaultDockerClientConfig.createDefaultConfigBuilder()
////                                .withDockerHost("tcp://localhost:2375")
////                                .build()
////                ).build();
////    }
//}

package com.theowl.springcloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;


@RestController
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() throws Exception {

        client.getServices().forEach(id -> {
            client.getInstances(id).forEach(instance -> {
                // wait a moment
                int sleepTime = new Random().nextInt(3000);
                logger.info("sleepTime: " + sleepTime);

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                logger.info("/hello, host:" +instance.getHost() + ", service_id: " +instance.getServiceId());
            });
        });

        return "Hello World, Hello SpringBoot, wangran !";
    }
}

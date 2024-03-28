package br.com.alura.challenge.api.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StatusController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Alura Challenge!";
    }

    @GetMapping("/status")
    public Map<String, Object> getAppStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("ok", true);
        status.put("uptime", ManagementFactory.getRuntimeMXBean().getUptime());
        return status;
    }
}

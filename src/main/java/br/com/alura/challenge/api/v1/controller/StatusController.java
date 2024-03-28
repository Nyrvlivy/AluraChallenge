package br.com.alura.challenge.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Status", description = "Operations related to application status.")
public class StatusController {

    @Operation(summary = "Welcome", description = "Displays a welcome message.")
    @GetMapping("/")
    public String welcome() {
        return "Welcome to Alura Challenge!";
    }

    @Operation(summary = "Application Status", description = "Provides a quick health check of the application.")
    @GetMapping("/status")
    public Map<String, Object> getAppStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("ok", true);
        status.put("uptime", ManagementFactory.getRuntimeMXBean().getUptime());
        return status;
    }
}

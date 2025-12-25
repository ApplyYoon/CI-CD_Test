package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.io.File;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow Electron to access
public class ApiController {

    @GetMapping("/hello")
    public String hello() {
        return "{\"message\": \"Hello from Spring Boot!\"}";
    }

    @GetMapping("/python")
    public String callPython() {
        try {
            // Adjust path based on where the jar/mvn runs.
            // Assuming running from 'springboot-backend' dir, python script is in
            // '../python-scripts'
            File scriptFile = new File("../python-scripts/hello.py");
            String scriptPath = scriptFile.getCanonicalPath();

            ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.lines().collect(Collectors.joining("\n"));

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return "{\"error\": \"Python script failed\", \"details\": \"" + output.replace("\"", "'") + "\"}";
            }

            return output; // Python script already returns JSON
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }
}

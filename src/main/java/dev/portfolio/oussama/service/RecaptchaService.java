package dev.portfolio.oussama.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${recaptcha.url}")
    private String recaptchaUrl;

    private final RestTemplate restTemplate;

    public RecaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean verifyRecaptcha(String token) {
        String requestUrl = String.format("%s?secret=%s&response=%s", recaptchaUrl, recaptchaSecret, token);
        System.out.println("token captcha r"+token);
        // Call Google's API
        Map<String, Object> response = restTemplate.postForObject(requestUrl, null, Map.class);
        System.out.println("response captcha r"+response);
        // Check the response
        if (response == null || !(boolean) response.get("success")) {
            return false;
        }

        // Optional: Handle score-based logic (v3-specific)
        Double score = (Double) response.get("score");
        return score != null && score >= 0.5; // Adjust threshold as needed
    }
}

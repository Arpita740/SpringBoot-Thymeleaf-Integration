package com.spring.thymeleaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.spring.busticketbooking.dto.ResponseStructure;
import com.spring.busticketbooking.entity.Review;

@Controller
public class ReviewsThymeleafController {

    private final String API_URL = "http://localhost:7080/api/reviews/all"; // API URL to fetch reviews
    private final RestTemplate restTemplate;

    @Autowired
    public ReviewsThymeleafController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch all reviews and render them using Thymeleaf
    @GetMapping("/reviews")
    public String getAllReviews(Model model) {
        try {
            // Make GET request to the API
            ResponseEntity<ResponseStructure> response = restTemplate.exchange(API_URL, org.springframework.http.HttpMethod.GET, null, ResponseStructure.class);
            ResponseStructure responseStructure = response.getBody();

            // Check if the response is not null and contains review data
            if (responseStructure != null && responseStructure.getData() != null) {
                List<Review> reviews = (List<Review>) responseStructure.getData();
                model.addAttribute("reviews", reviews); // Add reviews data to the model
                return "reviews"; // Thymeleaf view to display reviews
            } else {
                model.addAttribute("error", "No reviews found.");
                return "error"; // Thymeleaf error view
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching review data: " + e.getMessage());
            return "error"; // Return error view in case of an exception
        }
    }
}

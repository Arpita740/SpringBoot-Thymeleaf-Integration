package com.spring.thymeleaf.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.busticketbooking.entity.Discount;
import com.spring.busticketbooking.service.DiscountServices;
import com.spring.busticketbooking.service.TripServices;

@Controller
public class DiscountThymeleafController {

    private final DiscountServices discountServices;
    private final TripServices tripServices;

    @Autowired
    public DiscountThymeleafController(DiscountServices discountServices, TripServices tripServices) {
        this.discountServices = discountServices;
        this.tripServices = tripServices;
    }

    // Endpoint to display the form for creating a new discount
    @GetMapping("/discount/add")
    public String showDiscountForm(Model model) {
        model.addAttribute("discount", new Discount());
        return "add-discount"; // Thymeleaf template to render the form
    }

    // POST endpoint to save the discount to the database
    @PostMapping("/discount/add")
    public String addDiscountToTrip(
            @RequestParam int tripId, // Trip ID is provided via the form
            @RequestParam String discountCode,
            @RequestParam int discountPercentage,
            @RequestParam String validUntil) {

        // Create the Discount object from the form data
        Discount discount = new Discount();
        discount.setDiscountCode(discountCode);
        discount.setDiscountPercentage(discountPercentage);
        discount.setValidUntil(LocalDate.parse(validUntil));

        // Use DiscountServices to associate the discount with the trip and save it
        discountServices.createDiscount(tripId, discount);

        // No message or redirect, just return the same form
        return "add-discount"; // The form remains on the same page
    }
}

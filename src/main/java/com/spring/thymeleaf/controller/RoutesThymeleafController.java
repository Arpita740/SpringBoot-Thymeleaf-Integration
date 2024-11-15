package com.spring.thymeleaf.controller;

import com.spring.busticketbooking.entity.Route;
import com.spring.busticketbooking.service.RoutesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/routes")
public class RoutesThymeleafController {

    private final RoutesServices routesServices;

    @Autowired
    public RoutesThymeleafController(RoutesServices routesServices) {
        this.routesServices = routesServices;
    }

    // Display the route details in the update form
    @GetMapping("/update/{routeId}")
    public String showUpdateForm(@PathVariable("routeId") int routeId, Model model) {
        Route route = routesServices.getRouteById(routeId);
        if (route != null) {
            model.addAttribute("route", route);
            return "update_route";  // Return the Thymeleaf template to update the route
        } else {
            model.addAttribute("error", "Route not found");
            return "error";  // If route is not found, show an error page
        }
    }

    // Handle the form submission to update the route
    @PostMapping("/update/{routeId}")
    
    public String updateRoute(@PathVariable("routeId") int routeId, @ModelAttribute Route routeDetails, Model model) {
        try {
            String updateMessage = routesServices.updateRouteDetails(routeId, routeDetails);
            model.addAttribute("message", updateMessage);
            return "redirect:/routes/update/" + routeId;  // Redirect to updated route page
        } catch (Exception e) {
            model.addAttribute("error", "Error updating route: " + e.getMessage());
            return "error";  // Show an error page in case of failure
        }
    }
}

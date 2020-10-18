package ru.skubatko.dev.ees.ui.controller;

import ru.skubatko.dev.ees.ui.service.EesRatingService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class RatingController {

    private final EesRatingService service;

    @GetMapping("/ees/rating")
    public String listRating(Model model, Principal principal) {
        model.addAttribute("ratings", service.findByUserDesc(principal.getName()));
        return "rating/list-rating";
    }
}

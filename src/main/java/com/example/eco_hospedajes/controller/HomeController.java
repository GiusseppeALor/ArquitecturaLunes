package com.example.eco_hospedajes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // Busca templates/index.html
    }

    //@GetMapping("/login")
    //public String mostrarLogin() {
    //    return "redirect:/login.html";
    //}
}


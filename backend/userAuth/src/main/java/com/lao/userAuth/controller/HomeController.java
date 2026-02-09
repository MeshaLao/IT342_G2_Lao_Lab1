// Create a new file: src/main/java/com/lao/userAuth/controller/HomeController.java

package com.lao.userAuth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // This looks for home.html in templates folder
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // After login, redirect here
    }
}
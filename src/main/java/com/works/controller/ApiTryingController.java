package com.works.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiTryingController {

    @RequestMapping("/documents/main")
    public String documentsMain(Model model) {

        String thymeleaf = "Hello Thymeleaf!";
        model.addAttribute("test", thymeleaf);

        return "main";
    }
}

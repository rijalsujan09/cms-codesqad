package com.codesqad.cms.security.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
   @GetMapping("/")
    public String goHome() {
        return "redirect:/web/home";
    }

    @GetMapping("/web/dashboard")
    public String getDash() {
        return "redirect:/web/home";
    }
}

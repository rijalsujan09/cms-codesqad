package com.codesqad.cms.security.controller;

import com.codesqad.cms.account.dto.UserDtoSave;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("contentFragment", "dashboard");
        return "index";
    }
    @GetMapping("/user")
    public String getUser(Model model) {
        model.addAttribute("contentFragment", "user");
        return "index";
    }


    @GetMapping("forgot-password")
    public String getForgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("userDto", new UserDtoSave());
        return "register";
    }

    @GetMapping("/blog")
    public String getBlogPage(Model model) {
        model.addAttribute("contentFragment", "blog");
        return "index";
    }
}

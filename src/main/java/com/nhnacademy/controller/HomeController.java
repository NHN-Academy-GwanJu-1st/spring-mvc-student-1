package com.nhnacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest, Model model) {
        HttpSession session = httpServletRequest.getSession(false);

        if (Objects.nonNull(session)) {
            model.addAttribute("id", true);
        }

        return "view/index";
    }
}

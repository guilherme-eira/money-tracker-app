package io.github.guilherme_eira.money_tracker_app.infra.web.controller;

import io.github.guilherme_eira.money_tracker_app.infra.security.SecurityUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/", "/home", "/index"})
    public String showHome(@AuthenticationPrincipal SecurityUser user){

        if (user != null) {
            return "redirect:/overview";
        }
        return "index";
    }
}

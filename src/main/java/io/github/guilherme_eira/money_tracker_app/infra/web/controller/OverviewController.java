package io.github.guilherme_eira.money_tracker_app.infra.web.controller;

import io.github.guilherme_eira.money_tracker_app.application.query.GetOverviewPageDataQuery;
import io.github.guilherme_eira.money_tracker_app.infra.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OverviewController {

    private final GetOverviewPageDataQuery getOverviewPageDataQuery;

    @GetMapping("/overview")
    public String showOverviewPage(Model model, @AuthenticationPrincipal SecurityUser user){
        var overviewData = getOverviewPageDataQuery.execute(user.getId());
        model.addAttribute("overviewData", overviewData);
        return "app/overview";
    }
}

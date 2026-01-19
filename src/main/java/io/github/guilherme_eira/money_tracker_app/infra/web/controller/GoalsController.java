package io.github.guilherme_eira.money_tracker_app.infra.web.controller;

import io.github.guilherme_eira.money_tracker_app.application.dto.goal.CreateGoalInput;
import io.github.guilherme_eira.money_tracker_app.application.dto.goal.GetGoalsPageDataInput;
import io.github.guilherme_eira.money_tracker_app.application.dto.goal.UpdateGoalInput;
import io.github.guilherme_eira.money_tracker_app.application.query.FindGoalForUpdateQuery;
import io.github.guilherme_eira.money_tracker_app.application.query.GetCategoriesQuery;
import io.github.guilherme_eira.money_tracker_app.application.query.GetGoalsPageDataQuery;
import io.github.guilherme_eira.money_tracker_app.application.usecase.CreateGoalUseCase;
import io.github.guilherme_eira.money_tracker_app.application.usecase.DeleteGoalUseCase;
import io.github.guilherme_eira.money_tracker_app.application.usecase.UpdateGoalUseCase;
import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;
import io.github.guilherme_eira.money_tracker_app.infra.security.SecurityUser;
import io.github.guilherme_eira.money_tracker_app.infra.web.form.CreateGoalForm;
import io.github.guilherme_eira.money_tracker_app.infra.web.form.UpdateGoalForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

@Controller
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalsController {

    private final GetGoalsPageDataQuery getGoalsPageDataQuery;
    private final GetCategoriesQuery getCategoriesQuery;
    private final CreateGoalUseCase createGoalUseCase;
    private final FindGoalForUpdateQuery findGoalForUpdateQuery;
    private final UpdateGoalUseCase updateGoalUseCase;
    private final DeleteGoalUseCase deleteGoalUseCase;

    private final String GOALS_CREATION_FORM_TEMPLATE = "app/goals/create";
    private final String GOALS_UPDATE_FORM_TEMPLATE = "app/goals/update";

    @GetMapping
    public String showGoalsPage(Model model,
                                @AuthenticationPrincipal SecurityUser securityUser,
                                @RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "month", required = false) Integer month,
                                @RequestParam(value = "year", required = false) Integer year
    )
    {
        var userId = securityUser.getId();
        var now = LocalDate.now();
        var pageSize = 6;

        var input = new GetGoalsPageDataInput(
                userId,
                month == null? now.getMonthValue() : month,
                year == null? now.getYear() : year,
                pageSize,
                page);

        var goalsData = getGoalsPageDataQuery.execute(input);
        model.addAttribute("goalsData", goalsData);

        return "app/goals/goals";
    }

    @GetMapping("/create")
    public String showNewGoalForm(Model model){
        model.addAttribute("createGoalForm", new CreateGoalForm(null, null,  YearMonth.now()));
        var expenseCategories = getCategoriesQuery.execute(TransactionType.EXPENSE);
        model.addAttribute("categories", expenseCategories);
        return GOALS_CREATION_FORM_TEMPLATE;
    }

    @PostMapping("/create")
    public String createNewGoal(Model model,
                                @Valid @ModelAttribute("createGoalForm") CreateGoalForm form,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal SecurityUser securityUser
                                ){

        var input = new CreateGoalInput(
                form.categoryId(),
                form.maxExpense(),
                form.startDate(),
                securityUser.getId()
        );

        if (result.hasErrors()){
            var expenseCategories = getCategoriesQuery.execute(TransactionType.EXPENSE);
            model.addAttribute("categories", expenseCategories);
            return GOALS_CREATION_FORM_TEMPLATE;
        }

        try {
            createGoalUseCase.execute(input);
            redirectAttributes.addFlashAttribute("successMessage", "Meta criada com sucesso!");
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            var expenseCategories = getCategoriesQuery.execute(TransactionType.EXPENSE);
            model.addAttribute("categories", expenseCategories);
            return GOALS_CREATION_FORM_TEMPLATE;
        }
        return "redirect:/goals";
    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable UUID id, Model model){
        var goal = findGoalForUpdateQuery.execute(id);
        model.addAttribute("updateGoalForm", new UpdateGoalForm(
                id,
                goal.categoryName(),
                goal.maxExpense(),
                convertDate(goal.startDate())
        ));
        return GOALS_UPDATE_FORM_TEMPLATE;
    }

    @PostMapping("/update")
    public String updateTransaction(Model model,
                                    @Valid @ModelAttribute("updateGoalForm") UpdateGoalForm form,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes){

        var goal = findGoalForUpdateQuery.execute(form.getId());

        var input = new UpdateGoalInput(
                form.getId(),
                form.getMaxExpense()
        );

        if(result.hasErrors()){
            form.setCategoryName(goal.categoryName());
            form.setStartDate(convertDate(goal.startDate()));
            return GOALS_UPDATE_FORM_TEMPLATE;
        }

        try{
            updateGoalUseCase.execute(input);
            redirectAttributes.addFlashAttribute("successMessage", "Meta atualizada com sucesso!");
        } catch(Exception ex){
            form.setCategoryName(goal.categoryName());
            form.setStartDate(convertDate(goal.startDate()));
            model.addAttribute("errorMessage", ex.getMessage());
            return GOALS_UPDATE_FORM_TEMPLATE;
        }

        return "redirect:/goals";
    }

    @PostMapping("/{id}/delete")
    public String deleteGoal(@PathVariable UUID id, RedirectAttributes redirectAttributes){
        deleteGoalUseCase.execute(id);
        redirectAttributes.addFlashAttribute("successMessage", "Meta excluída com sucesso!");
        return "redirect:/goals";
    }

    private YearMonth convertDate(LocalDate startDate){
        return YearMonth.of(startDate.getYear(), startDate.getMonthValue());
    }

}

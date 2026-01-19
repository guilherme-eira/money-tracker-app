package io.github.guilherme_eira.money_tracker_app.infra.web.controller;

import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.CreateTransactionInput;
import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.GetTransactionsPageDataInput;
import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.UpdateTransactionInput;
import io.github.guilherme_eira.money_tracker_app.application.query.FindTransactionForUpdateQuery;
import io.github.guilherme_eira.money_tracker_app.application.query.GetCategoriesQuery;
import io.github.guilherme_eira.money_tracker_app.application.query.GetTransactionsPageDataQuery;
import io.github.guilherme_eira.money_tracker_app.application.usecase.CreateTransactionUseCase;
import io.github.guilherme_eira.money_tracker_app.application.usecase.DeleteTransactionUseCase;
import io.github.guilherme_eira.money_tracker_app.application.usecase.UpdateTransactionUseCase;
import io.github.guilherme_eira.money_tracker_app.infra.security.SecurityUser;
import io.github.guilherme_eira.money_tracker_app.infra.web.form.CreateTransactionForm;
import io.github.guilherme_eira.money_tracker_app.infra.web.form.UpdateTransactionForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private final CreateTransactionUseCase createTransactionUseCase;
    private final UpdateTransactionUseCase updateTransactionUseCase;
    private final GetTransactionsPageDataQuery getTransactionsPageDataQuery;
    private final GetCategoriesQuery getAllCategoriesQuery;
    private final FindTransactionForUpdateQuery findTransactionForUpdateQuery;
    private final DeleteTransactionUseCase deleteTransactionUseCase;

    private final String TRANSACTIONS_CREATION_FORM_TEMPLATE = "app/transactions/create";
    private final String TRANSACTIONS_UPDATE_FORM_TEMPLATE = "app/transactions/update";

    @GetMapping
    public String showTransactionPage(
            Model model,
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "categoryId", required = false) UUID categoryId,
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "year", required = false) Integer year
    ) {

        var userId = securityUser.getId();
        var now = LocalDateTime.now();
        var pageSize = 4;

        var input = new GetTransactionsPageDataInput(
                userId,
                categoryId,
                Objects.equals(description, "") ? null : description,
                month != null? month : now.getMonthValue(),
                year !=null? year : now.getYear(),
                pageSize,
                page
        );

        var transactionsData = getTransactionsPageDataQuery.execute(input);
                model.addAttribute("transactionsData", transactionsData);

        return "app/transactions/transactions";
    }

    @GetMapping("/create")
    public String showCreationForm(Model model){
        model.addAttribute("createTransactionForm", new CreateTransactionForm(null, null, null, LocalDate.now()));
        var categories = getAllCategoriesQuery.execute(null);
        model.addAttribute("allCategories", categories);
        return TRANSACTIONS_CREATION_FORM_TEMPLATE;
    }

    @PostMapping("/create")
    public String createTransaction(Model model,
                                       @Valid @ModelAttribute("createTransactionForm") CreateTransactionForm form,
                                       BindingResult result,
                                       RedirectAttributes redirectAttributes,
                                       @AuthenticationPrincipal SecurityUser securityUser){

        var categories = getAllCategoriesQuery.execute(null);
        var input = new CreateTransactionInput(
                form.description(),
                form.categoryId(),
                form.value(),
                form.date(),
                securityUser.getId()
        );

        if(result.hasErrors()){
            model.addAttribute("allCategories", categories);
            return TRANSACTIONS_CREATION_FORM_TEMPLATE;
        }

        try{
            createTransactionUseCase.execute(input);
            redirectAttributes.addFlashAttribute("successMessage", "Transação criada com sucesso!");

        } catch(Exception ex){
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("allCategories", categories);
            return TRANSACTIONS_CREATION_FORM_TEMPLATE;
        }

        return "redirect:/transactions";
    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable UUID id, Model model){
        var transaction = findTransactionForUpdateQuery.execute(id);
        model.addAttribute("allCategories", getAllCategoriesQuery.execute(null));
        model.addAttribute("updateTransactionForm", new UpdateTransactionForm(
                id,
                transaction.description(),
                transaction.categoryId(),
                transaction.value(),
                transaction.date()
        ));
        return TRANSACTIONS_UPDATE_FORM_TEMPLATE;
    }

    @PostMapping("/update")
    public String updateTransaction(Model model,
                                       @Valid @ModelAttribute("updateTransactionForm") UpdateTransactionForm form,
                                       BindingResult result,
                                       RedirectAttributes redirectAttributes){

        var categories = getAllCategoriesQuery.execute(null);
        var input = new UpdateTransactionInput(
                form.getId(),
                form.getDescription(),
                form.getCategoryId(),
                form.getValue(),
                form.getDate()
        );

        if(result.hasErrors()){
            model.addAttribute("allCategories", categories);
            return TRANSACTIONS_UPDATE_FORM_TEMPLATE;
        }

        try{
            updateTransactionUseCase.execute(input);
            redirectAttributes.addFlashAttribute("successMessage", "Transação atualizada com sucesso!");
        } catch(Exception ex){
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("allCategories", categories);
            return TRANSACTIONS_UPDATE_FORM_TEMPLATE;
        }

        return "redirect:/transactions";
    }

    @PostMapping("/{id}/delete")
    public String deleteTransaction(@PathVariable UUID id, RedirectAttributes redirectAttributes){
        deleteTransactionUseCase.execute(id);
        redirectAttributes.addFlashAttribute("successMessage", "Transação excluída com sucesso!");
        return "redirect:/transactions";
    }

}

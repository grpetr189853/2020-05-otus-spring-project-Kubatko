package ru.skubatko.dev.ees.ui.controller;

import ru.skubatko.dev.ees.ui.dto.EesCriterionDto;
import ru.skubatko.dev.ees.ui.feign.CriterionResourceFeignClient;
import ru.skubatko.dev.ees.ui.mapper.EesCriterionDtoToResourceMapper;
import ru.skubatko.dev.ees.ui.service.EesCriterionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class CriteriaController {

    private final EesCriterionService criterionService;
    private final CriterionResourceFeignClient criterionResource;
    private final EesCriterionDtoToResourceMapper toResourceMapper;

    @GetMapping("/ees/criteria")
    public String listCriteria(Model model, Principal principal) {
        model.addAttribute("criteria", criterionService.findByUser(principal.getName()));
        return "criteria/list-criteria";
    }

    @GetMapping("/ees/criteria/add")
    public String showAddCriterionForm(HttpServletRequest request, Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (Objects.nonNull(inputFlashMap)) {
            model.addAttribute("error", inputFlashMap.get("error"));
        }

        model.addAttribute("criterion", new EesCriterionDto());
        return "/criteria/add-criterion";
    }

    @PostMapping("/ees/criteria/add")
    public RedirectView addCriterion(@ModelAttribute("criterion") @Valid EesCriterionDto criterion,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return new RedirectView("/ees/criteria/add", true);
        }

        criterionResource.save(toResourceMapper.map(criterion));
        criterionService.save(criterion);
        redirectAttributes.addFlashAttribute("criterion", criterion);
        return new RedirectView("/ees/criteria/add/success", true);
    }

    @GetMapping("/ees/criteria/add/success")
    public String showAddCriterionSuccessForm(HttpServletRequest request, Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (Objects.nonNull(inputFlashMap)) {
            EesCriterionDto criterion = (EesCriterionDto) inputFlashMap.get("criterion");
            model.addAttribute("criterion", criterion);
            return "criteria/add-criterion-success";
        } else {
            return "redirect:/ees/criteria";
        }
    }

    @GetMapping("/ees/criteria/edit/{name}")
    public String showUpdateCriterionForm(@PathVariable("name") String name,
                                     HttpServletRequest request,
                                     Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (Objects.nonNull(inputFlashMap)) {
            model.addAttribute("error", inputFlashMap.get("error"));
        }

        EesCriterionDto criterion = criterionService.getByName(name);
        model.addAttribute("criterion", criterion);
        return "criteria/update-criterion";
    }

    @PostMapping("/ees/criteria/update/{name}")
    public RedirectView updateCriterion(@PathVariable("name") String name,
                                   @ModelAttribute("criterion") EesCriterionDto criterion,
                                   RedirectAttributes redirectAttributes) {
        if (criterion.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Criterion's name is mandatory");
            return new RedirectView(String.format("/ees/criteria/edit/%s", name), true);
        }

        criterionResource.save(toResourceMapper.map(criterion));
        criterionService.updateBook(name, criterion);
        return new RedirectView("/ees/criteria", true);
    }

    @GetMapping("/ees/criteria/delete/{name}")
    public String deleteCriterion(@PathVariable("name") String name) {
        criterionService.deleteByName(name);
        return "redirect:/ees/criteria";
    }
}

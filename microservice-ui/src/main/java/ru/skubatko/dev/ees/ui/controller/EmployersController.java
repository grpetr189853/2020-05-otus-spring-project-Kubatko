package ru.skubatko.dev.ees.ui.controller;

import ru.skubatko.dev.ees.ui.dto.EesEmployerDto;
import ru.skubatko.dev.ees.ui.feign.EmployersResourceFeignClient;
import ru.skubatko.dev.ees.ui.mappers.EesEmployerDtoToResourceMapper;
import ru.skubatko.dev.ees.ui.service.EesCriterionService;
import ru.skubatko.dev.ees.ui.service.EesEmployerService;

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
public class EmployersController {

    private final EesEmployerService employerService;
    private final EesCriterionService criterionService;
    private final EmployersResourceFeignClient employerResource;
    private final EesEmployerDtoToResourceMapper toResourceMapper;

    @GetMapping("/ees/employers")
    public String listEmployers(Model model, Principal principal) {
        model.addAttribute("employers", employerService.findAllByUser(principal.getName()));
        return "employers/list-employers";
    }

    @GetMapping("/ees/employers/add")
    public String showAddEmployerForm(HttpServletRequest request, Model model, Principal principal) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (Objects.nonNull(inputFlashMap)) {
            model.addAttribute("error", inputFlashMap.get("error"));
        }

        model.addAttribute("employer", new EesEmployerDto()
                                               .setCriteria(criterionService.findByUser(principal.getName())));
        return "employers/add-employer";
    }

    @PostMapping("/ees/employers/add")
    public RedirectView addEmployer(@ModelAttribute("employer") @Valid EesEmployerDto employer,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return new RedirectView("/ees/employers/add", true);
        }

        employerResource.save(toResourceMapper.map(employer));
        employerService.save(employer);
        redirectAttributes.addFlashAttribute("employer", employer);
        return new RedirectView("/ees/employers/add/success", true);
    }

    @GetMapping("/ees/employers/add/success")
    public String showAddEmployerSuccessForm(HttpServletRequest request, Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (Objects.nonNull(inputFlashMap)) {
            EesEmployerDto employer = (EesEmployerDto) inputFlashMap.get("employer");
            model.addAttribute("employer", employer);
            return "employers/add-employer-success";
        } else {
            return "redirect:/ees/employers";
        }
    }

    @GetMapping("/ees/employers/edit/{name}")
    public String showUpdateEmployerForm(@PathVariable("name") String name,
                                         HttpServletRequest request,
                                         Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (Objects.nonNull(inputFlashMap)) {
            model.addAttribute("error", inputFlashMap.get("error"));
        }

        EesEmployerDto employer = employerService.getByName(name);
        model.addAttribute("employer", employer);
        return "employers/update-employer";
    }

    @PostMapping("/ees/employers/update/{name}")
    public RedirectView updateEmployer(@PathVariable("name") String name,
                                       @ModelAttribute("employer") EesEmployerDto employer,
                                       RedirectAttributes redirectAttributes) {
        if (employer.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Employer's name is mandatory");
            return new RedirectView(String.format("/ees/employers/edit/%s", name), true);
        }

        employerResource.save(toResourceMapper.map(employer));
        employerService.updateEmployer(name, employer);
        return new RedirectView("/ees/employers", true);
    }

    @GetMapping("/ees/employers/delete/{name}")
    public String deleteEmployer(@PathVariable("name") String name, Model model) {
        employerService.deleteByName(name);
        return "redirect:/ees/employers";
    }
}

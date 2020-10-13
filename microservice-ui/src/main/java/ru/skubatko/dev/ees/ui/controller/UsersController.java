package ru.skubatko.dev.ees.ui.controller;

import ru.skubatko.dev.ees.ui.dto.EesUserDto;
import ru.skubatko.dev.ees.ui.feign.UsersResourceFeignClient;
import ru.skubatko.dev.ees.ui.feign.dto.UserDto;
import ru.skubatko.dev.ees.ui.mapper.EesUserDtoToResourceMapper;
import ru.skubatko.dev.ees.ui.mapper.ResourceUserToDtoMapper;
import ru.skubatko.dev.ees.ui.service.EesUserService;

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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final EesUserService userService;
    private final UsersResourceFeignClient usersResource;
    private final EesUserDtoToResourceMapper toResourceMapper;
    private final ResourceUserToDtoMapper resourceToDtoMapper;

    @GetMapping("/ees/users")
    public String listUsers(Model model) {
        model.addAttribute("users", usersResource.findAll().stream()
                                            .map(resourceToDtoMapper::map)
                                            .collect(Collectors.toList()));
        return "users/list-users";
    }

    @GetMapping("/ees/users/add")
    public String showAddUserForm(HttpServletRequest request, Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (Objects.nonNull(inputFlashMap)) {
            model.addAttribute("error", inputFlashMap.get("error"));
        }

        model.addAttribute("user", new EesUserDto());
        return "/users/add-user";
    }

    @PostMapping("/ees/users/add")
    public RedirectView addUser(@ModelAttribute("user") @Valid EesUserDto user,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return new RedirectView("/ees/users/add", true);
        }

        usersResource.save(toResourceMapper.map(user));
        userService.save(user);
        redirectAttributes.addFlashAttribute("user", user);
        return new RedirectView("/ees/users/add/success", true);
    }

    @GetMapping("/ees/users/add/success")
    public String showAddUserSuccessForm(HttpServletRequest request, Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (Objects.nonNull(inputFlashMap)) {
            EesUserDto user = (EesUserDto) inputFlashMap.get("user");
            model.addAttribute("user", user);
            return "users/add-user-success";
        } else {
            return "redirect:/ees/users";
        }
    }

    @GetMapping("/ees/users/edit/{name}")
    public String showUpdateUserForm(@PathVariable("name") String name,
                                     HttpServletRequest request,
                                     Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (Objects.nonNull(inputFlashMap)) {
            model.addAttribute("error", inputFlashMap.get("error"));
        }

        EesUserDto user = new EesUserDto();
        Optional<UserDto> optionalUser = usersResource.findByName(name);
        if (optionalUser.isPresent()) {
            user = resourceToDtoMapper.map(optionalUser.get());
        }

        model.addAttribute("user", user);
        return "users/update-user";
    }

    @PostMapping("/ees/users/update/{name}")
    public RedirectView updateUser(@PathVariable("name") String name,
                                   @ModelAttribute("user") EesUserDto user,
                                   RedirectAttributes redirectAttributes) {
        if (user.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User's name is mandatory");
            return new RedirectView(String.format("/ees/users/edit/%s", name), true);
        }

        usersResource.update(name, toResourceMapper.map(user));
        userService.update(name, user);
        return new RedirectView("/ees/users", true);
    }

    @GetMapping("/ees/users/delete/{name}")
    public String deleteUser(@PathVariable("name") String name) {
        usersResource.deleteByName(name);
        userService.deleteByName(name);
        return "redirect:/ees/users";
    }
}

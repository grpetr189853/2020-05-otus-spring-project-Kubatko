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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final EesUserService userService;
    private final UsersResourceFeignClient usersResource;
    private final EesUserDtoToResourceMapper toResourceMapper;
    private final ResourceUserToDtoMapper resourceToDtoMapper;

    @GetMapping("/ees/profile")
    public String showProfile(Model model, Principal principal) {
        model.addAttribute("profile", getEesUserDto(principal));
        return "profile/show-profile";
    }

    @GetMapping("/ees/profile/edit")
    public String showUpdateProfileForm(HttpServletRequest request, Model model, Principal principal) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (Objects.nonNull(inputFlashMap)) {
            model.addAttribute("error", inputFlashMap.get("error"));
        }

        model.addAttribute("profile", getEesUserDto(principal));
        return "profile/update-profile";
    }

    @PostMapping("/ees/profile/update")
    public RedirectView updateProfile(@ModelAttribute("profile") EesUserDto profile,
                                      RedirectAttributes redirectAttributes) {
        if (profile.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User's name is mandatory");
            return new RedirectView("/ees/profile/edit", true);
        }

        usersResource.save(toResourceMapper.map(profile));
        userService.update(profile.getName(), profile);
        return new RedirectView("/ees/profile", true);
    }

    private EesUserDto getEesUserDto(Principal principal) {
        EesUserDto user = new EesUserDto();
        Optional<UserDto> optionalUser = usersResource.findByName(principal.getName());
        if (optionalUser.isPresent()) {
            user = resourceToDtoMapper.map(optionalUser.get());
        }
        return user;
    }
}

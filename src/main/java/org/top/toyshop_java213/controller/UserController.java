package org.top.toyshop_java213.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.toyshop_java213.form.UserRegistrationForm;
import org.top.toyshop_java213.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // регистрация пользователя
    @GetMapping("register")
    public String getRegistrationForm(Model model) {
        UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
        model.addAttribute("userRegistrationForm", userRegistrationForm);
        return "registration-form";
    }

    @PostMapping("register")
    public String postRegistrationForm(UserRegistrationForm userRegistrationForm, RedirectAttributes ra) {
        // установить роль пользователя
        userRegistrationForm.setRole("USER");
        if (userService.register(userRegistrationForm)) {
            return "redirect:/login";
        }
        ra.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE, "Пользователь не зарегистрирован, проверьте правильность ввода");
        return "redirect:/user/register";
    }
}

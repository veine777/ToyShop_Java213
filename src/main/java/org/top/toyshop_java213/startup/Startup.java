package org.top.toyshop_java213.startup;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.top.toyshop_java213.form.UserRegistrationForm;
import org.top.toyshop_java213.service.UserService;

@Service
public class Startup {

    private final UserService userService;

    public Startup(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        if (userService.findUsersByRole("ADMIN").iterator().hasNext()) {
            System.out.println("Суперпользователь существует");
            return;
        }
        UserRegistrationForm superUserForm = new UserRegistrationForm();
        superUserForm.setLogin("admin");
        superUserForm.setPassword("qwerty");
        superUserForm.setPasswordConfirmation("qwerty");
        superUserForm.setRole("ADMIN");
        if (userService.register(superUserForm)) {
            System.out.println("Создан суперпользователь по умолчанию");
        } else {
            System.out.println("Не удается создать суперпользователя по умолчанию");
        }
    }
}

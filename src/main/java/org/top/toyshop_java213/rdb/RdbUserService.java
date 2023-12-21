package org.top.toyshop_java213.rdb;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.top.toyshop_java213.entity.User;
import org.top.toyshop_java213.form.UserRegistrationForm;
import org.top.toyshop_java213.rdb.repository.UserRepository;
import org.top.toyshop_java213.service.UserService;

import java.util.Optional;

// RdbUserService - бизнес логика работы с пользователями
@Service
public class RdbUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RdbUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // бизнес-логика регистрации пользователя
    @Override
    public boolean register(UserRegistrationForm userRegistrationForm) {

        // 2. проверить, уникален ли логин

        // 1. проверить подтверждение пароля
        if (!userRegistrationForm.getPassword().equals(userRegistrationForm.getPasswordConfirmation())) {
            return false;
        }
        if (userRepository.findByLogin(userRegistrationForm.getLogin()).isPresent()) {
            return false;
        }
        // создаем пользователя
        User user = new User();
        user.setLogin(userRegistrationForm.getLogin());
        String passwordHash = passwordEncoder.encode(userRegistrationForm.getPassword());
        user.setPassword(passwordHash);
        user.setRole(userRegistrationForm.getRole());
        userRepository.save(user);
        return true;
    }

    @Override
    public Iterable<User> findUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }


}

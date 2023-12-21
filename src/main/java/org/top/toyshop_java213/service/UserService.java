package org.top.toyshop_java213.service;

import org.springframework.stereotype.Service;
import org.top.toyshop_java213.entity.User;
import org.top.toyshop_java213.form.UserRegistrationForm;

import java.util.Optional;

// UserService - сервис для работы с пользователями
@Service
public interface UserService {

    boolean register(UserRegistrationForm userRegistrationForm);

    Iterable<User> findUsersByRole(String role);

    Optional<User> findUserByLogin(String login);

    Optional<User> findById(Integer id);
}

package org.top.toyshop_java213.rdb.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.top.toyshop_java213.entity.User;
import org.top.toyshop_java213.rdb.repository.UserRepository;

import java.util.Optional;

// UserDetailsService - сервис для аутентификации пользователей и получения информации о них
@Service
public class RdbUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public RdbUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. получить пользователя
        Optional<User> user = userRepository.findByLogin(username);
        // 2. если пользователь не найден - выкинуть ошибку
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        // 3. иначе вернуть UserDetails
        return new RdbUserDetails(user.get());
    }
}

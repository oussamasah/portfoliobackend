package dev.portfolio.oussama.service;
import dev.portfolio.oussama.model.User;
import dev.portfolio.oussama.repository.UserRepository;
import dev.portfolio.oussama.util.JwtTokenUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.Optional;

@Service
public  class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    public Optional<String> authenticateUser(String username, String password) {


        Optional<User> user = userRepository.findByUsername(username);
        System.out.println("----------------------------------"+user.isPresent()+"----------------------------"+passwordEncoder.matches(password, user.get().getPassword()));
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++"+user.get().getUsername());
            System.out.println(jwtTokenUtil.generateToken(user.get().getUsername()));
            return Optional.of(jwtTokenUtil.generateToken(user.get().getUsername()));
        }
        return Optional.empty();
    }

}

package com.kpo.springshaurma.controller;

import com.kpo.springshaurma.entity.User;
import com.kpo.springshaurma.repository.UserRepository;
import jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public UserController(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        User user = userRepository.findByEmail(email);

        if (user == null || !password.equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверные учетные данные");
        }

        String token = jwtUtils.generateToken(user.getId());

        return ResponseEntity.ok(token);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtils.getUserIdFromToken(token);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный токен");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User registrationRequest) {
        try {
            // Проверка корректности входных данных
            if (!isValidEmail(registrationRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Некорректный адрес электронной почты");
            }

            // Проверка наличия пользователя с таким же адресом электронной почты
            if (userRepository.existsByEmail(registrationRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Пользователь с таким адресом электронной почты уже существует");
            }

            // Создание нового пользователя
            User user = new User();
            user.setUsername(registrationRequest.getUsername());
            user.setEmail(registrationRequest.getEmail());
            user.setPasswordHash(registrationRequest.getPassword());
            user.setRole(registrationRequest.getRole());

            // Сохранение пользователя в базе данных
            userRepository.save(user);

            // Подтверждающее сообщение после успешной регистрации
            return ResponseEntity.ok("Пользователь успешно зарегистрирован");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при регистрации пользователя");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
}

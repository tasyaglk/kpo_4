package com.kpo.springshaurma.impl;

import com.kpo.springshaurma.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Возвращаем список ролей пользователя в формате GrantedAuthority
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Здесь можно добавить логику проверки действительности аккаунта
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Здесь можно добавить логику проверки блокировки аккаунта
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Здесь можно добавить логику проверки срока действия учетных данных
    }

    @Override
    public boolean isEnabled() {
        return true; // Здесь можно добавить логику проверки активации аккаунта
    }
}

package com.example.sklep2xd.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final int klientId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, int klientId) {
        super(username, password, authorities);
        this.klientId = klientId;
    }

    public int getKlientId() {
        return klientId;
    }
}
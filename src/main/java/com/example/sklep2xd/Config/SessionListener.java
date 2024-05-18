package com.example.sklep2xd.Config;

import com.example.sklep2xd.Service.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@Component
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            // Set the klientId in the session
            event.getSession().setAttribute("klientId", userDetails.getKlientId());
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        // Cleanup if needed
    }
}
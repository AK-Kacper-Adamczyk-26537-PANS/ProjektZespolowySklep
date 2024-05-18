package com.example.sklep2xd.Service;

import com.example.sklep2xd.Models.PracownikEntity;
import com.example.sklep2xd.Models.KlientEntity;
import com.example.sklep2xd.Models.Role;
import com.example.sklep2xd.Repositories.KlientRep;
import com.example.sklep2xd.Repositories.PracownikRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PracownikRep pracownikRepository;

    @Autowired
    private KlientRep klientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PracownikEntity pracownik = pracownikRepository.findByLogin(username);
        if (pracownik != null) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            for (Role role : pracownik.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
            return new CustomUserDetails(pracownik.getLogin(), pracownik.getHaslo(), authorities, pracownik.getIdPracownika());
        }

        KlientEntity klient = klientRepository.findByLogin(username);
        if (klient != null) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + klient.getRole()));
            return new CustomUserDetails(klient.getLogin(), klient.getHaslo(), authorities, klient.getIdKlienta());
        }

        throw new UsernameNotFoundException("User not found");
    }
}
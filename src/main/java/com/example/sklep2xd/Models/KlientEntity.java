package com.example.sklep2xd.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "klient", schema = "public", catalog = "Sklep")
public class KlientEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_klienta", nullable = false)
    private int idKlienta;
    @Basic
    @Column(name = "login", nullable = true, length = 30)
    private String login;
    @Basic
    @Column(name = "email", nullable = true, length = 100)
    private String email;
    @Basic
    @Column(name = "haslo", nullable = true, length = 100)
    private String haslo;
    @Basic
    @Column(name = "telefon", nullable = true, length = 13)
    private String telefon;
//    @Basic
//    @Column(name = "adres_id", nullable = true)
//    private Integer adresId;
    @Basic
    @Column(name = "imie", nullable = true, length = 50)
    private String imie;
    @Basic
    @Column(name = "nazwisko", nullable = true, length = 50)
    private String nazwisko;
    @ManyToOne
    @JoinColumn(name = "adres_id", referencedColumnName = "id_adresu")
    private AdresEntity adresId;

    private String role = "KLIENT";

    public int getIdKlienta() {
        return idKlienta;
    }

    public void setIdKlienta(int idKlienta) {
        this.idKlienta = idKlienta;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

//    public Integer getAdresId() {
//        return adresId;
//    }
//
//    public void setAdresId(Integer adresId) {
//        this.adresId = adresId;
//    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

//    public AdresEntity getAdresByAdresId() {
//        return adresId;
//    }
//
//    public void setAdresByAdresId(AdresEntity adresByAdresId) {
//        this.adresId = adresByAdresId;
//    }

    public AdresEntity getAdresId() {
        return adresId;
    }

    public void setAdresId(AdresEntity adresId) {
        this.adresId = adresId;
    }
}

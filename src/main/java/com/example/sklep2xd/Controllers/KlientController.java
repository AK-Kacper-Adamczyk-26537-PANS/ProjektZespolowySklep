package com.example.sklep2xd.Controllers;

import com.example.sklep2xd.Dto.KlientDto;
import com.example.sklep2xd.Models.AdresEntity;
import com.example.sklep2xd.Models.KlientEntity;
import com.example.sklep2xd.Repositories.AdresRep;
import com.example.sklep2xd.Repositories.KlientRep;
import com.example.sklep2xd.Service.AdresService;
import com.example.sklep2xd.Service.KlientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/klienci")
public class KlientController {
    private final KlientService klientService;
    private final AdresService adresService;

    @Autowired
    private KlientRep klientRepository;

    @Autowired
    private AdresRep adresRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public KlientController(KlientService klientService, AdresService adresService, PasswordEncoder passwordEncoder) {
        this.klientService = klientService;
        this.adresService = adresService;
        this.passwordEncoder = passwordEncoder;
    }

//    @GetMapping("/lista")
//    public String listKlients(Model model) {
//        List<KlientDto> klienci = klientService.findAllKlients();
//        model.addAttribute("header", "Lista wszystkich Klientów");
//        model.addAttribute("klientList", klienci);
//        return "Klienci";
//    }


//    @GetMapping("/{klientId}")
//    public String singleKlient(@PathVariable("klientId") int klientId, Model model) {
//        KlientDto klient = klientService.findKlientById(klientId);
//        model.addAttribute("klient", klient);
//        return "/Klient/single";
//    }

    @GetMapping("/dodajform")
    public String createKlientForm(Model model) {
        KlientEntity klient = new KlientEntity();
        model.addAttribute("klient", klient);
        model.addAttribute("hasAddress", klient.getAdresId() != null); //jakby był klient bez adresu jeszcze
        return "Rejestracja";
    }

    @PostMapping("/dodajform")
    public String saveKlient(@ModelAttribute("klient") KlientEntity klient, Model model) {
        // Sprawdzenie czy istnieje użytkownik o podanym e-mailu
        String pass = klient.getHaslo();
        pass = passwordEncoder.encode(pass);
        klient.setHaslo(pass);
        KlientEntity existingEmailUser = klientService.findKlientByEmail(klient.getEmail());
        if (existingEmailUser != null) {
            model.addAttribute("errorMessage", "Użytkownik z podanym adresem e-mail już istnieje.");
            return "Logowanie";
        }

        // Sprawdzenie czy istnieje użytkownik o podanym loginie
        KlientEntity existingLoginUser = klientService.findKlientByLogin(klient.getLogin());
        if (existingLoginUser != null) {
            model.addAttribute("errorMessage", "Użytkownik z podanym loginem już istnieje.");
            return "Rejestracja";
        }

        // Jeśli nie istnieje, zapisujemy nowego użytkownika
        klientService.saveKlient(klient);
        return "StronaGlowna";
    }


    @GetMapping("/edytuj")
    public String editKlientForm(HttpSession session, Model model) {
        Integer klientId = (Integer) session.getAttribute("klientId");
        if (klientId == null) {
            // Redirect to login if klientId is not found in the session
            return "redirect:/logowanie";
        }
        KlientDto klient = klientService.findKlientById(klientId);
        model.addAttribute("klient", klient);
        model.addAttribute("hasAddress", klient.getAdresId() != null);
        return "TwojeKonto";
    }

    @PostMapping("/edytuj")
    public String edytujKlienta(HttpSession session,
                                @RequestParam("imie") String imie,
                                @RequestParam("nazwisko") String nazwisko,
                                @RequestParam("email") String email,
                                @RequestParam(value = "haslo", required = false) String haslo,
                                @RequestParam("ulica") String ulica,
                                @RequestParam("nrDomu") String nrDomu,
                                @RequestParam("nrMieszkania") String nrMieszkania,
                                @RequestParam("kodPocztowy") String kodPocztowy,
                                @RequestParam("miejscowosc") String miejscowosc,
                                @RequestParam("kraj") String kraj) {
        Integer klientId = (Integer) session.getAttribute("klientId");
        if (klientId == null) {
            return "redirect:/logowanie"; // or some other appropriate action
        }
        // Pobierz klienta z bazy danych
        KlientEntity klient = klientRepository.findById(klientId).orElse(null);
        if (klient != null) {
            // Zaktualizuj dane klienta
            klient.setImie(imie);
            klient.setNazwisko(nazwisko);
            klient.setEmail(email);
            if (haslo != null && !haslo.isEmpty()) {
                klient.setHaslo(passwordEncoder.encode(haslo));
            }
            klientRepository.save(klient);

            // Zaktualizuj dane adresowe tylko jeśli są podane wartości
            if (!ulica.isEmpty() || !nrDomu.isEmpty() || !nrMieszkania.isEmpty() || !kodPocztowy.isEmpty() || !miejscowosc.isEmpty() || !kraj.isEmpty()) {
                AdresEntity adres = klient.getAdresId();
                if (adres == null) {
                    adres = new AdresEntity();
                }
                adres.setUlica(ulica);
                adres.setNrDomu(nrDomu);
                adres.setNrMieszkania(nrMieszkania);
                adres.setKodPocztowy(kodPocztowy);
                adres.setMiejscowosc(miejscowosc);
                adres.setKraj(kraj);
                adresRepository.save(adres);

                // Przypisz adres do klienta jeśli nowy
                if (klient.getAdresId() == null) {
                    klient.setAdresId(adres);
                    klientRepository.save(klient);
                }
            }
        }
        // Przekieruj użytkownika na odpowiednią stronę po zapisie zmian
        return "StronaGlowna";
    }


}
package com.example.sklep2xd.Controllers;

import com.example.sklep2xd.Dto.KlientDto;
import com.example.sklep2xd.Models.AdresEntity;
import com.example.sklep2xd.Models.KlientEntity;
import com.example.sklep2xd.Repositories.AdresRep;
import com.example.sklep2xd.Repositories.KlientRep;
import com.example.sklep2xd.Service.AdresService;
import com.example.sklep2xd.Service.KlientService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    public KlientController(KlientService klientService, AdresService adresService) {
        this.klientService = klientService;
        this.adresService = adresService;
    }

    @GetMapping("/lista")
    public String listKlients(Model model) {
        List<KlientDto> klienci = klientService.findAllKlients();
        model.addAttribute("header", "Lista wszystkich Klientów");
        model.addAttribute("klientList", klienci);
        return "Klienci";
    }


    @GetMapping("/{klientId}")
    public String singleKlient(@PathVariable("klientId") int klientId, Model model) {
        KlientDto klient = klientService.findKlientById(klientId);
        model.addAttribute("klient", klient);
        return "/Klient/single";
    }

    @GetMapping("/dodajform")
    public String createKlientForm(Model model) {
        KlientEntity klient = new KlientEntity();
        model.addAttribute("klient", klient);
        return "Rejestracja";
    }

    @PostMapping("/dodajform")
    public String saveKlient(@ModelAttribute("klient") KlientEntity klient, Model model) {
        // Sprawdzenie czy istnieje użytkownik o podanym e-mailu
        KlientEntity existingEmailUser = klientService.findKlientByEmail(klient.getEmail());
        if (existingEmailUser != null) {
            model.addAttribute("errorMessage", "Użytkownik z podanym adresem e-mail już istnieje.");
            return "Rejestracja";
        }

        // Sprawdzenie czy istnieje użytkownik o podanym loginie
        KlientEntity existingLoginUser = klientService.findKlientByLogin(klient.getLogin());
        if (existingLoginUser != null) {
            model.addAttribute("errorMessage", "Użytkownik z podanym loginem już istnieje.");
            return "Rejestracja";
        }

        // Jeśli nie istnieje, zapisujemy nowego użytkownika
        klientService.saveKlient(klient);
        return "redirect:/klienci/lista";
    }


    @GetMapping("/edytuj/{klientId}")
    public String editKlientForm(@PathVariable("klientId") int klientId, Model model) {
        KlientDto klient = klientService.findKlientById(klientId);
        model.addAttribute("klient", klient);
//        return "EdytujKlienta";
        return "TwojeKonto";
    }

    // Metoda do obsługi zapisu zmian danych klienta i adresu
    @PostMapping("/edytuj/{klientId}")
    public String edytujKlienta(@PathVariable("klientId") int klientId,
                                @RequestParam("imie") String imie,
                                @RequestParam("nazwisko") String nazwisko,
                                @RequestParam("email") String email,
                                @RequestParam("haslo") String haslo,
                                @RequestParam("ulica") String ulica,
                                @RequestParam("nrDomu") String nrDomu,
                                @RequestParam("nrMieszkania") String nrMieszkania,
                                @RequestParam("kodPocztowy") String kodPocztowy,
                                @RequestParam("miejscowosc") String miejscowosc) {
        // Pobierz klienta z bazy danych
        KlientEntity klient = klientRepository.findById(klientId).orElse(null);
        if (klient != null) {
            // Zaktualizuj dane klienta
            klient.setImie(imie);
            klient.setNazwisko(nazwisko);
            klient.setEmail(email);
            klient.setHaslo(haslo);
            klientRepository.save(klient);

            // Pobierz adres klienta z bazy danych
            AdresEntity adres = klient.getAdresId();
            if (adres != null) {
                // Zaktualizuj dane adresowe
                adres.setUlica(ulica);
                adres.setNrDomu(nrDomu);
                adres.setNrMieszkania(nrMieszkania);
                adres.setKodPocztowy(kodPocztowy);
                adres.setMiejscowosc(miejscowosc);
                adresRepository.save(adres);
            }
        }
        // Przekieruj użytkownika na odpowiednią stronę po zapisie zmian
//        return "redirect:/klienci/" + klientId;
        return "StronaGlowna";
    }


}
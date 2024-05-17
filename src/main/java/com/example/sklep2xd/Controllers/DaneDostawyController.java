package com.example.sklep2xd.Controllers;

import com.example.sklep2xd.Models.AdresEntity;
import com.example.sklep2xd.Models.KlientEntity;
import com.example.sklep2xd.Repositories.AdresRep;
import com.example.sklep2xd.Repositories.KlientRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/DaneDostawy")
public class DaneDostawyController {

    @Autowired
    private KlientRep klientRep;

    @Autowired
    private AdresRep adresRep;

    @GetMapping()
    public String daneDostawy(@RequestParam("sumaCen") Double sumaCen, Model model) {
        int idKlienta = 1; // Statyczne ID klienta, do zmiany na dynamiczne
        KlientEntity klient = klientRep.findByIdKlienta(idKlienta);
        AdresEntity adres = adresRep.findById(klient.getAdresId().getIdAdresu()).orElse(new AdresEntity());

        model.addAttribute("sumaCen", sumaCen);
        model.addAttribute("klient", klient);
        model.addAttribute("adres", adres);
        return "DaneDostawy"; // Nazwa widoku (HTML) do wyświetlenia
    }

    @PostMapping("/submitDaneDostawy")
    public String submitDaneDostawy() {
        // Obsługa danych dostawy (zapis do bazy danych itp.)

        // Przekierowanie do strony potwierdzenia zamówienia
        return "redirect:/Zamowienie/ZamowienieZlozone";
    }
}

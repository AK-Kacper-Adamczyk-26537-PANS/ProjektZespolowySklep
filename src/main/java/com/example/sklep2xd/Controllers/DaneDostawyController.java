package com.example.sklep2xd.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DaneDostawyController {

    @GetMapping("/DaneDostawy")
    public String daneDostawy(Model model) {
        // Dodaj tutaj logikę do pobierania i ustawiania danych potrzebnych do widoku
        return "DaneDostawy"; // Nazwa widoku (HTML) do wyświetlenia
    }
}

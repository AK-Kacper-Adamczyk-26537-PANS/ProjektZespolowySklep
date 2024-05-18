package com.example.sklep2xd.Controllers;

import com.example.sklep2xd.Dto.RecenzjaDto;
import com.example.sklep2xd.Models.KlientEntity;
import com.example.sklep2xd.Models.ProduktEntity;
import com.example.sklep2xd.Models.RecenzjaEntity;
import com.example.sklep2xd.Repositories.KlientRep;
import com.example.sklep2xd.Repositories.ProduktRep;
import com.example.sklep2xd.Service.RecenzjaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/Recenzja")
public class RecenzjaController {

    private final RecenzjaService recenzjaService;
    @Autowired
    private KlientRep klientRep; // Inject KlientRep repository

    @Autowired
    private ProduktRep produktRep; // Inject ProduktRep repository

    @Autowired
    public RecenzjaController(RecenzjaService recenzjaService) {
        this.recenzjaService = recenzjaService;
    }

    @GetMapping("/lista")
    public String listRecenzje(Model model) {
        List<RecenzjaDto> recenzje = recenzjaService.findAllRecenzje();
        model.addAttribute("header", "Lista wszystkich Recenzji");
        model.addAttribute("recenzjaList", recenzje);
//            return "ZarzadzenieRecenzjami";
        return "ZarzadzanieRecenzjami";
    }

    @GetMapping("/dodajform")
    public String createRecenzjaForm(Model model) {
        RecenzjaEntity recenzja = new RecenzjaEntity();
        model.addAttribute("recenzja", recenzja);
        return "NowaRecenzja";
    }

//        @PostMapping("/dodajform")
//        public String saveRecenzja(@ModelAttribute("recenzja") RecenzjaEntity recenzja, int idKlienta, int idProd) {
//            KlientEntity klient = klientRep.findByIdKlienta(idKlienta);
//            ProduktEntity produkt = produktRep.findByIdProduktu(idProd);
//            recenzja.setKlientByKlientId(klient);
//            recenzja.setProduktByProduktId(produkt);
//            recenzjaService.saveRecenzja(recenzja);
//            return "redirect:/Recenzja/lista";
//        }

    @PostMapping("/dodajform")
    public String saveRecenzja(@ModelAttribute("recenzja") RecenzjaEntity recenzja,
                               @RequestParam("review") String review,
                               @RequestParam("rating") int rating,
                               HttpSession session,
                               @RequestParam("idProd") int idProd) {
        KlientEntity klient = klientRep.findByIdKlienta((int) session.getAttribute("userId"));
        ProduktEntity produkt = produktRep.findByIdProduktu(idProd);

        recenzja.setTresc(review);
        recenzja.setOcena(rating);
        recenzja.setKlientByKlientId(klient);
        recenzja.setProduktByProduktId(produkt);
        recenzjaService.saveRecenzja(recenzja);

        // Przekierowanie z powrotem na stronę produktu
        return "redirect:/Produkt/" + idProd;
    }


    @GetMapping("/edytuj/{recenzjaId}")
    public String editRecenzjaForm(@PathVariable("recenzjaId") int recenzjaId, Model model) {
        RecenzjaDto recenzja = recenzjaService.findRecenzjaById(recenzjaId);
        model.addAttribute("recenzja", recenzja);
        return "EdytujRecenzje";
    }

    @GetMapping("/lista/{idProduktu}")
    public String listRecenzjeForProduct(@PathVariable("idProduktu") int idProduktu, Model model) {
        List<RecenzjaDto> recenzje = recenzjaService.findRecenzjeByProductId(idProduktu);
        model.addAttribute("header", "Lista recenzji dla produktu");
        model.addAttribute("recenzjaList", recenzje);
        return "Recenzje";
    }


    @PostMapping("/edytuj/{recenzjaId}")
    public String updateRecenzja(@PathVariable("recenzjaId") int recenzjaId, @ModelAttribute("recenzja") RecenzjaDto recenzjaDto) {
        recenzjaDto.setIdRecenzji(recenzjaId);
        recenzjaService.updateRecenzja(recenzjaDto);
        return "redirect:/Recenzja/lista";
    }
//        @DeleteMapping("/usun/{recenzjaId}")
//        public String deleteRecenzja(@PathVariable("recenzjaId") int recenzjaId) {
//            recenzjaService.removeRecenzja(recenzjaId);
//            return "redirect:/Recenzja/lista";
//        }

    @DeleteMapping("/usun/{recenzjaId}")
    @ResponseBody
    public void deleteRecenzja(@PathVariable("recenzjaId") int recenzjaId) {
        recenzjaService.removeRecenzja(recenzjaId);
    }


}
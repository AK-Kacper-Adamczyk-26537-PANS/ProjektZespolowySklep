package com.example.sklep2xd.Controllers;

import com.example.sklep2xd.Dto.KlientDto;
import com.example.sklep2xd.Dto.ZamowienieDto;
import com.example.sklep2xd.Models.ZamowienieEntity;
import com.example.sklep2xd.Service.KlientService;
import com.example.sklep2xd.Service.ZamowienieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/Zamowienie")
public class ZamowienieController {

    private final ZamowienieService zamowienieService;

    @Autowired
    public ZamowienieController(ZamowienieService zamowienieService) {
        this.zamowienieService = zamowienieService;
    }

    @GetMapping("/lista")
    public String listZamowienia(Model model) {
        List<ZamowienieDto> zamowienia = zamowienieService.findAllZamowienia();
        model.addAttribute("header", "Lista wszystkich Zamówień");
        model.addAttribute("zamowienieList", zamowienia);
//        return "Zamowienia";
        return "ListaZamowien";
    }

    @GetMapping("/dodajform")
    public String createZamowienieForm(Model model) {
        ZamowienieEntity zamowienie = new ZamowienieEntity();
        model.addAttribute("zamowienie", zamowienie);
        return "NoweZamowienie";
    }

    @PostMapping("/dodajform")
    public String saveZamowienie(@ModelAttribute("zamowienie") ZamowienieEntity zamowienie) {
        zamowienieService.saveZamowienie(zamowienie);
        return "redirect:/Zamowienie/lista";
    }

    @GetMapping("/edytuj/{zamowienieId}")
    public String editZamowienieForm(@PathVariable("zamowienieId") int zamowienieId, Model model) {
        ZamowienieDto zamowienieDto = zamowienieService.findZamowienieById(zamowienieId);
        model.addAttribute("zamowienieDto", zamowienieDto);
        return "StatusZamowienia";
    }


    @PostMapping("/edytuj/{zamowienieId}")
    public String updateZamowienie(@PathVariable("zamowienieId") int zamowienieId, @ModelAttribute("zamowienie") ZamowienieDto zamowienieDto) {
        zamowienieDto.setIdZamowienia(zamowienieId);
        zamowienieService.updateZamowienie(zamowienieDto);
        return "redirect:/Zamowienie/lista";
    }

    @GetMapping("/ZamowienieZlozone")
    public String zamowienieZlozone() {
        return "ZamowienieZlozone"; // Nazwa widoku (HTML) do wyświetlenia
    }
}
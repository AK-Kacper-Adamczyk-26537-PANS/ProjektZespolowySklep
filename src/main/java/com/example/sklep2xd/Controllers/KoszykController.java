package com.example.sklep2xd.Controllers;

import com.example.sklep2xd.Dto.KoszykDto;
import com.example.sklep2xd.Models.*;
import com.example.sklep2xd.Repositories.*;
import com.example.sklep2xd.Security.CustomUserDetails;
import com.example.sklep2xd.Service.KoszykService;
import com.example.sklep2xd.Service.ProduktZamowienieService;
import com.example.sklep2xd.Service.ZamowienieService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/koszyk")
public class KoszykController {
    private final KoszykService koszykService;
    private final ZamowienieService zamowienieService;
    private final ProduktZamowienieService produktZamowienieService; //te dwa potrzeben do tworzenia zamowienia na podstawie koszyka
    @Autowired
    private KlientRep klientRep; // Inject KlientRep repository

    @Autowired
    private ProduktRep produktRep; // Inject ProduktRep repository
    @Autowired
    private ZamowienieRep zamowienieRep;
    @Autowired
    private AdresRep adresRep;
    @Autowired
    private ProduktZamowienieRep produktZamowienieRep;

    @Autowired
    public KoszykController(KoszykService koszykService, ZamowienieService zamowienieService, ProduktZamowienieService produktZamowienieService){
        this.koszykService = koszykService;
        this.zamowienieService = zamowienieService;
        this.produktZamowienieService = produktZamowienieService;
    }
    //odnieść się do *komentarza* pod konstruktorem ProduktZamowienieController

//    @GetMapping("/{idKlienta}")
//    public String koszykKlienta(@PathVariable("idKlienta") int idKlienta, Model model){
//        List<KoszykDto> koszyk = koszykService.findKoszykByKlientId(idKlienta);
//        model.addAttribute("header", "Twój koszyk");
//        model.addAttribute("Koszyk", koszyk);
//        return "Koszyk";
//    }
        @GetMapping("/{idKlienta}")
        public String koszykKlienta(@PathVariable("idKlienta") int idKlienta, Model model){
            List<KoszykDto> koszyk = koszykService.findKoszykByKlientId(idKlienta);
            double sumaCen = KoszykService.obliczCeneKoszyka(koszyk);
            model.addAttribute("header", "Twój koszyk");
            model.addAttribute("Koszyk", koszyk);
            model.addAttribute("sumaCen", sumaCen);
            return "Koszyk";
        }


//    @GetMapping
//    public String koszykKlienta(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        int idKlienta = userDetails.getId(); // Pobierz ID zalogowanego użytkownika
//
//        List<KoszykDto> koszyk = koszykService.findKoszykByKlientId(idKlienta);
//        double sumaCen = KoszykService.obliczCeneKoszyka(koszyk);
//        model.addAttribute("header", "Twój koszyk");
//        model.addAttribute("Koszyk", koszyk);
//        model.addAttribute("sumaCen", sumaCen);
//        return "Koszyk";
//    }


//    @PostMapping("/dodajDoKoszyka/{idKlienta}/{idProduktu}/{ilosc}") //ma być wywoływane na stronach z produktami
//    public String dodajDoKoszyka(@PathVariable("idKlienta") int idKlienta,
//                                 @PathVariable("idProduktu") int idProduktu,
//                                 @PathVariable("ilosc") int ilosc, Model model){
//        KlientEntity klient = klientRep.findByIdKlienta(idKlienta);
//        ProduktEntity produkt = produktRep.findByIdProduktu(idProduktu);
//        model.addAttribute("Koszyk1",produkt);
//        KoszykPK id = new KoszykPK(idKlienta, idProduktu);
//        KoszykEntity koszykEntity = new KoszykEntity();
//        koszykEntity.setKpk(id);
//        koszykEntity.setIlosc(ilosc);
//        koszykEntity.setKlient(klient);
//        koszykEntity.setProdukt(produkt);
//        koszykService.saveKoszyk(koszykEntity);
//        return "Koszyk";
//    }

    @PostMapping("/dodajDoKoszyka/{idKlienta}/{idProduktu}/{ilosc}")
    public String dodajDoKoszyka(@PathVariable("idKlienta") int idKlienta,
                                 @PathVariable("idProduktu") int idProduktu,
                                 @PathVariable("ilosc") int ilosc,
                                 Model model) {
        KlientEntity klient = klientRep.findByIdKlienta(idKlienta);
        ProduktEntity produkt = produktRep.findByIdProduktu(idProduktu);
        KoszykPK id = new KoszykPK(idKlienta, idProduktu);
        KoszykEntity koszykEntity = new KoszykEntity();
        koszykEntity.setKpk(id);
        koszykEntity.setIlosc(ilosc);
        koszykEntity.setKlient(klient);
        koszykEntity.setProdukt(produkt);
        koszykService.saveKoszyk(koszykEntity);
        return "redirect:/koszyk/1" ;  // Redirect to the cart view
    }

//    @PostMapping("/dodajDoKoszyka")
//    public String dodajDoKoszyka(@RequestParam("idKlienta") int idKlienta,
//                                 @RequestParam("idProduktu") int idProduktu,
//                                 Model model) {
//        // Tutaj dodaj logikę dodawania produktu do koszyka
//        return "redirect:/lista-produktow"; // Przekierowanie do listy produktów lub innego widoku
//    }


    @PostMapping("/usun/{idk}/{idp}")
    public String usunZKoszyka(@PathVariable("idk") int idKlienta, @PathVariable("idp") int idProduktu, RedirectAttributes redirectAttributes) {
        koszykService.deleteKoszyk(idKlienta, idProduktu);
        redirectAttributes.addFlashAttribute("successMessage", "Produkt został usunięty z koszyka.");
        return "redirect:/koszyk/" + idKlienta;
    }

    @DeleteMapping("/{idKlienta}")
    public String wyczyscKosszykKlienta(@PathVariable("idKlienta") int idKlienta){
        koszykService.deleteKoszykKlienta(idKlienta);
        return "koszyk";
    }
//    @PostMapping("/{idKlienta}/zlozzamowienie") //dalej trzeba obczaić jak uzyskać Id klienta (może z JWT?)
//    @Transactional
////    public String zlozZamowienie(@PathVariable("idKlienta") int idKlienta){
//    public String zlozZamowienie(Model model) {  // Usunąłem tutaj {int idKlienta} z parametrów
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        int idKlienta = userDetails.getId(); // Pobierz ID zalogowanego użytkownika
//
//        //Uzyskaj potrzebne dane klienta
//        KlientEntity klient = klientRep.findByIdKlienta(idKlienta);
////        System.out.println("Dane klienta" + klient.getImie() +" "+ klient.getAdresId().getIdAdresu());
//        //utwórz nowe zamówienie dla id klienta
//        ZamowienieEntity zamowienie = new ZamowienieEntity();
//        int idZamowienia = zamowienie.getIdZamowienia(); //przypisuje 0 - tego nie chcemy - a może jendak nie w tym problem bo id prawidlowo przypisuje później chyba
////        System.out.println("Zamowenie nr"+idZamowienia);
//        zamowienie.setCzyZaplacone(false);
//        zamowienie.setStatus("Złożone");
//        zamowienie.setKlientByKlientId(klientRep.findByIdKlienta(idKlienta));
//
//        //Kopia adresu klienta aby dodać adres wysyłki
//        AdresEntity adresKlienta = adresRep.findByIdAdresu(klient.getAdresId().getIdAdresu());
//        AdresEntity adresZamowienia = new AdresEntity();
//        adresZamowienia.setKraj(adresKlienta.getKraj());
//        adresZamowienia.setMiejscowosc(adresKlienta.getMiejscowosc());
//        adresZamowienia.setKodPocztowy(adresKlienta.getKodPocztowy());
//        adresZamowienia.setUlica(adresKlienta.getUlica());
//        adresZamowienia.setNrDomu(adresKlienta.getNrDomu());
//        if(adresKlienta.getNrMieszkania()!=null)
//            adresZamowienia.setNrMieszkania(adresKlienta.getNrMieszkania());
//        adresRep.save(adresZamowienia);
//
//        //i przypisujemy adres do zamowienia
//        zamowienie.setAdresByAdresId(adresZamowienia);
//        zamowienieRep.save(zamowienie);
//
//        //pętlą przejdź przez rekordy koszyka dla idklienta i iteracyjnie wypełnij koszykZamowienie
//        List<KoszykDto> koszyk = koszykService.findKoszykByKlientId(idKlienta);
//        for (KoszykDto koszykDto : koszyk) {
//            ProduktZamowienieEntity produktZamowienie = new ProduktZamowienieEntity();
//            ProduktZamowieniePK id = new ProduktZamowieniePK(zamowienie.getIdZamowienia(), koszykDto.getProdukt().getIdProduktu());
//
//            produktZamowienie.setIdpz(id);
//            produktZamowienie.setProduktByProduktId(koszykDto.getProdukt());
//            produktZamowienie.setZamowienieByZamowienieId(zamowienie);
//            produktZamowienie.setIlosc(koszykDto.getIlosc());
//
//            produktZamowienieRep.save(produktZamowienie); // Ensure this is called after all fields are set
//        }
//
//        return "PodsumowanieZamowienia"; //widok do zrobienia
//    }

    @PostMapping("/1/zlozZamowienie")
    @Transactional
    public String zlozZamowienie(Model model) {
        int idKlienta = 1; // Statyczne ID klienta

        //Uzyskaj potrzebne dane klienta
        KlientEntity klient = klientRep.findByIdKlienta(idKlienta);
        ZamowienieEntity zamowienie = new ZamowienieEntity();
        zamowienie.setCzyZaplacone(false);
        zamowienie.setStatus("Złożone");
        zamowienie.setKlientByKlientId(klientRep.findByIdKlienta(idKlienta));

        //Kopia adresu klienta aby dodać adres wysyłki
        AdresEntity adresKlienta = adresRep.findByIdAdresu(klient.getAdresId().getIdAdresu());
        AdresEntity adresZamowienia = new AdresEntity();
        adresZamowienia.setKraj(adresKlienta.getKraj());
        adresZamowienia.setMiejscowosc(adresKlienta.getMiejscowosc());
        adresZamowienia.setKodPocztowy(adresKlienta.getKodPocztowy());
        adresZamowienia.setUlica(adresKlienta.getUlica());
        adresZamowienia.setNrDomu(adresKlienta.getNrDomu());
        if (adresKlienta.getNrMieszkania() != null)
            adresZamowienia.setNrMieszkania(adresKlienta.getNrMieszkania());
        adresRep.save(adresZamowienia);

        zamowienie.setAdresByAdresId(adresZamowienia);
        zamowienieRep.save(zamowienie);

        List<KoszykDto> koszyk = koszykService.findKoszykByKlientId(idKlienta);
        for (KoszykDto koszykDto : koszyk) {
            ProduktZamowienieEntity produktZamowienie = new ProduktZamowienieEntity();
            ProduktZamowieniePK id = new ProduktZamowieniePK(zamowienie.getIdZamowienia(), koszykDto.getProdukt().getIdProduktu());

            produktZamowienie.setIdpz(id);
            produktZamowienie.setProduktByProduktId(koszykDto.getProdukt());
            produktZamowienie.setZamowienieByZamowienieId(zamowienie);
            produktZamowienie.setIlosc(koszykDto.getIlosc());

            produktZamowienieRep.save(produktZamowienie);
        }

        return "redirect:/DaneDostawy"; // Przekierowanie do strony DaneDostawy
    }



}

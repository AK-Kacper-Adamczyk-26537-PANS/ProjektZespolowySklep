package com.example.sklep2xd.Service;

import com.example.sklep2xd.Dto.ProduktZamowienieDto;
import com.example.sklep2xd.Models.ProduktZamowienieEntity;
import java.util.List;

public interface ProduktZamowienieService {
    List<ProduktZamowienieDto> findAllProduktZamowienia();
    List<ProduktZamowienieDto> findProduktZamowieniaByIdZamowienia(int zamowienieId);
    ProduktZamowienieEntity saveProduktZamowienie(ProduktZamowienieEntity produktZamowienie);
    void updateProduktZamowienie(ProduktZamowienieDto produktZamowienieDto);
}

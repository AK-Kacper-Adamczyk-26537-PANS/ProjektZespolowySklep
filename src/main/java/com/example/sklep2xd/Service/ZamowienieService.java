package com.example.sklep2xd.Service;

import com.example.sklep2xd.Dto.AdresDto;
import com.example.sklep2xd.Dto.ZamowienieDto;
import com.example.sklep2xd.Models.ProduktEntity;
import com.example.sklep2xd.Models.ZamowienieEntity;

import java.util.List;

public interface ZamowienieService {

    List<ZamowienieDto> findAllZamowienia();
    ZamowienieDto findZamowienieById(int id);
    ZamowienieEntity saveZamowienie(ZamowienieEntity zamowienie);
    void updateZamowienie(ZamowienieDto zamowienieDto);

    List<ProduktEntity> findProduktyByZamowienieId(int zamowienieId);
}

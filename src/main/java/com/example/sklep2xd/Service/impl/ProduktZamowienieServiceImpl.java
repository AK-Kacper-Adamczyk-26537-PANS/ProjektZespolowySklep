package com.example.sklep2xd.Service.impl;

import com.example.sklep2xd.Dto.ProduktZamowienieDto;
import com.example.sklep2xd.Models.ProduktZamowienieEntity;
import com.example.sklep2xd.Repositories.ProduktZamowienieRep;
import com.example.sklep2xd.Service.ProduktZamowienieService;
import com.example.sklep2xd.Models.ZamowienieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProduktZamowienieServiceImpl implements ProduktZamowienieService {

    private final ProduktZamowienieRep produktZamowienieRep;

    @Autowired
    public ProduktZamowienieServiceImpl(ProduktZamowienieRep produktZamowienieRep) {
        this.produktZamowienieRep = produktZamowienieRep;
    }

    @Override
    public List<ProduktZamowienieDto> findAllProduktZamowienia() {
        List<ProduktZamowienieEntity> produktZamowienia = produktZamowienieRep.findAll();
        return produktZamowienia.stream().map(this::mapToProduktZamowienieDto).collect(Collectors.toList());
    }

    @Override
    public List<ProduktZamowienieDto> findProduktZamowieniaByIdZamowienia(int zamowienieId) {
        ZamowienieEntity zamowienie = new ZamowienieEntity();
        zamowienie.setIdZamowienia(zamowienieId);
        List<ProduktZamowienieEntity> produktZamowienia = produktZamowienieRep.findByZamowienie(zamowienie);
        return produktZamowienia.stream().map(this::mapToProduktZamowienieDto).collect(Collectors.toList());
    }

    @Override
    public ProduktZamowienieEntity saveProduktZamowienie(ProduktZamowienieEntity produktZamowienie) {
        return produktZamowienieRep.save(produktZamowienie);
    }

    @Override
    public void updateProduktZamowienie(ProduktZamowienieDto produktZamowienieDto) {
        ProduktZamowienieEntity produktZamowienie = produktZamowienieRep.findById(produktZamowienieDto.getIdpz())
                .orElseThrow(() -> new RuntimeException("ProduktZamowienie not found"));
        produktZamowienie.setIlosc(produktZamowienieDto.getIlosc());
        produktZamowienieRep.save(produktZamowienie);
    }

    private ProduktZamowienieDto mapToProduktZamowienieDto(ProduktZamowienieEntity produktZamowienie) {
        return ProduktZamowienieDto.builder()
                .idpz(produktZamowienie.getIdpz())
                .produkt(produktZamowienie.getProduktByProduktId())
                .zamowienie(produktZamowienie.getZamowienieByZamowienieId())
                .ilosc(produktZamowienie.getIlosc())
                .build();
    }
}

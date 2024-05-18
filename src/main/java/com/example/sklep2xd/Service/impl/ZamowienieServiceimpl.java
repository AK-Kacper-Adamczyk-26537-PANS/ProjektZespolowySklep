package com.example.sklep2xd.Service.impl;

import com.example.sklep2xd.Dto.ZamowienieDto;
import com.example.sklep2xd.Models.ProduktEntity;
import com.example.sklep2xd.Models.ZamowienieEntity;
import com.example.sklep2xd.Repositories.ZamowienieRep;
import com.example.sklep2xd.Service.ZamowienieService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ZamowienieServiceimpl implements ZamowienieService {

    private final ZamowienieRep zamowienieRep;

    @Autowired
    public ZamowienieServiceimpl(ZamowienieRep zamowienieRep){
        this.zamowienieRep = zamowienieRep;
    }

    private ZamowienieDto mapTozamowienieDto(ZamowienieEntity zamowienie){
        return ZamowienieDto.builder()
                .idZamowienia(zamowienie.getIdZamowienia())
                .dataZlozenia(zamowienie.getDataZlozenia())
                .wartoscZamowienia(zamowienie.getWartoscZamowienia())
                .czyZaplacone(zamowienie.getCzyZaplacone())
                .status(zamowienie.getStatus())
                .adres(zamowienie.getAdresByAdresId())
                .klient(zamowienie.getKlientByKlientId())
                .pracownik(zamowienie.getPracownikByPracownikId())
                .build();
    }

    @Override
    public List<ZamowienieDto> findAllZamowienia() {
        List<ZamowienieEntity> zamowienia = zamowienieRep.findAll();
        return zamowienia.stream().map(this::mapTozamowienieDto).collect(Collectors.toList());
    }

    @Override
    public ZamowienieDto findZamowienieById(int id) {
        Optional<ZamowienieEntity> optionalZamowienie = zamowienieRep.findById(id);
        if (optionalZamowienie.isPresent()) {
            ZamowienieEntity zamowienie = optionalZamowienie.get();
            return mapTozamowienieDto(zamowienie);
        } else {
            throw new EntityNotFoundException("Zamówienie o podanym identyfikatorze nie istnieje: " + id);
        }
    }

    @Override
    public ZamowienieEntity saveZamowienie(ZamowienieEntity zamowienie) {
        return zamowienieRep.save(zamowienie);
    }

    @Override
    public void updateZamowienie(ZamowienieDto zamowienieDto) {
        ZamowienieEntity zamowienie = zamowienieRep.findById(zamowienieDto.getIdZamowienia())
                .orElseThrow(() -> new EntityNotFoundException("Zamówienie o podanym identyfikatorze nie istnieje: " + zamowienieDto.getIdZamowienia()));
        zamowienie.setStatus(zamowienieDto.getStatus());
        zamowienieRep.save(zamowienie);
    }

    @Override
    public List<ProduktEntity> findProduktyByZamowienieId(int zamowienieId) {
        ZamowienieEntity zamowienie = zamowienieRep.findById(zamowienieId)
                .orElseThrow(() -> new EntityNotFoundException("Zamówienie o podanym identyfikatorze nie istnieje: " + zamowienieId));
        return zamowienie.getProdukty();
    }

    public ZamowienieEntity dodajZamowienie(ZamowienieEntity zamowienie) {
        return zamowienieRep.save(zamowienie);
    }
}

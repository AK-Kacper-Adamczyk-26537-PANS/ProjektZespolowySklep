package com.example.sklep2xd.Service.impl;

import com.example.sklep2xd.Dto.KoszykDto;
import com.example.sklep2xd.Models.KoszykEntity;
import com.example.sklep2xd.Repositories.KoszykRep;
import com.example.sklep2xd.Service.KoszykService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class KoszykServiceimpl implements KoszykService{
    private KoszykRep koszykRep;
    @Autowired
    public KoszykServiceimpl(KoszykRep koszykRep){
        this.koszykRep = koszykRep;
    }
    private KoszykDto mapToDto(KoszykEntity koszykEntity){
        KoszykDto koszykDto = KoszykDto.builder()
                .ilosc(koszykEntity.getIlosc())
                .produkt(koszykEntity.getProdukt())
                .klient(koszykEntity.getKlient())
                .build();
        return koszykDto;
    }
    //Service i kontroler będą działać podobnie do tych od ProduktZamowienie, z tą różnicą
    //że na podstawie koszyka robimy zawartość zamówienia, ale przy tworzeniu jakoś trzeba będzie odnieść
    // się do Id nowego zamówienia - patrz na komentarze pod saveProduktZamowienie w ProduktZamowienieServiceimpl

    @Override
    public List<KoszykDto> findAllKoszyki() {
        return null;
    }

    @Override
    public List<KoszykDto> findKoszykByKlientId(int idKlienta) {
        return null;
    } //trzeba przerobić aby zwrócić listę rekordów jakoś zamiast jednego rekordu bo
    // tabela dla jednego zamówienia ma wiele rekordów

    @Override
    public KoszykEntity saveKoszyk(KoszykEntity koszykEntity) {
        return koszykRep.save(koszykEntity);
    } //

    @Override
    public void updateKoszyk(KoszykEntity koszykEntity) {

    }
}

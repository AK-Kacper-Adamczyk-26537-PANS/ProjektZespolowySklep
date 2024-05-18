package com.example.sklep2xd.Repositories;

import com.example.sklep2xd.Models.ProduktZamowienieEntity;
import com.example.sklep2xd.Models.ProduktZamowieniePK;
import com.example.sklep2xd.Models.ZamowienieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduktZamowienieRep extends JpaRepository<ProduktZamowienieEntity, ProduktZamowieniePK> {
        List<ProduktZamowienieEntity> findByZamowienie(ZamowienieEntity zamowienie);
}

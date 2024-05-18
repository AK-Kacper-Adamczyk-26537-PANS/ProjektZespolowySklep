package com.example.sklep2xd.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "zamowienie", schema = "public", catalog = "Sklep")
public class ZamowienieEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_zamowienia", nullable = false)
    private int idZamowienia;
    @Basic
    @Column(name = "data_zlozenia", nullable = true)
    private Date dataZlozenia;
    @Basic
    @Column(name = "wartosc_zamowienia", nullable = true, precision = 0)
    private Double wartoscZamowienia;
    @Basic
    @Column(name = "czy_zaplacone", nullable = true)
    private Boolean czyZaplacone;
    @Basic
    @Column(name = "status", nullable = true, length = 100)
    private String status;
//    @Basic
//    @Column(name = "adres_id", nullable = true)
//    private Integer adresId;
//    @Basic
//    @Column(name = "klient_id", nullable = true)
//    private Integer klientId;
//    @Basic
//    @Column(name = "pracownik_id", nullable = true)
//    private Integer pracownikId;
    @ManyToOne
    @JoinColumn(name = "adres_id", referencedColumnName = "id_adresu")
    private AdresEntity adres;
    @ManyToOne
    @JoinColumn(name = "klient_id", referencedColumnName = "id_klienta")
    private KlientEntity klient;
    @ManyToOne
    @JoinColumn(name = "pracownik_id", referencedColumnName = "id_pracownika")
    private PracownikEntity pracownik;


    @ManyToMany
    @JoinTable(
            name = "zamowienie_produkt",
            joinColumns = @JoinColumn(name = "zamowienie_id"),
            inverseJoinColumns = @JoinColumn(name = "produkt_id")
    )
    private List<ProduktEntity> produkty;





    public int getIdZamowienia() {
        return idZamowienia;
    }

    public void setIdZamowienia(int idZamowienia) {
        this.idZamowienia = idZamowienia;
    }

    public Date getDataZlozenia() {
        return dataZlozenia;
    }

    public void setDataZlozenia(Date dataZlozenia) {
        this.dataZlozenia = dataZlozenia;
    }

    public Double getWartoscZamowienia() {
        return wartoscZamowienia;
    }

    public void setWartoscZamowienia(Double wartoscZamowienia) {
        this.wartoscZamowienia = wartoscZamowienia;
    }

    public Boolean getCzyZaplacone() {
        return czyZaplacone;
    }

    public void setCzyZaplacone(Boolean czyZaplacone) {
        this.czyZaplacone = czyZaplacone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public AdresEntity getAdresByAdresId() {
        return adres;
    }

    public void setAdresByAdresId(AdresEntity adresByAdresId) {
        this.adres = adresByAdresId;
    }

    public KlientEntity getKlientByKlientId() {
        return klient;
    }

    public void setKlientByKlientId(KlientEntity klientByKlientId) {
        this.klient = klientByKlientId;
    }

    public PracownikEntity getPracownikByPracownikId() {
        return pracownik;
    }

    public void setPracownikByPracownikId(PracownikEntity pracownikByPracownikId) {
        this.pracownik = pracownikByPracownikId;
    }


    public List<ProduktEntity> getProdukty() {
        return produkty;
    }

    public void setProdukty(List<ProduktEntity> produkty) {
        this.produkty = produkty;
    }
}

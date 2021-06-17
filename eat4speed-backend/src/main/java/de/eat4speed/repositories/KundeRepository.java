package de.eat4speed.repositories;

import de.eat4speed.entities.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class KundeRepository implements PanacheRepository<Kunde> {

    @Transactional
    public void addKunde(Kunde kunde) {
        persist(kunde);
    }

    @Transactional
    public Kunde getKundeByBenutzerID(Integer benutzer_ID) {
        return find("Benutzer_ID", benutzer_ID).firstResult();
    }

    @Transactional
    public Kunde getKundeByKundenId(Integer kundenId) {
        return find("Kundennummer", kundenId).firstResult();
    }

    @Transactional
    public void updateKundeEinstellungen(Kunde kunde) {
        update("name = ?1, vorname = ?2 where kundennummer = ?3", kunde.getName(), kunde.getVorname(), kunde.getKundennummer());
    }

}
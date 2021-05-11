package de.eat4speed.repositories;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Auftrag;
import de.eat4speed.entities.Benutzer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class AdressenRepository implements PanacheRepository<Adressen> {

    @Transactional
    public void addAdresse(Adressen adresse)
    {
        persist(adresse);
    }

    @Transactional
    public void updateAdresse(Adressen adresse) {
        update("strasse = ?1, ort = ?2, postleitzahl = ?3, hausnummer = ?4 where adress_id = ?5", adresse.getStrasse(), adresse.getOrt(), adresse.getPostleitzahl(), adresse.getHausnummer(), adresse.getAdress_ID());
    }

}

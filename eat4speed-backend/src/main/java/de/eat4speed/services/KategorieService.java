package de.eat4speed.services;

import de.eat4speed.dto.CategorieMapper;
import de.eat4speed.dto.CategoriePreferences;
import de.eat4speed.entities.Auftrag;
import de.eat4speed.entities.Bestellung;
import de.eat4speed.entities.Gericht_Kategorie;
import de.eat4speed.entities.Kategorie;
import de.eat4speed.repositories.AuftragRepository;
import de.eat4speed.repositories.BestellungRepository;
import de.eat4speed.repositories.Gericht_KategorieRepository;
import de.eat4speed.repositories.KategorieRepository;
import de.eat4speed.services.interfaces.IKategorieService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.*;

@ApplicationScoped
public class KategorieService implements IKategorieService {

    private KategorieRepository _kategorie;
    private AuftragRepository _auftrag;
    private BestellungRepository _bestellung;
    private Gericht_KategorieRepository _gericht_kategorie;


    @Inject
    public KategorieService(KategorieRepository kategorie,
                            AuftragRepository auftragRepository,
                            BestellungRepository bestellungRepository,
                            Gericht_KategorieRepository gericht_kategorieRepository
    ) {
        _kategorie = kategorie;
        _auftrag = auftragRepository;
        _bestellung = bestellungRepository;
        _gericht_kategorie = gericht_kategorieRepository;

    }

    @Override
    public Response addKategorie(Kategorie kategorie) {
        _kategorie.addKategorie(kategorie);

        return Response.status(Response.Status.CREATED).entity(kategorie).build();
    }

    @Override
    public List listAll() {
        return _kategorie.listAllKategories();
    }

    /**
     * Gets the most bought Category for a given customer
     *
     * @param customerId
     * @return
     */
    @Override
    public CategoriePreferences getFavoriteCategorie(int customerId) {

        List<Auftrag> auftraege = null;
        List<Bestellung> bestellungen = null;
        HashMap<String, Integer> dataSet = new HashMap<String, Integer>();

        try {
            auftraege = _auftrag.getAllAuftraegeByKunde(customerId);
        } catch (Exception e) {
            System.out.println("@CatPref Failed to get Aufträge: " + e);
        }

        if (auftraege != null) {
            for (Auftrag auftrag : auftraege) {
                bestellungen = new ArrayList<Bestellung>();
                try {
                    bestellungen = _bestellung.getAllBestellungenByAuftragsId(auftrag.getAuftrags_ID());
                } catch (Exception e) {
                    System.out.println("@CatPref Failed to get Bestellung: " + e);
                }
                for (Bestellung bestellung : bestellungen) {
                    try {
                        String idsString = bestellung.getGericht_IDs();
                        idsString = idsString.replaceAll("[\\[\\]\\(\\)]", "");
                        String[] ids = idsString.split("\\,");

                        for (String id : ids) {
                            Gericht_Kategorie cat = _gericht_kategorie.getGericht_KategorieById(Long.parseLong(id.trim()));
                            if (dataSet.containsKey(cat.getKategorie())) {
                                int value = dataSet.get(cat.getKategorie());
                                dataSet.replace(cat.getKategorie(), ++value);
                            } else {
                                dataSet.put(cat.getKategorie(), 1);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("@CatPref ID JSON was poorly formed or DB query is meesed up: " + e);
                    }
                }
            }
        }

        CategoriePreferences prefs = new CategoriePreferences();
        Iterator it = dataSet.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            prefs.AddToList(pair.getKey().toString(), (Integer) pair.getValue());
        }

        return prefs;
    }
}

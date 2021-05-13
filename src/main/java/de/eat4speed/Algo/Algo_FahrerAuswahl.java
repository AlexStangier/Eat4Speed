package de.eat4speed.Algo;

import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrtenplan_Station;
import de.eat4speed.entities.Fahrzeug;
import de.eat4speed.entities.Schicht;
import de.eat4speed.repositories.FahrerRepository;
import de.eat4speed.repositories.FahrtenplanRepository;
import de.eat4speed.repositories.FahrzeugRepository;
import de.eat4speed.repositories.SchichtRepository;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class Algo_FahrerAuswahl {

    private SchichtRepository schichtRepository = new SchichtRepository();
    private FahrerRepository fahrerRepository = new FahrerRepository();
    private FahrtenplanRepository fahrtenplanRepository = new FahrtenplanRepository();
    private FahrzeugRepository fahrzeugRepository = new FahrzeugRepository();

    private Fahrtenplan_Station startPunkt;
    private final int sekunden = 30;
    private final int maxFahrer = 10;
    private int anzahlGerichte;

    // Sende Auftrag an besten Fahrer, falls nach 30 sekunden nicht angenommen
    // sende zusätzlich an nächstbesten fahrer falls keine fahrer mehr da oder 5min vergangen sind
    // -> neustart
    public void Fahrtenvergabe(int startPunktID) {

        List<Fahrer> naheFahrer = null;
        int count = 0;
        startPunkt = fahrtenplanRepository.findByStationsID(startPunktID);

        // TODO anzahlGerichte berechnen
        anzahlGerichte = 1;

        boolean isRunning = true;

        //fahrernummer 7  4  2  3  8  6  9  5 10 11
        //fahrer      f6 f3 f1 f2 f7 f5 f8 f4 f9 f10
        while(isRunning) {

            // start
            if (count == 0)
            {
                naheFahrer = getNaheFahrer();
                System.out.println("size: " + naheFahrer.size());
            }

            // sende alle 30 sek an nächstbesten Fahrer
            if (naheFahrer.size() > 0 && !AuftragAngenommen(startPunktID))
            {
                if (naheFahrer.size() > count && count < maxFahrer)
                {
                    // wenn noch an unter maxFahrer gesendet
                    System.out.println(count + " Schicke an Fahrer mit ID: "+naheFahrer.get(count).getFahrernummer());
                    Schicht schicht = schichtRepository.getSchichtHeute(naheFahrer.get(count).getFahrernummer());
                    sende_Auftrag_an_Fahrer(naheFahrer.get(count).getFahrernummer());
                    count++;
                }
                else
                {
                    // wenn Liste durch und nicht angenommen
                    // anfragen entfernen und neustart
                    for (int i = 0; i < count; i++)
                    {
                        entferne_Auftrag_von_Fahrer(naheFahrer.get(i).getFahrernummer());
                    }
                    System.out.println("NEUSTART");
                    count = 0;
                }
            }
            else if (AuftragAngenommen(startPunktID))
            {
                // evtl anfragen entfernen
                isRunning = false;
            }
            else if (naheFahrer.size() == 0)
            {
                System.out.println("No Found -> Restarting in 30s");
            }
            System.out.flush();
            try
            {
                Thread.sleep(sekunden * 1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean AuftragAngenommen(int id)
    {
        boolean retVal = false;

        // TODO testen

        return retVal;
    }

    public void sende_Auftrag_an_Fahrer(int fahrerID)
    {
        // TODO
    }

    public void entferne_Auftrag_von_Fahrer(int fahrerID)
    {
        // TODO
    }

    public List<Fahrer> getNaheFahrer()
    {
        List<Fahrer> fahrer = fahrerRepository.getEveryVerifiedFahrer();

        System.out.println(fahrer.size());

        // sortieren nach nähe zum Startpunkt der Stationen
        fahrer.sort(new SortByDistanz(startPunkt));

        for (int i = 0; i < fahrer.size(); i++)
        {
            if (!CheckFahrerAvailability(fahrer.get(i))) {
                fahrer.remove(fahrer.get(i));
                i--;
            }
        }

        return fahrer;
    }

    public boolean CheckFahrerAvailability(Fahrer fahrer)
    {
        boolean isAvailable = false;

        if (fahrer.getAnzahl_Aktueller_Auftraege() < 1 && fahrer.getIst_in_Pause() == 0)
        {
            Schicht schicht = schichtRepository.getSchichtHeute(fahrer.getFahrernummer());
            Fahrzeug fahrzeug = fahrzeugRepository.findByFahrzeugID(fahrer.getFahrzeug());

            Timestamp time = new Timestamp(new Date().getTime()
                    + (startPunkt.getGeschaetzte_Fahrtzeit() * 60L * 1000L));

            if (schicht.getEnde().after(time) && fahrzeug.getKapazitaet_Gerichte() >= anzahlGerichte)
            {
                isAvailable = true;
            }
        }
        return isAvailable;
    }
}

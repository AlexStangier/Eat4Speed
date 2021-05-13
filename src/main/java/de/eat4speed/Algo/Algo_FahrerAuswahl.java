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
import java.util.Timer;
import java.util.TimerTask;

@ApplicationScoped
public class Algo_FahrerAuswahl {

    private SchichtRepository schichtRepository = new SchichtRepository();
    private FahrerRepository fahrerRepository = new FahrerRepository();
    private FahrtenplanRepository fahrtenplanRepository = new FahrtenplanRepository();
    private FahrzeugRepository fahrzeugRepository = new FahrzeugRepository();

    private static List<Fahrer> naheFahrer;
    private Fahrtenplan_Station startPunkt;
    private int count = 0;
    private final int sekunden = 30;
    private final int maxFahrer = 10;
    private int anzahlGerichte;
    private boolean isRunning = false;
    private Timer timer;

    public void Fahrtenvergabe(int startPunktID) {
        startPunkt = fahrtenplanRepository.findByStationsID(startPunktID);

        // TODO anzahlGerichte berechnen
        anzahlGerichte = 1;

        // Sende Auftrag an besten Fahrer
        // falls nach 30 sekunden nicht angenommen
        // sende zusätzlich an nächstbesten fahrer
        // falls keine fahrer mehr da oder 5min vergangen sind
        // neustart

        isRunning = true;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                System.out.println("TEst1");
                // start / neustart
                if (count == 0)
                {
                    naheFahrer = getNaheFahrer();
                }
                // sende alle 30 sek an nächstbesten Fahrer
                if (naheFahrer.size() > 0 && AuftragAngenommen(startPunktID) == false)
                {
                    if (naheFahrer.size() > count && count < maxFahrer)
                    {
                        // wenn noch an unter maxFahrer gesendet
                        System.out.println("TEst2");
                        sende_Auftrag_an_Fahrer(naheFahrer.get(count).getFahrernummer());
                        count++;
                        System.out.println("Suche...");
                    }
                    else
                    {
                        // wenn Liste durch und nicht angenommen
                        // anfragen entfernen und neustart
                        for (int i = 0; i < count; i++)
                        {
                            entferne_Auftrag_von_Fahrer(naheFahrer.get(i).getFahrernummer());
                            count = 0;
                        }
                    }
                }
                else if (AuftragAngenommen(startPunktID) == true)
                {
                    // evtl anfragen entfernen
                    timer.cancel();
                    isRunning = false;
                }
            }
        }, 0, sekunden * 1000);

        while(isRunning)
        {
            try
            {
                System.out.println("TEst3");
                this.wait(1 * 1000);
            }
            catch(InterruptedException e)
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

        // sortieren nach nähe zum Startpunkt der Stationen
        fahrer.sort(new SortByDistanz(startPunkt));

        for (Fahrer f : fahrer)
        {
            if (CheckFahrerAvailability(f) == false)
            {
                fahrer.remove(f);
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

            Timestamp time = new Timestamp(new Date().getTime());

            if (schicht.getEnde().getTime() > ( time.getTime() + startPunkt.getGeschaetzte_Fahrtzeit( ) ) && fahrzeug.getKapazitaet_Gerichte() > anzahlGerichte)
            {
                isAvailable = true;
            }
        }
        return isAvailable;
    }
}

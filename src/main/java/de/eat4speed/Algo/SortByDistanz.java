package de.eat4speed.Algo;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrtenplan_Station;
import de.eat4speed.repositories.AdressenRepository;

import java.util.Comparator;

public class SortByDistanz implements Comparator<Fahrer> {

    private Adressen startAdresse;

    private AdressenRepository adressenRepository;

    SortByDistanz(Fahrtenplan_Station startPunktAuftrag)
    {
        this.adressenRepository = new AdressenRepository();
        this.startAdresse = adressenRepository.getAdresseByID(startPunktAuftrag.getLiefer_Abholadresse());
    }

    @Override
    public int compare(Fahrer f1, Fahrer f2) {

        Adressen Fahrer1_Adresse = adressenRepository.getAdresseByID(f1.getAktueller_Standort());
        Adressen Fahrer2_Adresse = adressenRepository.getAdresseByID(f2.getAktueller_Standort());

        double F1_Lat = Double.parseDouble(Fahrer1_Adresse.getLat());
        double F1_Lng = Double.parseDouble(Fahrer1_Adresse.getLng());
        double F2_Lat = Double.parseDouble(Fahrer2_Adresse.getLat());
        double F2_Lng = Double.parseDouble(Fahrer2_Adresse.getLng());

        double Start_Lat = Double.parseDouble(startAdresse.getLat());
        double Start_Lng = Double.parseDouble(startAdresse.getLng());

        return Double.compare(distance(F1_Lat, Start_Lat, F1_Lng, Start_Lng, 0, 0),
                distance(F2_Lat, Start_Lat, F2_Lng, Start_Lng, 0, 0));
    }

    public static double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2)
    {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}

package de.eat4speed.services;

import de.eat4speed.dishAlternatives.DishAlternativesOptions;
import de.eat4speed.entities.Gericht;
import de.eat4speed.multipart.MultipartBody;
import de.eat4speed.repositories.GerichtRepository;
import de.eat4speed.services.interfaces.IGerichtService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class GerichtService implements IGerichtService {

    private GerichtRepository gerichtRepository;

    @Inject
    public GerichtService(GerichtRepository gericht) {
        this.gerichtRepository = gericht;
    }

    @Override
    public Response addGericht(Gericht gericht)
    {
        gerichtRepository.addGericht(gericht);

        return Response.status(Response.Status.CREATED).entity(gericht).build();
    }

    @Override
    public Gericht getGerichtByGerichtID(int gericht_ID)
    {
        return gerichtRepository.getGerichtByGerichtID(gericht_ID);
    }

    @Override
    public List getAllGerichteDataRestaurantSpeisekarte(int restaurant_ID)
    {
        return gerichtRepository.getAllGerichteDataRestaurantSpeiseKarte(restaurant_ID);
    }

    @Override
    public List getAllGetraenkeDataRestaurantSpeiseKarte(int restaurant_ID)
    {
        return gerichtRepository.getAllGetraenkeDataRestaurantSpeiseKarte(restaurant_ID);
    }
    @Override
    public List getGerichtDataByGerichtName(String gerichtName)
    {
        return gerichtRepository.getGerichtDataByGerichtName(gerichtName);
    }

    @Override
    public List getGerichtDataByGerichtKategorie(String kategorie)
    {
        return gerichtRepository.getGerichtDataByGerichtKategorie(kategorie);
    }

    @Override
    public List getGerichtDataByRestaurant_ID(int restaurant_ID)
    {
        return gerichtRepository.getGerichtDataByRestaurant_ID(restaurant_ID);
    }

    @Override
    public List getGerichtDataByGericht_ID(int gericht_ID)
    {
        return gerichtRepository.getGerichtDataByGericht_ID(gericht_ID);
    }

    @Override
    public Response updateGerichtAllData(Gericht gericht)
    {
        gerichtRepository.updateGerichtAllData(gericht);

        return Response.status(Response.Status.OK).entity(gericht).build();
    }

    @Override
    public List getGerichtAlternatives(DishAlternativesOptions options)
    {
        List<Integer> alternative_IDs = new ArrayList<>();
        List<String> nameParts;

        Integer excluded_ID = options.getGericht_ID();

        System.out.println("Alternativen: ");

        if(options.isUseName())
        {
            nameParts = Arrays.asList(options.getGerichtName().split(" "));
            System.out.println("Name Parts: "+ nameParts);

            for (int i = 0; i < nameParts.size(); i++)
            {
                List<Integer> alternative_IDsByName = gerichtRepository.getGericht_IDsByGerichtName(nameParts.get(i));

                for (int e = 0; e < alternative_IDsByName.size(); e++)
                {
                    if(!alternative_IDs.contains(alternative_IDsByName.get(e)))
                    {
                        alternative_IDs.add(alternative_IDsByName.get(e));
                        System.out.println(alternative_IDsByName.get(e).toString());
                    }
                }
            }
        }
        if(options.isUseKategorien())
        {
            System.out.println(options.getKategorien().toString());
            for(int i = 0; i < options.getKategorien().size(); i++)
            {
                List<Integer> alternative_IDsByKategorie = gerichtRepository.getGericht_IDsByGerichtKategorie(options.getKategorien().get(i));

                for (int e = 0; e < alternative_IDsByKategorie.size(); e++)
                {
                    if(!alternative_IDs.contains(alternative_IDsByKategorie.get(e)))
                    {
                        alternative_IDs.add(alternative_IDsByKategorie.get(e));
                        System.out.println(alternative_IDsByKategorie.get(e).toString());
                    }
                }
            }
        }
        List gerichtAlternativen = null;

        alternative_IDs.remove(excluded_ID);

        if(alternative_IDs.size()>0)
        {
            gerichtAlternativen = gerichtRepository.getGerichtDataByGericht_ID(alternative_IDs.get(0));

            if(alternative_IDs.size()>=1)
            {
                for(int i = 1; i < alternative_IDs.size(); i++)
                {
                    List gericht = gerichtRepository.getGerichtDataByGericht_ID(alternative_IDs.get(i));

                    gerichtAlternativen.add(gericht.get(0));
                }
            }

        }

        return gerichtAlternativen;
    }

    @Override
    public List getGerichtDataByKundennummer_Favoriten(int kundennummer){
        return gerichtRepository.getGerichtDataByKundennummer_Favoriten(kundennummer);
    }

    @Override
    public void updatePicturePath(String path, int id)
    {
        gerichtRepository.updatePicturePath(path, id);
    }

    @Override
    public Response deleteGericht(int gericht_ID)
    {
        gerichtRepository.deleteGericht(gericht_ID);

        return Response.status(Response.Status.OK).build();
    }

}

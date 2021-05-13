package de.eat4speed.services;

import de.eat4speed.dishAlternatives.DishAlternativesOptions;
import de.eat4speed.entities.Gericht;
import de.eat4speed.multipart.MultipartBody;
import de.eat4speed.repositories.GerichtRepository;
import de.eat4speed.services.interfaces.IGerichtService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
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
    public List searchGerichte(DishAlternativesOptions options){
        List<Integer> search_IDs = new ArrayList<>();
        List<String> nameParts;

        Integer excluded_ID = options.getGericht_ID();

        System.out.println("Alternativen: ");

        if(options.isUseName())
        {
            nameParts = Arrays.asList(options.getGerichtName().split(" "));
            System.out.println("Name Parts: "+ nameParts);

            List<Integer> search_IDsByName = gerichtRepository.getGericht_IDsByGerichtName(options.getGerichtName());

            for (int e = 0; e < search_IDsByName.size(); e++)
            {
                search_IDs.add(search_IDsByName.get(e));
                System.out.println(search_IDsByName.get(e).toString());
            }

            for (int i = 0; i < nameParts.size(); i++)
            {
                List<Integer> search_IDsByNamePart = gerichtRepository.getGericht_IDsByGerichtName(nameParts.get(i));

                for (int e = 0; e < search_IDsByNamePart.size(); e++)
                {
                    if(!search_IDs.contains(search_IDsByNamePart.get(e)))
                    {
                        search_IDs.add(search_IDsByNamePart.get(e));
                        System.out.println(search_IDsByNamePart.get(e).toString());
                    }
                }
            }
        }
        if(options.isUseKategorien())
        {
            System.out.println(options.getKategorien().toString());
            for(int i = 0; i < options.getKategorien().size(); i++)
            {
                List<Integer> gericht_IDsByKategorie = gerichtRepository.getGericht_IDsByGerichtKategorie(options.getKategorien().get(i));

                for (int e = 0; e < gericht_IDsByKategorie.size(); e++)
                {
                    if(!search_IDs.contains(gericht_IDsByKategorie.get(e)))
                    {
                        search_IDs.add(gericht_IDsByKategorie.get(e));
                        System.out.println(gericht_IDsByKategorie.get(e).toString());
                    }
                }
            }
        }

        List<Integer> gericht_IDsByAllergeneAll = null;

        if(options.isUseAllergene())
        {
            for(int i = 0; i < options.getExcludedAllergene().size(); i++)
            {
                List<Integer> gericht_IDsByAllergene = gerichtRepository.getGericht_IDsByGerichtAllergene(options.getExcludedAllergene().get(i));

                if(i==0)
                {
                    gericht_IDsByAllergeneAll = gericht_IDsByAllergene;
                }

                for (int e = 0; e < gericht_IDsByAllergene.size(); e++)
                {
                    if(!gericht_IDsByAllergeneAll.contains(gericht_IDsByAllergene.get(e)))
                    {
                        gericht_IDsByAllergeneAll.add(gericht_IDsByAllergene.get(e));
                        System.out.println(gericht_IDsByAllergene.get(e).toString());
                    }
                }
            }
            for(int i = 0; i < search_IDs.size(); i++)
            {
                if(gericht_IDsByAllergeneAll.contains(search_IDs.get(i)))
                {
                    search_IDs.remove(search_IDs.get(i));
                }
            }

        }

        List<Integer> gericht_IDsByMindestbestellwertAll = null;

        if(options.isUseMindestbestellwert())
        {
            gericht_IDsByMindestbestellwertAll = gerichtRepository.getGericht_IDsByMindestbestellwertMax(options.getMaxMindestbestellwert());

            for(int i = 0; i < search_IDs.size(); i++)
            {
                if(gericht_IDsByMindestbestellwertAll.contains(search_IDs.get(i)))
                {
                    search_IDs.remove(search_IDs.get(i));
                }
            }
        }

        List<Integer> gericht_IDsByBewertungAll = null;

        if(options.isUseBewertung())
        {
            gericht_IDsByBewertungAll = gerichtRepository.getGericht_IDsByBewertung(options.getMinBewertung());

            for(int i = 0; i < search_IDs.size(); i++)
            {
                if(gericht_IDsByBewertungAll.contains(search_IDs.get(i)))
                {
                    search_IDs.remove(search_IDs.get(i));
                }
            }
        }
        //todo Entfernungen
        List<Integer> gericht_IDsByEntfernungAll = null;

        List foundGerichte = null;

        if(options.getGericht_ID()>=0)
        {
            search_IDs.remove(excluded_ID);
        }

        if(search_IDs.size()>0)
        {
            foundGerichte = gerichtRepository.getGerichtDataByGericht_ID(search_IDs.get(0));

            if(search_IDs.size()>=1)
            {
                for(int i = 1; i < search_IDs.size(); i++)
                {
                    List gericht = gerichtRepository.getGerichtDataByGericht_ID(search_IDs.get(i));

                    foundGerichte.add(gericht.get(0));
                }
            }

        }

        return foundGerichte;
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

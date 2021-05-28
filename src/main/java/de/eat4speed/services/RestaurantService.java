package de.eat4speed.services;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Restaurant;
import de.eat4speed.entities.Fahrzeug;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.RestaurantRepository;
import de.eat4speed.searchOptions.RestaurantSearchOptions;
import de.eat4speed.services.interfaces.IRestaurantService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class RestaurantService implements IRestaurantService {

    private RestaurantRepository _restaurant;

    @Inject
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this._restaurant = restaurantRepository;
    }

    @Override
    public Response addRestaurant(Restaurant restaurant) {
        _restaurant.addRestaurant(restaurant);

        return Response.status(Response.Status.CREATED).entity(restaurant).build();
    }

    @Override
    public List getAllRestaurant() {
        return _restaurant.getAllRestaurant();
    }

    @Override
    public List getNotVerifiedRestaurant() {
        return _restaurant.getNotVerifiedRestaurant();
    }

    @Override
    public List getVerifiedRestaurant() {
        return _restaurant.getVerifiedRestaurant();
    }

    @Override
    public List getRestaurantDataByRestaurantName(String restaurantName) {
        return _restaurant.getRestaurantDataByRestaurantName(restaurantName);
    }

    @Override
    public List getAllRestaurantDataByRestaurant_ID(int restaurant_ID) {
        return _restaurant.getAllRestaurantDataByRestaurant_ID(restaurant_ID);
    }

    @Override
    public List searchRestaurants(RestaurantSearchOptions options) {
        List<Integer> search_IDs = new ArrayList<>();
        List<String> nameParts;

        System.out.println("Alternativen: ");

        if(options.isUseName())
        {
            nameParts = Arrays.asList(options.getRestaurantName().split(" "));
            System.out.println("Name Parts: "+ nameParts);

            List<Integer> search_IDsByName = _restaurant.getRestaurant_IDsByRestaurantName(options.getRestaurantName());

            for (int e = 0; e < search_IDsByName.size(); e++)
            {
                search_IDs.add(search_IDsByName.get(e));
                System.out.println(search_IDsByName.get(e).toString());
            }

            for (int i = 0; i < nameParts.size(); i++)
            {
                List<Integer> search_IDsByNamePart = _restaurant.getRestaurant_IDsByRestaurantName(nameParts.get(i));

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

        List<Integer> restaurant_IDsByMindestbestellwertAll = null;

        if(options.isUseMindestbestellwert())
        {
            restaurant_IDsByMindestbestellwertAll = _restaurant.getRestaurant_IDsByMindestbestellwertMax(options.getMaxMindestbestellwert());

            for(int i = 0; i < search_IDs.size(); i++)
            {
                if(restaurant_IDsByMindestbestellwertAll.contains(search_IDs.get(i)))
                {
                    search_IDs.remove(search_IDs.get(i));
                    i--;
                }
            }
        }

        List<Integer> restaurant_IDsByBewertungAll = null;

        if(options.isUseBewertung())
        {
            restaurant_IDsByBewertungAll = _restaurant.getRestaurant_IDsByBewertung(options.getMinBewertung());

            for(int i = 0; i < search_IDs.size(); i++)
            {
                if(!restaurant_IDsByBewertungAll.contains(search_IDs.get(i)))
                {
                    search_IDs.remove(search_IDs.get(i));
                    i--;
                }
            }
        }
        List<Integer> restaurant_IDsByEntfernungAll = null;

        System.out.println(search_IDs);
        if(options.isUseEntfernung())
        {
            restaurant_IDsByEntfernungAll = _restaurant.getRestaurant_IDsByDistance(options.getKundennummer(), options.getMaxEntfernung());

            System.out.println("Entfernung:");
            System.out.println(restaurant_IDsByEntfernungAll);

            for(int i = 0; i < search_IDs.size(); i++)
            {
                System.out.println(search_IDs.get(i));
                if(restaurant_IDsByEntfernungAll.contains(search_IDs.get(i)))
                {
                    search_IDs.remove(search_IDs.get(i));
                    i--;
                }
            }
        }
        System.out.println(search_IDs);

        List foundRestaurants = null;

        if(search_IDs.size()>0)
        {
            foundRestaurants = _restaurant.getAllRestaurantDataByRestaurant_ID(search_IDs.get(0));

            if(search_IDs.size()>=1)
            {
                for(int i = 1; i < search_IDs.size(); i++)
                {
                    List restaurant = _restaurant.getAllRestaurantDataByRestaurant_ID(search_IDs.get(i));

                    foundRestaurants.add(restaurant.get(0));
                }
            }

        }

        return foundRestaurants;
    }

    @Override
    public Response updateRestaurant_Verifiziert(int id) {
        _restaurant.updateRestaurant_Verifiziert(id);

        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response deleteRestaurant(int id) {
        _restaurant.deleteRestaurant(id);

        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response updateRestaurantStammdaten(Restaurant restaurant) {
        _restaurant.updateRestaurantStammdaten(restaurant);
        return Response.status(Response.Status.OK).entity(restaurant).build();
    }

}

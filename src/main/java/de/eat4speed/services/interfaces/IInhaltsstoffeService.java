package de.eat4speed.services.interfaces;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IInhaltsstoffeService
{
    Response addInhaltsstoffe(Inhaltsstoffe obj);
    List listAll();

}

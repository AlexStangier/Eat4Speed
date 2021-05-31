package de.eat4speed.services;

import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.*;
import de.eat4speed.dto.PaypalDto;
import de.eat4speed.entities.Auftrag;
import de.eat4speed.entities.Bestellung;
import de.eat4speed.entities.Rechnung;
import de.eat4speed.paypal.Credentials;
import de.eat4speed.repositories.AuftragRepository;
import de.eat4speed.repositories.BestellungRepository;
import de.eat4speed.repositories.RechnungRepository;
import de.eat4speed.services.interfaces.IPaypalService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PaypalService implements IPaypalService {

    private AuftragRepository _auftragRepository;
    private RechnungRepository _rechnungRepository;
    private BestellungRepository _bestellungRepository;

    @Inject
    public PaypalService(AuftragRepository auftragRepository,
                         RechnungRepository rechnungRepository,
                         BestellungRepository bestellungRepository) {
        _auftragRepository = auftragRepository;
        _rechnungRepository = rechnungRepository;
        _bestellungRepository = bestellungRepository;
    }

    @Override
    public PaypalDto checkOutViaPaypal(long auftragId) {

        //retrieve auftrag from database
        Auftrag auftrag = _auftragRepository.getAuftragByID((int) auftragId);
        Bestellung bestellung = null;
        Order order = null;
        PaypalDto ppDto = new PaypalDto();
        List<String> resultLinks = new ArrayList<>();

        if (auftrag != null) {
            bestellung = _bestellungRepository.getBestellungByID((int) auftrag.getAuftrags_ID());
            if (bestellung != null) {
                Rechnung rechnung = _rechnungRepository.getRechnungByID(bestellung.getRechnung());

                OrderRequest orderRequest = new OrderRequest();
                orderRequest.checkoutPaymentIntent("CAPTURE");
                List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>();
                purchaseUnits.add(new PurchaseUnitRequest().amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode("EUR").value(String.valueOf(Math.floor(rechnung.getBetrag() * 100) / 100))));
                orderRequest.purchaseUnits(purchaseUnits);
                OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);

                try {
                    HttpResponse<Order> response = Credentials.client.execute(request);
                    order = response.result();
                    ppDto.setOrderId(order.id());
                    order.links().forEach(link -> resultLinks.add(link.href()));
                    ppDto.setSelfLink(resultLinks.get(0));
                    ppDto.setApproveLink(resultLinks.get(1));
                    ppDto.setUpdateLink(resultLinks.get(2));
                    ppDto.setCaptureLink(resultLinks.get(3));
                    ppDto.setBestellungId(bestellung.getBestell_ID());
                } catch (IOException ioe) {
                    if (ioe instanceof HttpException) {
                        // Something went wrong server-side
                        HttpException he = (HttpException) ioe;
                        System.out.println(he.getMessage());
                        he.headers().forEach(x -> System.out.println(x + " :" + he.headers().header(x)));
                    } else {
                        System.out.println("Client fucked up payment!!1111!!!111!");
                    }
                }
            } else {
                return new PaypalDto();
            }

        }
        return ppDto;
    }
}

package org.example.Client;

import com.soap.ws.client.generated.CalculatriceService;
import com.soap.ws.client.generated.CalculatriceServiceService;

public class CalculatriceClient {
    public static void main(String[] args) {
        CalculatriceServiceService service = new CalculatriceServiceService();
        CalculatriceService serv = service.getCalculatriceServicePort();

        double result = serv.calculateMensualite(100, 100, 100);
        System.out.println("result: " + result);
    }
}

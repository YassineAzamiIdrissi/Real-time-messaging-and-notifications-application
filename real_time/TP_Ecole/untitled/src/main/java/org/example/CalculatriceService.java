package org.example;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public class CalculatriceService {
    @WebMethod
    public double calculateMensualite(double capital,
                                      double tauxAnnuel,
                                      int dureeMois) {
        double tauxMensuel = tauxAnnuel / 12 / 100;
        return (capital * tauxMensuel) / (1 - Math.pow(1 + tauxMensuel, -dureeMois));
    }
}

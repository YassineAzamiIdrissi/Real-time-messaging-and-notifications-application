package org.example;

import jakarta.xml.ws.Endpoint;

public class CalculatriceServicePublicateur {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/LoanCalculatorService", new CalculatriceService());
        System.out.println("servixz  publié en http://localhost:8080/LoanCalculatorService?wsdl");
    }
}

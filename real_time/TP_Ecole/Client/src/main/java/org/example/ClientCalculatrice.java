package org.example;

public class ClientCalculatrice
{
    public static void main(String[] args) {
        try {
            Calculatrice service = new LoanCalculatorService();

            LoanCalculator loanCalculator = service.getLoanCalculatorPort();

            double capital = 100000;
            double tauxAnnuel = 2;
            int dureeMois = 60;

            double mensualite = loanCalculator.(capital, tauxAnnuel, dureeMois);
            System.out.println("La mensualité calculée est : " + mensualite);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

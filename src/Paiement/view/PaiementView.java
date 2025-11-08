package Paiement.view;

import Paiement.controller.PaiementController;
import java.util.Scanner;

public class PaiementView {
    private PaiementController controller = new PaiementController();

    public void afficherMenuPaiement(int commandeId, double montant, int userId) {
        Scanner sc = new Scanner(System.in);
        int choix;

        do {
            System.out.println("\n=== GESTION DU PAIEMENT ===");
            System.out.println("1. Effectuer un paiement");
            System.out.println("2. Voir historique des paiements");
            System.out.println("0. Retour");
            System.out.print("Choix : ");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1 -> {
                    System.out.println("Méthode de paiement :");
                    System.out.println("1. En ligne");
                    System.out.println("2. À la récupération");
                    int m = sc.nextInt();
                    String methode = (m == 1) ? "en ligne" : "à la récupération";
                    controller.effectuerPaiement(commandeId, montant, methode);
                }
                case 2 -> controller.afficherHistorique(userId);
                case 0 -> System.out.println("Retour au menu principal...");
                default -> System.out.println("Choix invalide !");
            }

        } while (choix != 0);
    }
}


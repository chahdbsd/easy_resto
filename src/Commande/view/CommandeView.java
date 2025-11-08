package Commande.view;

import Commande.controller.CommandeController;
import Commande.dao.PanierDAO;
import Commande.dao.IPanierDAO;
import java.util.Scanner;

public class CommandeView {
    private CommandeController controller = new CommandeController();
    private IPanierDAO panierDAO = new PanierDAO();

    public void menuCommande(int userId) {
        Scanner sc = new Scanner(System.in);
        int choix;
        do {
            System.out.println("\n=== GESTION COMMANDE ===");
            System.out.println("1. Voir panier");
            System.out.println("2. Passer commande");
            System.out.println("3. Voir historique");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");
            choix = sc.nextInt();

            switch (choix) {
                case 1 -> panierDAO.voirPanier(userId).forEach(System.out::println);
                case 2 -> {
                    System.out.println("Paiement en ligne ? (1: oui, 0: non)");
                    boolean enLigne = sc.nextInt() == 1;
                    controller.passerCommande(userId, enLigne);
                }
                case 3 -> controller.voirHistorique(userId);
                case 0 -> System.out.println("Retour menu principal...");
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 0);
    }
}

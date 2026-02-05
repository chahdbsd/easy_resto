package Commande.view;

import Commande.controller.CommandeController;
import Commande.dao.IPanierDAO;
import Commande.dao.PanierDAO;
import java.util.Scanner;

public class CommandeView {
    private final CommandeController controller;
    private final IPanierDAO panierDAO = new PanierDAO();
    private final Scanner sc = new Scanner(System.in);

    // ✅ Nouveau constructeur exigé
    public CommandeView(CommandeController controller) {
        this.controller = controller;
    }

    public void menuCommande(int userId) {
        int choix;
        do {
            System.out.println("\n=== GESTION COMMANDE ===");
            System.out.println("1. Voir panier");
            System.out.println("2. Passer commande");
            System.out.println("3. Voir historique");
            System.out.println("0. Retour");
            System.out.print("Choix : ");
            choix = sc.nextInt(); sc.nextLine();

            switch (choix) {
                case 1 -> panierDAO.voirPanier(userId).forEach(System.out::println);
                case 2 -> {
                    System.out.print("Paiement en ligne ? (1: oui, 0: non) : ");
                    boolean enLigne = (sc.nextInt() == 1); sc.nextLine();
                    controller.passerCommandeAvecDetails(userId, enLigne);
                }
                case 3 -> controller.voirHistorique(userId);
                case 0 -> System.out.println("Retour au menu principal...");
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 0);
    }
}

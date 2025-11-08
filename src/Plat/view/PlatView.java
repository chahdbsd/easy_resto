package Plat.view;

import Plat.controller.PlatController;
import Plat.model.Plat;
import Commande.dao.IPanierDAO;
import Commande.dao.PanierDAO;
import java.util.Scanner;

public class PlatView {
    private PlatController controller = new PlatController();
    private IPanierDAO panierDAO = new PanierDAO();

    public void afficherMenuPrincipal(int userId) {
        Scanner sc = new Scanner(System.in);
        int choix;

        do {
            System.out.println("\n=== MENU DU RESTAURANT ===");
            System.out.println("1. Afficher tous les plats");
            System.out.println("2. Filtrer par catégorie");
            System.out.println("3. Ajouter un plat au panier");
            System.out.println("0. Retour");
            System.out.print("Choix : ");
            choix = sc.nextInt();
            sc.nextLine(); // flush

            switch (choix) {
                case 1 -> controller.afficherMenu();
                case 2 -> {
                    System.out.print("Entrez une catégorie (ex: Pizza, Dessert...) : ");
                    String cat = sc.nextLine();
                    controller.filtrerParCategorie(cat);
                }
                case 3 -> {
                    System.out.print("ID du plat à ajouter : ");
                    int id = sc.nextInt();
                    System.out.print("Quantité : ");
                    int qte = sc.nextInt();

                    Plat plat = controller.obtenirPlatParId(id);
                    if (plat != null) {
                        panierDAO.ajouterAuPanier(userId, plat.getId(), qte);
                        System.out.println("✅ Plat ajouté au panier !");
                    } else {
                        System.out.println("❌ Plat introuvable !");
                    }
                }
                case 0 -> System.out.println("Retour au menu principal...");
                default -> System.out.println("Choix invalide !");
            }

        } while (choix != 0);
    }
}

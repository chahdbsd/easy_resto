package Admin.view;

import Admin.controller.AdminController;

import java.util.List;
import java.util.Scanner;

public class AdminView {
    private final AdminController ctrl = new AdminController();
    private final Scanner sc = new Scanner(System.in);

    public void menuAdmin(int userId) {
        if (!ctrl.isUserAdmin(userId)) {
            System.out.println("⛔ Accès refusé : vous n'êtes pas admin.");
            return;
        }
        while (true) {
            System.out.println("\n=== ADMIN ===");
            System.out.println("1) Utilisateurs");
            System.out.println("2) Plats & Menu");
            System.out.println("3) Commandes");
            System.out.println("4) Paiements");
            System.out.println("5) Support / Messages");
            System.out.println("0) Retour");
            System.out.print("Choix : ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1" -> menuUsers();
                case "2" -> menuPlats();
                case "3" -> menuCommandes();
                case "4" -> menuPaiements();
                case "5" -> menuMessages();
                case "0" -> { return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }


    private void menuUsers() {
        while (true) {
            System.out.println("\n=== ADMIN > Utilisateurs ===");
            System.out.println("1) Lister");
            System.out.println("2) Créer");
            System.out.println("3) Modifier email");
            System.out.println("4) Modifier mot de passe");
            System.out.println("5) Set admin ON/OFF");
            System.out.println("6) Supprimer");
            System.out.println("0) Retour");
            System.out.print("Choix : ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> printList(ctrl.listUsers());
                case "2" -> {
                    System.out.print("Nom: "); String nom = sc.nextLine();
                    System.out.print("Prénom: "); String prenom = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.print("Mot de passe: "); String pwd = sc.nextLine();
                    System.out.print("Admin ? (1=oui/0=non): "); boolean isAdmin = "1".equals(sc.nextLine().trim());
                    int id = ctrl.createUser(nom, prenom, email, pwd, isAdmin);
                    System.out.println(id>0 ? "✅ Créé id="+id : "❌ Échec création.");
                }
                case "3" -> {
                    int id = askInt("ID utilisateur: ");
                    System.out.print("Nouvel email: "); String em = sc.nextLine();
                    ctrl.updateUserEmail(id, em); System.out.println("✅ Email modifié.");
                }
                case "4" -> {
                    int id = askInt("ID utilisateur: ");
                    System.out.print("Nouveau mot de passe: "); String pwd = sc.nextLine();
                    ctrl.updateUserPassword(id, pwd); System.out.println("✅ Mot de passe modifié.");
                }
                case "5" -> {
                    int id = askInt("ID utilisateur: ");
                    System.out.print("Admin ? (1=ON/0=OFF): "); boolean a = "1".equals(sc.nextLine().trim());
                    ctrl.setAdmin(id, a); System.out.println("✅ is_admin = " + a);
                }
                case "6" -> {
                    int id = askInt("ID utilisateur: ");
                    ctrl.deleteUser(id); System.out.println("🗑️ Utilisateur supprimé.");
                }
                case "0" -> { return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }


    private void menuPlats() {
        while (true) {
            System.out.println("\n=== ADMIN > Plats & Menu ===");
            System.out.println("1) Lister");
            System.out.println("2) Ajouter");
            System.out.println("3) Modifier prix");
            System.out.println("4) Modifier description");
            System.out.println("5) Activer/Désactiver (disponible)");
            System.out.println("0) Retour");
            System.out.print("Choix : ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> printList(ctrl.listPlats());
                case "2" -> {
                    System.out.print("Nom: "); String nom = sc.nextLine();
                    System.out.print("Description: "); String desc = sc.nextLine();
                    double prix = askDouble("Prix: ");
                    System.out.print("Catégorie: "); String cat = sc.nextLine();
                    System.out.print("Disponible ? (1=oui/0=non): "); boolean disp = "1".equals(sc.nextLine().trim());
                    int id = ctrl.createPlat(nom, desc, prix, cat, disp);
                    System.out.println(id>0 ? "✅ Plat créé id="+id : "❌ Échec création.");
                }
                case "3" -> {
                    int id = askInt("ID plat: "); double prix = askDouble("Nouveau prix: ");
                    ctrl.updatePlatPrice(id, prix); System.out.println("✅ Prix mis à jour.");
                }
                case "4" -> {
                    int id = askInt("ID plat: "); System.out.print("Nouvelle description: "); String d = sc.nextLine();
                    ctrl.updatePlatDesc(id, d); System.out.println("✅ Description mise à jour.");
                }
                case "5" -> {
                    int id = askInt("ID plat: ");
                    System.out.print("Disponible ? (1=oui/0=non): "); boolean disp = "1".equals(sc.nextLine().trim());
                    ctrl.setPlatDisponible(id, disp); System.out.println("✅ disponible="+disp);
                }
                case "0" -> { return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }


    private void menuCommandes() {
        while (true) {
            System.out.println("\n=== ADMIN > Commandes ===");
            System.out.println("1) Toutes");
            System.out.println("2) Filtrer par statut (En attente / Payée / En préparation / Livrée / Annulée)");
            System.out.println("3) Changer le statut d’une commande");
            System.out.println("0) Retour");
            System.out.print("Choix : ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> printList(ctrl.listCommandes(null));
                case "2" -> {
                    System.out.print("Statut: "); String s = sc.nextLine();
                    printList(ctrl.listCommandes(s));
                }
                case "3" -> {
                    int id = askInt("ID commande: ");
                    System.out.print("Nouveau statut (En attente/Payée/En préparation/Livrée/Annulée): ");
                    String s = sc.nextLine();
                    ctrl.updateCommandeStatut(id, s);
                    System.out.println("✅ Statut mis à jour.");
                }
                case "0" -> { return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }


    private void menuPaiements() {
        while (true) {
            System.out.println("\n=== ADMIN > Paiements ===");
            System.out.println("1) Derniers paiements");
            System.out.println("2) Détecter anomalies (montant payé ≠ total commande)");
            System.out.println("0) Retour");
            System.out.print("Choix : ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> {
                    int lim = askInt("Combien (ex: 20) ? ");
                    printList(ctrl.listPaiements(lim));
                }
                case "2" -> printList(ctrl.detecterAnomaliesPaiements());
                case "0" -> { return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }


    private void menuMessages() {
        while (true) {
            System.out.println("\n=== ADMIN > Messages ===");
            System.out.println("1) Voir messages d’un utilisateur");
            System.out.println("2) Supprimer un message");
            System.out.println("0) Retour");
            System.out.print("Choix : ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> {
                    int uid = askInt("ID utilisateur: ");
                    printList(ctrl.listMessagesByUser(uid));
                }
                case "2" -> {
                    int mid = askInt("ID message: ");
                    ctrl.deleteMessage(mid);
                    System.out.println("🗑️ Message supprimé.");
                }
                case "0" -> { return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }


    private void printList(List<String> lines) {
        if (lines == null || lines.isEmpty()) { System.out.println("(vide)"); return; }
        lines.forEach(System.out::println);
    }
    private int askInt(String label) {
        while (true) {
            System.out.print(label);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { System.out.println("Veuillez entrer un entier."); }
        }
    }
    private double askDouble(String label) {
        while (true) {
            System.out.print(label);
            try { return Double.parseDouble(sc.nextLine().trim().replace(',', '.')); }
            catch (Exception e) { System.out.println("Veuillez entrer un nombre."); }
        }
    }
}

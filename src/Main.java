import java.util.Scanner;

// === ÉVÉNEMENTS
import events.EvenementDispatcher;
import events.AjoutEvenement;
import events.SuppressionEvenement;
import events.listeners.NotificationListener;

// === SERVICES
import Commande.service.CommandeService;
import Paiement.service.PaiementService;
import Avis.service.AvisService;
import Message.service.MessageService;

// === VUES / CONTRÔLEURS
import Commande.controller.CommandeController;
import Commande.view.CommandeView;
import Paiement.view.PaiementView;
import Avis.view.AvisView;
import Utilisateur.view.UtilisateurView;
import Plat.view.PlatView;


import Admin.view.AdminView;


import Utilisateur.model.Utilisateur;

public class Main {


    private static final String RESET   = "\u001B[0m";
    private static final String BOLD    = "\u001B[1m";
    private static final String DIM     = "\u001B[2m";
    private static final String RED     = "\u001B[31m";
    private static final String GREEN   = "\u001B[32m";
    private static final String YELLOW  = "\u001B[33m";
    private static final String BLUE    = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String CYAN    = "\u001B[36m";
    private static final String GRAY    = "\u001B[90m";

    private static final String SEP = "─".repeat(42);

    public static void main(String[] args) {


        EvenementDispatcher dispatcher = new EvenementDispatcher();
        NotificationListener log = new NotificationListener();
        dispatcher.subscribe(AjoutEvenement.class, log);
        dispatcher.subscribe(SuppressionEvenement.class, log);


        CommandeService commandeService = new CommandeService(dispatcher);
        PaiementService  paiementService = new PaiementService(dispatcher);
        AvisService      avisService     = new AvisService(dispatcher);
        MessageService   messageService  = new MessageService(dispatcher);


        PaiementView    paiementView    = new PaiementView();
        AvisView        avisView        = new AvisView();
        UtilisateurView utilisateurView = new UtilisateurView();
        PlatView        platView        = new PlatView();

        CommandeController commandeController =
                new CommandeController(commandeService, paiementView, avisView);
        CommandeView cmdView = new CommandeView(commandeController);

        AdminView adminView = new AdminView();


        printHeader();


        Scanner sc = new Scanner(System.in);
        Utilisateur currentUser = null;

        while (true) {
            printMainMenu(currentUser);

            System.out.print(BOLD + CYAN + "👉 Choix : " + RESET);
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "0" -> {
                    printlnBox(GREEN, "À bientôt et bon appétit ! 😋");
                    return;
                }
                case "1" -> {

                    int uid = (currentUser != null) ? currentUser.getId() : -1;
                    platView.afficherMenuPrincipal(uid);
                }
                case "2" -> {
                    currentUser = ensureLogged(utilisateurView, currentUser);
                    if (currentUser == null) break;
                    cmdView.menuCommande(currentUser.getId());
                }
                case "3" -> {
                    currentUser = ensureLogged(utilisateurView, currentUser);
                    if (currentUser == null) break;
                    cmdView.menuCommande(currentUser.getId());
                }
                case "4" -> {
                    currentUser = ensureLogged(utilisateurView, currentUser);
                    if (currentUser == null) break;
                    printlnTitle("Historique des commandes");
                    commandeController.voirHistorique(currentUser.getId());
                    pause(sc);
                }
                case "5" -> {
                    currentUser = ensureLogged(utilisateurView, currentUser);
                    if (currentUser == null) break;

                    System.out.println("\n" + BOLD + BLUE + "=== GESTION DU PAIEMENT ===" + RESET);
                    System.out.println("1) Effectuer un paiement");
                    System.out.println("2) Historique des paiements");
                    System.out.print(CYAN + "Choix : " + RESET);
                    String cp = sc.nextLine().trim();

                    if ("1".equals(cp)) {
                        try {
                            System.out.print("ID commande : ");
                            int cmdId = Integer.parseInt(sc.nextLine().trim());
                            System.out.print("Montant (€) : ");
                            double montant = Double.parseDouble(sc.nextLine().trim());
                            System.out.print("Méthode (en ligne / comptoir) : ");
                            String methode = sc.nextLine().trim();
                            paiementService.simulerPaiement(cmdId, montant, methode);
                        } catch (Exception e) {
                            printlnBox(RED, "Entrée invalide.");
                        }
                    } else if ("2".equals(cp)) {
                        paiementService.afficherHistoriquePaiement(currentUser.getId());
                        pause(sc);
                    }
                }
                case "6" -> {
                    currentUser = ensureLogged(utilisateurView, currentUser);
                    if (currentUser == null) break;

                    System.out.println("\n" + BOLD + BLUE + "=== AVIS ===" + RESET);
                    System.out.println("1) Ajouter un avis");
                    System.out.println("2) Voir avis d’un plat");
                    System.out.print(CYAN + "Choix : " + RESET);
                    String ca = sc.nextLine().trim();

                    if ("1".equals(ca)) {
                        try {
                            System.out.print("ID plat : ");
                            int platId = Integer.parseInt(sc.nextLine().trim());
                            System.out.print("Note (1..5) : ");
                            int note = Integer.parseInt(sc.nextLine().trim());
                            System.out.print("Commentaire : ");
                            String com = sc.nextLine();
                            avisService.ajouter(currentUser.getId(), platId, note, com);
                        } catch (Exception e) {
                            printlnBox(RED, "Entrée invalide.");
                        }
                    } else if ("2".equals(ca)) {
                        try {
                            System.out.print("ID plat : ");
                            int platId = Integer.parseInt(sc.nextLine().trim());
                            avisService.afficherParPlat(platId);
                            pause(sc);
                        } catch (Exception e) {
                            printlnBox(RED, "Entrée invalide.");
                        }
                    }
                }
                case "7" -> {
                    currentUser = ensureLogged(utilisateurView, currentUser);
                    if (currentUser == null) break;

                    System.out.println("\n" + BOLD + BLUE + "=== SUPPORT / MESSAGES ===" + RESET);
                    System.out.println("1) Voir mes messages");
                    System.out.println("2) Envoyer un message");
                    System.out.println("3) Supprimer un message");
                    System.out.print(CYAN + "Choix : " + RESET);
                    String cm = sc.nextLine().trim();

                    if ("1".equals(cm)) {
                        messageService.afficherMesMessages(currentUser.getId());
                        pause(sc);
                    } else if ("2".equals(cm)) {
                        System.out.print("Votre message : ");
                        String msg = sc.nextLine();
                        messageService.envoyer(currentUser.getId(), msg);
                    } else if ("3".equals(cm)) {
                        try {
                            System.out.print("ID du message à supprimer : ");
                            int mid = Integer.parseInt(sc.nextLine().trim());
                            messageService.supprimer(mid);
                        } catch (Exception e) {
                            printlnBox(RED, "Entrée invalide.");
                        }
                    }
                }
                case "8" -> {
                    currentUser = loginFlow(utilisateurView);
                }
                case "9" -> {
                    currentUser = ensureLogged(utilisateurView, currentUser);
                    if (currentUser == null) break;
                    if (!currentUser.isAdmin()) {
                        printlnBox(YELLOW, "⛔ Accès refusé : vous n’êtes pas admin.");
                        break;
                    }
                    adminView.menuAdmin(currentUser.getId());
                }
                default -> printlnBox(RED, "Choix invalide.");
            }
        }
    }



    private static void printHeader() {
        System.out.println("\n" + BOLD + CYAN + "┌" + SEP + "┐" + RESET);
        System.out.println("│" + center(BOLD + "🍽️  RESTO — Application" + RESET, SEP.length()) + " │");
        System.out.println(BOLD + CYAN + "└" + SEP + "┘" + RESET);
    }

    private static void printMainMenu(Utilisateur currentUser) {
        System.out.println("\n" + BOLD + BLUE + "=== MENU PRINCIPAL ===" + RESET);
        System.out.println("1) " + BOLD + "Menu Plats" + RESET + " (afficher / filtrer / rechercher / ajouter)");
        System.out.println("2) Passer une commande " + GRAY  + RESET);
        System.out.println("3) Voir panier " + GRAY  + RESET);
        System.out.println("4) Historique des commandes " + GRAY + RESET);
        System.out.println("5) Paiement " + GRAY  + RESET);
        System.out.println("6) Avis " + GRAY + RESET);
        System.out.println("7) Support / Messages " + GRAY + RESET);
        System.out.println("8) Se connecter / Créer un compte");
        System.out.println("9) Administration " + GRAY + RESET);
        System.out.println("0) Quitter");
        if (currentUser != null) {
            System.out.println(GREEN + "✔ Connecté : " + currentUser.getEmail()
                    + (currentUser.isAdmin() ? " (admin)" : "") + RESET);
        } else {
            System.out.println(YELLOW + "⚠ Non connecté" + RESET);
        }
    }

    private static void printlnTitle(String title) {
        System.out.println("\n" + BOLD + MAGENTA + "— " + title + " —" + RESET);
    }

    private static void printlnBox(String color, String msg) {
        String line = " " + msg + " ";
        String border = "─".repeat(Math.max(6, line.length()));
        System.out.println(color + "┌" + border + "┐" + RESET);
        System.out.println(color + "│" + line + "│" + RESET);
        System.out.println(color + "└" + border + "┘" + RESET);
    }

    private static String center(String s, int width) {
        int len = s.replaceAll("\u001B\\[[;\\d]*m", "").length();
        if (len >= width) return s;
        int left = (width - len) / 2, right = width - len - left;
        return " ".repeat(left) + s + " ".repeat(right);
    }

    private static void pause(Scanner sc) {
        System.out.print(GRAY + DIM + "Appuie sur Entrée pour continuer…" + RESET);
        sc.nextLine();
    }

   

    private static Utilisateur ensureLogged(UtilisateurView utilisateurView, Utilisateur currentUser) {
        if (currentUser != null) return currentUser;
        printlnBox(YELLOW, "Connexion requise.");
        return loginFlow(utilisateurView);
    }

    private static Utilisateur loginFlow(UtilisateurView utilisateurView) {
        try {
            Utilisateur u = utilisateurView.loginOuCreerCompte();
            if (u != null && u.getId() > 0) {
                printlnBox(GREEN, "Connecté (id=" + u.getId() + ", admin=" + u.isAdmin() + ")");
                return u;
            }
            printlnBox(RED, "Échec de connexion / création.");
            return null;
        } catch (Throwable t) {
            printlnBox(RED, "Auth indisponible: " + t.getMessage());
            return null;
        }
    }
}

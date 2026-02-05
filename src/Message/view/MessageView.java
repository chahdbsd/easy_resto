package Message.view;

import Message.controller.MessageController;

import java.util.Scanner;

public class MessageView {
    private final MessageController controller = new MessageController();
    private final Scanner sc = new Scanner(System.in);

    public void menuMessages(int userId) {
        while (true) {
            System.out.println("\n=== SUPPORT / MESSAGES ===");
            System.out.println("1) Voir mes messages");
            System.out.println("2) Envoyer un message");
            System.out.println("3) Supprimer un message");
            System.out.println("0) Retour");
            System.out.print("Choix : ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> controller.afficherMesMessages(userId);
                case "2" -> {
                    System.out.print("Votre message : ");
                    String txt = sc.nextLine();
                    controller.envoyer(userId, txt);
                }
                case "3" -> {
                    System.out.print("ID du message : ");
                    try {
                        int id = Integer.parseInt(sc.nextLine().trim());
                        controller.supprimer(id);
                    } catch (NumberFormatException e) {
                        System.out.println("ID invalide.");
                    }
                }
                case "0" -> { return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }
}

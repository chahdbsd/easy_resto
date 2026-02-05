package Commande.controller;

import Commande.service.ICommandeService;
import Commande.model.CommandePlat;
import Paiement.view.PaiementView;
import Avis.view.AvisView;

import java.util.List;
import java.util.Scanner;

public class CommandeController {

    private final ICommandeService service;
    private final Scanner scanner = new Scanner(System.in);
    private final AvisView avisView;
    private final PaiementView paiementView;

    public CommandeController(ICommandeService service, PaiementView paiementView, AvisView avisView) {
        this.service = service;
        this.paiementView = paiementView;
        this.avisView = avisView;
    }

    public List<CommandePlat> passerCommandeAvecDetails(int userId, boolean paiementEnLigne) {
        System.out.println("\n=== PASSER UNE COMMANDE ===");

        List<CommandePlat> platsCommandes = service.passerCommandeAvecDetails(userId, paiementEnLigne);
        if (platsCommandes.isEmpty()) return platsCommandes;


        int commandeId = platsCommandes.get(0).getPlatId();

        double montantTotal = service.calculerMontantTotalCommandeUtilisateur(userId);
        System.out.println("Montant total : " + montantTotal + "€");

        if (paiementEnLigne) {
            paiementView.afficherMenuPaiement(commandeId, montantTotal, userId);
        } else {
            System.out.print("Souhaitez-vous payer maintenant ? (o/n) : ");
            String choix = scanner.nextLine();
            if (choix.equalsIgnoreCase("o")) {
                paiementView.afficherMenuPaiement(commandeId, montantTotal, userId);
            }
        }

        for (CommandePlat cp : platsCommandes) {
            System.out.println("\nVoulez-vous laisser un avis pour : " + cp.getNom() + " ? (oui/non)");
            String reponse = scanner.nextLine().trim().toLowerCase();
            if (reponse.equals("oui")) {
                avisView.ajouterAvis(userId, cp.getPlatId());
            }
        }

        return platsCommandes;
    }

    public void voirHistorique(int userId) {
        service.voirHistorique(userId);
    }
}

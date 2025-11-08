package Paiement.model;

import java.sql.Timestamp;

public class Paiement {
    private int id;
    private int commandeId;
    private double montant;
    private String methode; // "en ligne" ou "à la récupération"
    private boolean effectue;
    private Timestamp datePaiement;

    public Paiement() {}

    public Paiement(int id, int commandeId, double montant, String methode, boolean effectue, Timestamp datePaiement) {
        this.id = id;
        this.commandeId = commandeId;
        this.montant = montant;
        this.methode = methode;
        this.effectue = effectue;
        this.datePaiement = datePaiement;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCommandeId() { return commandeId; }
    public void setCommandeId(int commandeId) { this.commandeId = commandeId; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getMethode() { return methode; }
    public void setMethode(String methode) { this.methode = methode; }

    public boolean isEffectue() { return effectue; }
    public void setEffectue(boolean effectue) { this.effectue = effectue; }

    public Timestamp getDatePaiement() { return datePaiement; }
    public void setDatePaiement(Timestamp datePaiement) { this.datePaiement = datePaiement; }

    @Override
    public String toString() {
        return "Paiement #" + id +
                " | Commande #" + commandeId +
                " | Montant: " + montant + "€" +
                " | Méthode: " + methode +
                " | Effectué: " + (effectue ? "Oui" : "Non") +
                " | Date: " + datePaiement;
    }
}

package Commande.model;

import java.sql.Timestamp;

public class Commande {
    private int id;
    private int userId;
    private Timestamp dateCommande;
    private String statut; // exemple : "en cours", "terminée"
    private boolean paiementEnLigne;


    public Commande() {}


    public Commande(int id, int userId, Timestamp dateCommande, String statut, boolean paiementEnLigne) {
        this.id = id;
        this.userId = userId;
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.paiementEnLigne = paiementEnLigne;
    }


    public Commande(int id, int userId, Timestamp dateCommande, boolean paiementEnLigne) {
        this.id = id;
        this.userId = userId;
        this.dateCommande = dateCommande;
        this.statut = "en cours"; // valeur par défaut
        this.paiementEnLigne = paiementEnLigne;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getDateCommande() { return dateCommande; }
    public void setDateCommande(Timestamp dateCommande) { this.dateCommande = dateCommande; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public boolean isPaiementEnLigne() { return paiementEnLigne; }
    public void setPaiementEnLigne(boolean paiementEnLigne) { this.paiementEnLigne = paiementEnLigne; }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", userId=" + userId +
                ", dateCommande=" + dateCommande +
                ", statut='" + statut + '\'' +
                ", paiementEnLigne=" + paiementEnLigne +
                '}';
    }
}

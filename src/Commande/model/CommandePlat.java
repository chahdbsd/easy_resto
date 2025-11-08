package Commande.model;

public class CommandePlat {
    private int platId;
    private String nom;
    private int quantite;

    public CommandePlat(int platId, String nom, int quantite) {
        this.platId = platId;
        this.nom = nom;
        this.quantite = quantite;
    }

    public int getPlatId() { return platId; }
    public String getNom() { return nom; }
    public int getQuantite() { return quantite; }

    @Override
    public String toString() {
        return nom + " x" + quantite;
    }
}

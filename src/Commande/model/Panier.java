package Commande.model;

import Plat.model.Plat;

public class Panier {
    private Plat plat;
    private int quantite;

    public Panier(Plat plat, int quantite) {
        this.plat = plat;
        this.quantite = quantite;
    }

    public Plat getPlat() { return plat; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getTotal() {
        return plat.getPrix() * quantite;
    }

    @Override
    public String toString() {
        return plat.getNom() + " x" + quantite + " = " + getTotal() + "€";
    }
}

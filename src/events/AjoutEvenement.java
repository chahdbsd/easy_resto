package events;

public final class AjoutEvenement extends Evenement {
    private final String cible; // ex: "commande#12", "paiement#cmd:5"
    public AjoutEvenement(String cible) { this.cible = cible; }
    public String cible() { return cible; }
    @Override public String type() { return "ajout"; }
}

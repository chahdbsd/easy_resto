package events;

public final class SuppressionEvenement extends Evenement {
    private final String cible;
    public SuppressionEvenement(String cible) { this.cible = cible; }
    public String cible() { return cible; }
    @Override public String type() { return "suppression"; }
}

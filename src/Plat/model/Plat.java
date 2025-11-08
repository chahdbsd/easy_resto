package Plat.model;

public class Plat {
    private int id;
    private String nom;
    private String description;
    private double prix;
    private String categorie;

    public Plat() {}

    public Plat(int id, String nom, String description, double prix, String categorie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.categorie = categorie;
    }
    public Plat(int id, String nom, double prix, String categorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.categorie = categorie;
        this.description = ""; // valeur par défaut
    }

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return id + " | " + nom + " (" + categorie + ") - " + prix + "€\n    " + description;
    }
}

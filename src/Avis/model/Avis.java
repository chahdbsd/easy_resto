package Avis.model;

import java.sql.Timestamp;

public class Avis {
    private int id;
    private int userId;
    private int platId;
    private int note; // 1 à 5
    private String commentaire;
    private Timestamp date;

    public Avis() {}

    public Avis(int id, int userId, int platId, int note, String commentaire, Timestamp date) {
        this.id = id;
        this.userId = userId;
        this.platId = platId;
        this.note = note;
        this.commentaire = commentaire;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getPlatId() { return platId; }
    public void setPlatId(int platId) { this.platId = platId; }

    public int getNote() { return note; }
    public void setNote(int note) { this.note = note; }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }

    @Override
    public String toString() {
        return "Avis #" + id + " | Plat: " + platId + " | Note: " + note + "/5 | " + commentaire;
    }
}

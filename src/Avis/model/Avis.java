package Avis.model;

import java.sql.Timestamp;

public class Avis {
    private int id;
    private int userId;
    private int platId;
    private int note;             // 1..5
    private String commentaire;   // optionnel
    private Timestamp dateAvis;   // peut être null si colonne absente

    public Avis() {}

    // Getters / Setters
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

    public Timestamp getDateAvis() { return dateAvis; }
    public void setDateAvis(Timestamp dateAvis) { this.dateAvis = dateAvis; }

    @Override
    public String toString() {
        String dateTxt = (dateAvis == null) ? "-" : dateAvis.toString();
        return String.format("#%d | user=%d | plat=%d | %d★ | %s | %s",
                id, userId, platId, note, (commentaire == null ? "" : commentaire), dateTxt);
    }
}

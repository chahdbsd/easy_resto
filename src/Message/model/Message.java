package Message.model;

import java.sql.Timestamp;

public class Message {
    private int id;
    private int userId;
    private String contenu;
    private Timestamp dateMessage;

    public Message(int id, int userId, String contenu, Timestamp dateMessage) {
        this.id = id;
        this.userId = userId;
        this.contenu = contenu;
        this.dateMessage = dateMessage;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getContenu() { return contenu; }
    public Timestamp getDateMessage() { return dateMessage; }
}

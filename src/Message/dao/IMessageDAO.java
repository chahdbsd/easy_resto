package Message.dao;

import Message.model.Message;
import java.util.List;

public interface IMessageDAO {
    void envoyer(int userId, String contenu);
    List<Message> listeParUtilisateur(int userId);
    void supprimer(int messageId);
}

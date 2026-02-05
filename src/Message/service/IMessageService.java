package Message.service;

public interface IMessageService {
    void envoyer(int userId, String contenu);
    void afficherMesMessages(int userId);
    void supprimer(int messageId);
}

package Message.controller;

import Message.service.IMessageService;
import Message.service.MessageService;

public class MessageController {
    private final IMessageService service = new MessageService();

    public void envoyer(int userId, String contenu) { service.envoyer(userId, contenu); }
    public void afficherMesMessages(int userId) { service.afficherMesMessages(userId); }
    public void supprimer(int messageId) { service.supprimer(messageId); }
}

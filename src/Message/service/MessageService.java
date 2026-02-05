package Message.service;

import Message.dao.IMessageDAO;
import Message.dao.MessageDAO;
import Message.model.Message;

import java.util.List;


import events.EvenementDispatcher;
import events.AjoutEvenement;
import events.SuppressionEvenement;

public class MessageService implements IMessageService {
    private final IMessageDAO dao = new MessageDAO();


    private final EvenementDispatcher dispatcher;
    public MessageService(EvenementDispatcher dispatcher) { this.dispatcher = dispatcher; }

    public MessageService() { this.dispatcher = null; }

    @Override
    public void envoyer(int userId, String contenu) {
        if (contenu == null || contenu.isBlank()) {
            System.out.println("Contenu vide.");
            return;
        }
        dao.envoyer(userId, contenu.trim());
        System.out.println("✅ Message envoyé.");


        if (dispatcher != null) {
            dispatcher.publish(new AjoutEvenement("message#user:" + userId));
        }
    }

    @Override
    public void afficherMesMessages(int userId) {
        List<Message> list = dao.listeParUtilisateur(userId);
        if (list.isEmpty()) {
            System.out.println("Aucun message.");
            return;
        }
        for (Message m : list) {
            String dateTxt = (m.getDateMessage() == null) ? "-" : m.getDateMessage().toString();
            String txt = (m.getContenu() == null) ? "" : m.getContenu();
            System.out.printf("#%d | %s | %s%n", m.getId(), dateTxt, txt);
        }
    }

    @Override
    public void supprimer(int messageId) {
        dao.supprimer(messageId);
        System.out.println("🗑️ Message supprimé.");


        if (dispatcher != null) {
            dispatcher.publish(new SuppressionEvenement("message#" + messageId));
        }
    }
}

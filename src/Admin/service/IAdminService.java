package Admin.service;

import java.util.List;

public interface IAdminService {
    // Users
    List<String> listUsers();
    int createUser(String nom, String prenom, String email, String password, boolean isAdmin);
    void updateUserEmail(int userId, String newEmail);
    void updateUserPassword(int userId, String newPassword);
    void setAdmin(int userId, boolean isAdmin);
    void deleteUser(int userId);

    // Plats
    List<String> listPlats();
    int createPlat(String nom, String description, double prix, String categorie, boolean disponible);
    void updatePlatPrice(int platId, double prix);
    void updatePlatDesc(int platId, String desc);
    void setPlatDisponible(int platId, boolean disponible);

    // Commandes
    List<String> listCommandes(String statut);
    void updateCommandeStatut(int commandeId, String statut);

    // Paiements
    List<String> listPaiements(int limit);
    List<String> detecterAnomaliesPaiements();

    // Messages
    List<String> listMessagesByUser(int userId);
    void deleteMessage(int messageId);

    boolean isUserAdmin(int userId);
}

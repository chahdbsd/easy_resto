package Admin.controller;

import Admin.service.AdminService;
import Admin.service.IAdminService;

import java.util.List;

public class AdminController {
    private final IAdminService service = new AdminService();

    // Users
    public List<String> listUsers(){ return service.listUsers(); }
    public int createUser(String n,String p,String e,String pwd,boolean a){ return service.createUser(n,p,e,pwd,a); }
    public void updateUserEmail(int id,String em){ service.updateUserEmail(id,em); }
    public void updateUserPassword(int id,String pwd){ service.updateUserPassword(id,pwd); }
    public void setAdmin(int id, boolean a){ service.setAdmin(id,a); }
    public void deleteUser(int id){ service.deleteUser(id); }

    // Plats
    public List<String> listPlats(){ return service.listPlats(); }
    public int createPlat(String n,String d,double prix,String c,boolean disp){ return service.createPlat(n,d,prix,c,disp); }
    public void updatePlatPrice(int id,double prix){ service.updatePlatPrice(id,prix); }
    public void updatePlatDesc(int id,String desc){ service.updatePlatDesc(id,desc); }
    public void setPlatDisponible(int id,boolean disp){ service.setPlatDisponible(id,disp); }

    // Commandes
    public List<String> listCommandes(String statut){ return service.listCommandes(statut); }
    public void updateCommandeStatut(int cmdId,String statut){ service.updateCommandeStatut(cmdId,statut); }

    // Paiements
    public List<String> listPaiements(int limit){ return service.listPaiements(limit); }
    public List<String> detecterAnomaliesPaiements(){ return service.detecterAnomaliesPaiements(); }

    // Messages
    public List<String> listMessagesByUser(int userId){ return service.listMessagesByUser(userId); }
    public void deleteMessage(int messageId){ service.deleteMessage(messageId); }

    public boolean isUserAdmin(int userId){ return service.isUserAdmin(userId); }
}

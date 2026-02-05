package Admin.service;

import Admin.dao.AdminDAO;
import Admin.dao.IAdminDAO;

import java.util.List;

public class AdminService implements IAdminService {
    private final IAdminDAO dao = new AdminDAO();

    // Users
    @Override public List<String> listUsers(){ return dao.listUsers(); }
    @Override public int createUser(String n,String p,String e,String pwd,boolean a){ return dao.createUser(n,p,e,pwd,a); }
    @Override public void updateUserEmail(int id,String em){ dao.updateUserEmail(id,em); }
    @Override public void updateUserPassword(int id,String pwd){ dao.updateUserPassword(id,pwd); }
    @Override public void setAdmin(int id, boolean a){ dao.setAdmin(id,a); }
    @Override public void deleteUser(int id){ dao.deleteUser(id); }

    // Plats
    @Override public List<String> listPlats(){ return dao.listPlats(); }
    @Override public int createPlat(String n,String d,double prix,String c,boolean disp){ return dao.createPlat(n,d,prix,c,disp); }
    @Override public void updatePlatPrice(int id,double prix){ dao.updatePlatPrice(id,prix); }
    @Override public void updatePlatDesc(int id,String desc){ dao.updatePlatDesc(id,desc); }
    @Override public void setPlatDisponible(int id,boolean disp){ dao.setPlatDisponible(id,disp); }

    // Commandes
    @Override public List<String> listCommandes(String statut){ return dao.listCommandes(statut); }
    @Override public void updateCommandeStatut(int cmdId,String statut){ dao.updateCommandeStatut(cmdId,statut); }

    // Paiements
    @Override public List<String> listPaiements(int limit){ return dao.listPaiements(limit); }
    @Override public List<String> detecterAnomaliesPaiements(){ return dao.detecterAnomaliesPaiements(); }

    // Messages
    @Override public List<String> listMessagesByUser(int userId){ return dao.listMessagesByUser(userId); }
    @Override public void deleteMessage(int messageId){ dao.deleteMessage(messageId); }

    @Override public boolean isUserAdmin(int userId){ return dao.isUserAdmin(userId); }
}

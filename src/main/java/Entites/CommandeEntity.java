package Entites;

import java.time.LocalDate;
import java.util.List;



public class CommandeEntity {
    private int id_c;
    private int client_id;
    private String etat;
    private LocalDate date_c;
    private Double total;

    private List<LigneCommandeEntity> lignesCommande;

    public CommandeEntity() {
    }

    public CommandeEntity(int client_id, String etat, LocalDate date_c, Double total, List<LigneCommandeEntity> lignesCommande) {
        this.client_id = client_id;
        this.etat = etat;
        this.date_c = date_c;
        this.total = total;
        this.lignesCommande = lignesCommande;
    }

    public CommandeEntity(int id_c, int client_id, String etat, LocalDate date_c, Double total, List<LigneCommandeEntity> lignesCommande) {
        this.id_c = id_c;
        this.client_id = client_id;
        this.etat = etat;
        this.date_c = date_c;
        this.total = total;
        this.lignesCommande = lignesCommande;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public LocalDate getDate_c() {
        return date_c;
    }

    public void setDate_c(LocalDate date_c) {
        this.date_c = date_c;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public List<LigneCommandeEntity> getLignesCommande() {
        return lignesCommande;
    }

    public void setLignesCommande(List<LigneCommandeEntity> lignesCommande) {
        this.lignesCommande = lignesCommande;
    }

    @Override
    public String toString() {
        return "CommandeEntity{" +
                "id_c=" + id_c +
                ", date_c=" + date_c +
                ", total=" + total +
                ", client_id=" + client_id +
                ", etat='" + etat + '\'' +
                ", lignesCommande=" + lignesCommande +
                '}';
    }
}

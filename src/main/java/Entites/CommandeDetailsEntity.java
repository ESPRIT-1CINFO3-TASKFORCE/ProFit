package Entites;

import java.util.List;

public class CommandeDetailsEntity {
    private int idCommande;
    private String nomClient;
    private String emailClient;
    private int numTelClient;
    private double totalCommande;
    private String etatCommande;
    private List<ProduitDetailsEntity> produits;

    public CommandeDetailsEntity(int idCommande, String nomClient, String emailClient, int numTelClient, double totalCommande, String etatCommande) {
        this.idCommande = idCommande;
        this.nomClient = nomClient;
        this.emailClient = emailClient;
        this.numTelClient = numTelClient;
        this.totalCommande = totalCommande;
        this.etatCommande = etatCommande;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public int getNumTelClient() {
        return numTelClient;
    }

    public void setNumTelClient(int numTelClient) {
        this.numTelClient = numTelClient;
    }

    public double getTotalCommande() {
        return totalCommande;
    }

    public void setTotalCommande(double totalCommande) {
        this.totalCommande = totalCommande;
    }

    public String getEtatCommande() {
        return etatCommande;
    }

    public void setEtatCommande(String etatCommande) {
        this.etatCommande = etatCommande;
    }

    public List<ProduitDetailsEntity> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitDetailsEntity> produits) {
        this.produits = produits;
    }
}

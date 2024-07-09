package Entites;

import java.util.List;

public class PanierEntity {
    private int id_pan;
    private double total;
    private List<ProduitEntity> produits;

    public PanierEntity() {
    }

    public PanierEntity(int id_pan, double total) {
        this.id_pan = id_pan;
        this.total = total;
    }

    public PanierEntity(double total, List<ProduitEntity> produits) {
        this.total = total;
        this.produits = produits;
    }

    public PanierEntity(int id_pan) {
        this.id_pan = id_pan;
        this.total = total;
        this.produits = produits;
    }

    public int getId_pan() {
        return id_pan;
    }

    public void setId_pan(int id_pan) {
        this.id_pan = id_pan;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<ProduitEntity> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitEntity> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "PanierEntity{" +
                "id_pan=" + id_pan +
                ", total=" + total +
                ", produits=" + produits +
                '}';
    }
}

package Entites;

public class ProduitDetailsEntity {
    private String nomProduit;
    private int prixProduit;
    private int quantiteProduit;
    private String imagProduit;

    public ProduitDetailsEntity(String nomProduit, int prixProduit, int quantiteProduit,String imagProduit) {
        this.nomProduit = nomProduit;
        this.prixProduit = prixProduit;
        this.quantiteProduit = quantiteProduit;
        this.imagProduit =imagProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public int getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(int prixProduit) {
        this.prixProduit = prixProduit;
    }

    public int getQuantiteProduit() {
        return quantiteProduit;
    }

    public void setQuantiteProduit(int quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public String getImagProduit() {
        return imagProduit;
    }

    public void setImagProduit(String imagProduit) {
        this.imagProduit = imagProduit;
    }
}

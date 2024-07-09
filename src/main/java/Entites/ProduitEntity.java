package Entites;

public class ProduitEntity {
    private  int id_pro;
    private String nom_p;
    private int prix;
    private int qnt;

    private String image;

    public ProduitEntity() {
    }

    public ProduitEntity(int id_pro, String nom_p, int prix, String image) {
        this.id_pro = id_pro;
        this.nom_p = nom_p;
        this.prix = prix;
        this.image = image;
    }

    public ProduitEntity(int id_pro, String nom_p, int prix, int qnt, String image) {
        this.id_pro = id_pro;
        this.nom_p = nom_p;
        this.prix = prix;
        this.qnt = qnt;
        this.image = image;
    }

    public ProduitEntity(String nom_p, int prix, int qnt, String image) {
        this.nom_p = nom_p;
        this.prix = prix;
        this.qnt = qnt;
        this.image = image;
    }

    public ProduitEntity(int id_pro) {
        this.id_pro = id_pro;
    }

    public ProduitEntity(String nom_p, int prix, String image) {
        this.nom_p = nom_p;
        this.prix = prix;
        this.image = image;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getNom_p() {
        return nom_p;
    }

    public void setNom_p(String nom_p) {
        this.nom_p = nom_p;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProduitEntity{" +
                "id_pro=" + id_pro +
                ", nom_p='" + nom_p + '\'' +
                ", prix=" + prix +
                ", qnt=" + qnt +
                ", image='" + image + '\'' +
                '}';
    }
}

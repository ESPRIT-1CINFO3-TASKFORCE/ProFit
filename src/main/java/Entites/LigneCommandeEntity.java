package Entites;

public class LigneCommandeEntity {
    private int id_lc;
    private int id_c;
    private int id_pro;
    private int quantite;
    private double prix;

    public LigneCommandeEntity(int id_lc, int id_c, int id_pro, int quantite, double prix) {
        this.id_lc = id_lc;
        this.id_c = id_c;
        this.id_pro = id_pro;
        this.quantite = quantite;
        this.prix = prix;
    }

    public int getId_lc() {
        return id_lc;
    }

    public void setId_lc(int id_lcc) {
        this.id_lc = id_lc;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "LigneCommandeCommandeEntity{" +
                "id_lcc=" + id_lc +
                ", id_c=" + id_c +
                ", id_pro=" + id_pro +
                ", quantite=" + quantite +
                ", prix=" + prix +
                '}';
    }
}

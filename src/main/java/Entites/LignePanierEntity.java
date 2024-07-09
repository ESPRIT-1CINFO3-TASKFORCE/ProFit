package Entites;

public class LignePanierEntity {
    private int id_lc;
    private int id_pan;
    private int id_pro;
    private int quantite;

    public LignePanierEntity(int id_lc, int id_pan, int id_pro, int quantite) {
        this.id_lc = id_lc;
        this.id_pan = id_pan;
        this.id_pro = id_pro;
        this.quantite = quantite;
    }

    public int getId_lc() {
        return id_lc;
    }

    public void setId_lc(int id_lc) {
        this.id_lc = id_lc;
    }

    public int getId_c() {
        return id_pan;
    }

    public void setId_c(int id_c) {
        this.id_pan = id_c;
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

    @Override
    public String toString() {
        return "LigneCommandeEntity{" +
                "id_lc=" + id_lc +
                ", id_pan=" + id_pan +
                ", id_pro=" + id_pro +
                ", quantite=" + quantite +
                '}';
    }






}

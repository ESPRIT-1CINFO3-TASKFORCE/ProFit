package Entites;

public class RegimeUser {
    int id_ligne;
    int id_R;
    int id_C;
    public RegimeUser(){}

    public RegimeUser(int id_ligne, int id_R, int id_C) {
        this.id_ligne = id_ligne;
        this.id_R = id_R;
        this.id_C = id_C;
    }

    public int getId_ligne() {
        return id_ligne;
    }

    public void setId_ligne(int id_ligne) {
        this.id_ligne = id_ligne;
    }

    public int getId_R() {
        return id_R;
    }

    public void setId_R(int id_R) {
        this.id_R = id_R;
    }

    public int getId_C() {
        return id_C;
    }

    public void setId_C(int id_C) {
        this.id_C = id_C;
    }
}

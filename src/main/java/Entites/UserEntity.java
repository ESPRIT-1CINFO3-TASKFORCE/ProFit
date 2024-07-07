package Entites;

import java.util.Objects;

public class UserEntity {
    private int id;
    private int age;
    private int poids;
    private int longeur;
    private int note_c;
    private int note_n;
    private int n_tel;
    private String nom;
    private String prenom;
    private String login;
    private String mdp;
    private String email;
    private String role;
    private Boolean active;

    // Constructeur complet
    public UserEntity(int id, int age, int poids, int longeur, int note_c, int note_n, int n_tel, String nom, String prenom, String login, String mdp, String email, String role) {
        this.id = id;
        this.age = age;
        this.poids = poids;
        this.longeur = longeur;
        this.note_c = note_c;
        this.note_n = note_n;
        this.n_tel = n_tel;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.mdp = mdp;
        this.email = email;
        this.role = role;
        this.active = active;
    }

    // Constructeur sans ID pour l'ajout de nouveaux utilisateurs
    /*public UserEntity(int age, int poids, int longeur, int note_c, int note_n, int n_tel, String nom, String prenom, String login, String mdp, String email, int role) {
        this.age = age;
        this.poids = poids;
        this.longeur = longeur;
        this.note_c = note_c;
        this.note_n = note_n;
        this.n_tel = n_tel;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.mdp = mdp;
        this.email = email;
        this.role = role;
    }*/

    public UserEntity(String nom, String prenom, int age, String email, int nTel) {
    }

    public UserEntity() {

    }

    public UserEntity(String nom, String prenom, String email, String role) {
    }



    public UserEntity(int id, String nom, String prenom, String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }


    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public int getLongeur() {
        return longeur;
    }

    public void setLongeur(int longeur) {
        this.longeur = longeur;
    }

    public int getNote_c() {
        return note_c;
    }

    public void setNote_c(int note_c) {
        this.note_c = note_c;
    }

    public int getNote_n() {
        return note_n;
    }

    public void setNote_n(int note_n) {
        this.note_n = note_n;
    }

    public int getN_tel() {
        return n_tel;
    }

    public void setN_tel(int n_tel) {
        this.n_tel = n_tel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public boolean isActive() { return false; }

    public void setActive(boolean active) {this.active=active;}

    // Méthodes Object (toString, equals, hashCode)
    @Override
    public String toString() {
        return nom+" "+prenom+" - "+role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                age == that.age &&
                poids == that.poids &&
                longeur == that.longeur &&
                note_c == that.note_c &&
                note_n == that.note_n &&
                n_tel == that.n_tel &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(prenom, that.prenom) &&
                Objects.equals(login, that.login) &&
                Objects.equals(mdp, that.mdp) &&
                Objects.equals(email, that.email) &&
                Objects.equals(role, that.role);


    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, poids, longeur, note_c, note_n, n_tel, nom, prenom, login, mdp, email, role, active);
    }

}

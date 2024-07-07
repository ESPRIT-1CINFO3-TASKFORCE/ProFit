package entites;

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
    private int role;

    public UserEntity() {
    }

    public UserEntity(int id, int age, int poids, int longeur, int note_c, int note_n, int n_tel, String nom, String prenom, String login, String mdp, String email, int role) {
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
    }

    public UserEntity(String nom, String prenom, int age, String email, int n_tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.n_tel = n_tel;
    }

    // Getters and setters

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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}

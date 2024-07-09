package Entites;

public class UserEntity {
    private int client_id ;

    public UserEntity() {
        this.client_id = client_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "client_id=" + client_id +
                '}';
    }
}

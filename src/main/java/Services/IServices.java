package Services;

import Entites.UserEntity;

import java.sql.SQLException;
import java.util.List;

public interface IServices <T>{

    void ajouter(T t) throws SQLException;

    void supprimer(T t) throws SQLException;

    void  update(T t) throws SQLException;

    T findbyId(int e) throws SQLException;

    List<UserEntity> readAll() throws SQLException;
}

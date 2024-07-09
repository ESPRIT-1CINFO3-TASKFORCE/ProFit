package Services;

import java.sql.SQLException;
import java.util.List;

public interface IServices <T>{

    void ajouter(T t) throws SQLException;

    void supprimer(T t) throws SQLException;

    void  update(T t,int id) throws SQLException;

    T findbyId(String e) throws SQLException;

    List<T> readAll() throws SQLException;
}

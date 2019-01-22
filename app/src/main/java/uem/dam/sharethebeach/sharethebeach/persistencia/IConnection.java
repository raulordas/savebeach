package uem.dam.sharethebeach.sharethebeach.persistencia;

import java.sql.Connection;

public interface IConnection {

    void callback(Connection con);

}

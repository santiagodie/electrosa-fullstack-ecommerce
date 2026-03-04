package es.unirioja.paw.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManagerDS {

    private static final String NOMBRE_DE_DATA_SOURCE = "java:comp/env/jdbc/electrosa";
    private static DataSource ds;

    static {
        try {
		// Variable static para contener la referencia al DataSource, que se inicializa cuando se
		// carga esta clase
            ds = getDataSource();
        } catch (NamingException ex) {
            Logger.getLogger(ConnectionManagerDS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static DataSource getDataSource() throws NamingException {
        DataSource ds = null;
		//Obtener el DataSource del servidor de aplicaciones
        ds = (DataSource) new InitialContext().lookup(NOMBRE_DE_DATA_SOURCE);
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void returnConnection(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }

}

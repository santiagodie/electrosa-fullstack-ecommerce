package es.unirioja.paw.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManagerDS {

    private DataSource ds;
    private static final String JNDI_NAME = "java:comp/env/jdbc/electrosa";
    private static final String URL  = "jdbc:mysql://localhost:3306/electrosa?serverTimezone=GMT%2B2";
    private static final String USER = "root";
    private static final String PASS = "root";

    public ConnectionManagerDS() {
        try {
            this.ds = (DataSource) InitialContext.doLookup(JNDI_NAME);
            System.out.println("[ConnectionManagerDS] Usando pool JNDI de Tomcat.");
        } catch (NamingException e) {
            System.out.println("[ConnectionManagerDS] JNDI no disponible, usando MysqlDataSource.");
            MysqlDataSource dsUni = new MysqlDataSource();
            dsUni.setURL(URL);
            dsUni.setUser(USER);
            dsUni.setPassword(PASS);
            this.ds = dsUni;
        }
    }

    public ConnectionManagerDS(String url, String user, String password) {
        MysqlDataSource dsUni = new MysqlDataSource();
        dsUni.setURL(url);
        dsUni.setUser(user);
        dsUni.setPassword(password);
        this.ds = dsUni;
    }

    public ConnectionManagerDS(DataSource entrada) {
        this.ds = entrada;
    }

    public DataSource getDatasource() { return ds; }
    public void setDatasource(DataSource entrada) { this.ds = entrada; }

    public Connection crearCon() throws SQLException {
        if (ds != null) {
            return ds.getConnection(); // pool o directo según el caso
        }
        throw new SQLException("No se pudo establecer conexión.");
    }

    public void cerrarCon(Connection con) throws SQLException {
        if (con != null && !con.isClosed()) {
            con.close(); // devuelve al pool si es JNDI, cierra si es directa
        }
    }
}
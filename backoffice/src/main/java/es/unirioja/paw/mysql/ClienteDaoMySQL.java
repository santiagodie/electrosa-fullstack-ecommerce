package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.ClienteDAO;
import es.unirioja.paw.model.Cliente;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoMySQL implements ClienteDAO {

    private final ConnectionManagerDS ds;

    public ClienteDaoMySQL() {
        this.ds = new ConnectionManagerDS();
    }

    public ClienteDaoMySQL(ConnectionManagerDS con) {
        this.ds = con;
    }

    @Override
    public List<Cliente> findAll() throws ExcepcionDeAplicacion {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement pts = null;
        ResultSet rs = null;
        Connection con = null;

        String sql = "SELECT * FROM cliente";
        try {
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
            rs = pts.executeQuery();
            while (rs.next()) {
                String cod  = rs.getString("codigo");
                String user = rs.getString("username");
                String nom  = rs.getString("nombre");
                String email = rs.getString("email");
                String cp   = rs.getString("cp");
                String ciu  = rs.getString("ciudad");
                String prov = rs.getString("provincia");
                clientes.add(new Cliente(cod, user, nom, email, cp, ciu, prov));
            }
            rs.close();
            pts.close();
        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al obtener todos los clientes", e);
        } finally {
            if (con != null) {
                try {
                    ds.cerrarCon(con);
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return clientes;
    }

    @Override
    public Cliente findOneByCodigo(String codigo) throws ExcepcionDeAplicacion {
        Cliente cliente = null;
        PreparedStatement pts = null;
        ResultSet rs = null;
        Connection con = null;

        String sql = "SELECT * FROM cliente WHERE codigo = ?";
        try {
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
            pts.setString(1, codigo);
            rs = pts.executeQuery();

            if (rs.next()) {
                String cod  = rs.getString("codigo");
                String user = rs.getString("username");
                String nom  = rs.getString("nombre");
                String email = rs.getString("email");
                String cp   = rs.getString("cp");
                String ciu  = rs.getString("ciudad");
                String prov = rs.getString("provincia");
                cliente = new Cliente(cod, user, nom, email, cp, ciu, prov);
            }
            rs.close();
            pts.close();
        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al obtener el cliente", e);
        } finally {
            if (con != null) {
                try {
                    ds.cerrarCon(con);
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return cliente;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.PedidoDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Santiago Die
 */
public class PedidoDaoMySQL implements PedidoDAO {
    private final ConnectionManagerDS ds;

    public PedidoDaoMySQL() {
        this.ds = new ConnectionManagerDS();
    }
    public PedidoDaoMySQL(ConnectionManagerDS con) {
        this.ds = con;
    }
    
    @Override
    public List<Pedido> findAll() throws ExcepcionDeAplicacion {
        
        List<Pedido> orders = new ArrayList<>();
        PreparedStatement pts= null;
        ResultSet rs= null;
        Connection con = null;
        
        String sql = "SELECT * FROM pedido";

        try {
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
            rs = pts.executeQuery();

            while (rs.next()) {
                String cod = rs.getString("CODIGO");
                String codclient = rs.getString("CODIGOCLIENTE");
                String cp = rs.getString("CP");
                String calle = rs.getString("CALLE");
                String ciu = rs.getString("CIUDAD");
                String prov = rs.getString("PROVINCIA");
                orders.add(new Pedido(cod,codclient,cp,calle,ciu,prov));
            }
            rs.close();
            pts.close();

        }catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionDeAplicacion("Error al obtener todos los orders", e);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }finally{
            try {
                ds.cerrarCon(con);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return orders;
    }

   
    
}

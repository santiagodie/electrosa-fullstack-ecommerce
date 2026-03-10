/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.AlmacenDAO;
import es.unirioja.paw.model.Almacen;

import es.unirioja.paw.model.ExcepcionDeAplicacion;
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
public class AlmacenDaoMySQL implements AlmacenDAO {
    
    private final ConnectionManagerDS ds;

    public AlmacenDaoMySQL() {
        this.ds = new ConnectionManagerDS();
    }
    
    @Override
    public List<Almacen> findAll() throws ExcepcionDeAplicacion {
    List<Almacen> almacenes = new ArrayList<>();
        PreparedStatement pts= null;
        ResultSet rs= null;
        Connection con = null;
        
        String sql = "SELECT * FROM almacen";

        try {
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
            rs = pts.executeQuery();

            while (rs.next()) {
                String cod = rs.getString("codigo");
                String calle = rs.getString("calle");
                String ciu = rs.getString("ciudad");
                String cp = rs.getString("cp"); 
                String provincia=rs.getString("provincia");
                almacenes.add(new Almacen(cod,calle,ciu,cp,provincia));
            }
            rs.close();
            pts.close();

        }catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionDeAplicacion("Error al obtener todos los artículos", e);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }finally{
            try {
                ds.cerrarCon(con);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return almacenes;
    
    }
}

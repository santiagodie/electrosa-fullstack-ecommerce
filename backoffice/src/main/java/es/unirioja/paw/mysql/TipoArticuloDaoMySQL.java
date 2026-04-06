/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.TipoArticuloDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Pedido;
import es.unirioja.paw.model.TipoArticulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sadie
 */
public class TipoArticuloDaoMySQL implements TipoArticuloDAO {
    
    private final ConnectionManagerDS ds;
    public TipoArticuloDaoMySQL(){
        this.ds = new ConnectionManagerDS();
    }
    
    @Override
    public List<TipoArticulo> findall() throws ExcepcionDeAplicacion {
       List<TipoArticulo> tipos = new ArrayList<>();
        PreparedStatement pts= null;
        ResultSet rs= null;
        Connection con = null;
        
        String sql = "select distinct(tipo) from articulo";

        try {
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
            rs = pts.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("tipo");
                tipos.add(new TipoArticulo(nombre));
            }
            rs.close();
            pts.close();

        }catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionDeAplicacion("Error al obtener todos los tipos", e);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }finally{
            try {
                ds.cerrarCon(con);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return tipos;
    }
    
}

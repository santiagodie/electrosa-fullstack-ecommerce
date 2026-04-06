/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.FabricanteDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Fabricante;
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
public class FabricanteDaoMySQL implements FabricanteDAO {
    private final ConnectionManagerDS ds;
    
    public FabricanteDaoMySQL(){
        this.ds = new ConnectionManagerDS();
    }
    public FabricanteDaoMySQL(ConnectionManagerDS con){
        this.ds = con;
    }
    @Override
    public List<Fabricante> findall() throws ExcepcionDeAplicacion {
           List<Fabricante> fabricantes = new ArrayList<>();
        PreparedStatement pts= null;
        ResultSet rs= null;
        Connection con = null;
        
        String sql = "select distinct(fabricante) from articulo";

        try {
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
            rs = pts.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("fabricante");
                fabricantes.add(new Fabricante(nombre));
            }
            rs.close();
            pts.close();

        }catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionDeAplicacion("Error al obtener todos los fabricantes", e);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }finally{
            try {
                ds.cerrarCon(con);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return fabricantes;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.ArticuloDAO;
import es.unirioja.paw.pagination.PageNumberPagination;
import es.unirioja.paw.model.Articulo;
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
public class ArticuloDaoMySQL implements ArticuloDAO {
    private final ConnectionManagerDS ds;

    public ArticuloDaoMySQL() {
        this.ds = new ConnectionManagerDS();
    }
    public ArticuloDaoMySQL(ConnectionManagerDS con) {
        this.ds = con;
    }
    
    @Override
    public List<Articulo> findByPage(int pageNumber, int pageSize) throws ExcepcionDeAplicacion {
        List<Articulo> articulos = new ArrayList<>();
        PreparedStatement pts= null;
        ResultSet rs= null;
        Connection con = null;
        
        String sql = "SELECT * FROM articulo LIMIT ?, ?";
        int variacion =  pageSize *(pageNumber-1) ;
        
    try {
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
    
            pts.setInt(1, variacion);
            pts.setInt(2, pageSize);
            rs = pts.executeQuery();
            
            while (rs.next()){
                String cod = rs.getString("codigo");
                String nom = rs.getString("nombre");
                double pvp = rs.getDouble("pvp");
                String tipo = rs.getString("tipo");
                String fab = rs.getString("fabricante");
                String foto = rs.getString("foto");
                String desp = rs.getString("descripcion");
                articulos.add(new Articulo(cod, nom, pvp, tipo, fab, foto, desp));
            }
            rs.close();
            pts.close();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionDeAplicacion("Error al obtener los artículos", e);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }finally{
            try {
                ds.cerrarCon(con);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    return articulos;
}

    @Override
    public PageNumberPagination buildPageNumberPagination(int pageSize, int currentPage) throws ExcepcionDeAplicacion {
        
        
        PreparedStatement pts = null;
        ResultSet rs = null;
        Connection con= null;
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM articulo";
        
    try { 
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
            rs = pts.executeQuery();
            
        if (rs.next()) {
            total = rs.getInt("total");
        }
        rs.close();
        pts.close();
    }catch(SQLException e){
            e.printStackTrace();
            throw new ExcepcionDeAplicacion("Error al obtener el total de articulos", e);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }finally{
            try {
                ds.cerrarCon(con);
            }catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return new PageNumberPagination(total, pageSize, currentPage);
    }
}

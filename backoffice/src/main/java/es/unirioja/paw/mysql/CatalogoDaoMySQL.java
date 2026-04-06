/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.CatalogoDAO;
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
 * @author sadie
 */
public class CatalogoDaoMySQL implements CatalogoDAO {

    private final ConnectionManagerDS ds;

    public CatalogoDaoMySQL() {
        this.ds = new ConnectionManagerDS();
    }

    public CatalogoDaoMySQL(ConnectionManagerDS connectionManager) {
        this.ds = connectionManager;
    }
    
    @Override
    public List<Articulo> findAll() throws ExcepcionDeAplicacion {
        
        List<Articulo> articulos = new ArrayList<>();
        PreparedStatement pts= null;
        ResultSet rs= null;
        Connection con = null;
        
        String sql = "SELECT * FROM articulo";

        try {
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
            rs = pts.executeQuery();

            while (rs.next()) {
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
            throw new ExcepcionDeAplicacion("Error al obtener todos los artículos", e);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public Articulo findOneByCodigo(String codigo) throws ExcepcionDeAplicacion {
        
        Articulo articulo = null;
        PreparedStatement pts = null;
        ResultSet rs = null;
        Connection con= null;
        
        String sql = "SELECT * FROM articulo WHERE codigo = ?";
        try{ 
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
            pts.setString(1,codigo);          
            rs = pts.executeQuery();
            
            if (rs.next()) {
                String nom = rs.getString("nombre");
                double pvp = rs.getDouble("pvp");
                String tipo = rs.getString("tipo");
                String fab = rs.getString("fabricante");
                String foto = rs.getString("foto");
                String desp = rs.getString("descripcion");
                articulo = new Articulo(codigo, nom, pvp, tipo, fab, foto, desp);
            }
            rs.close();
            pts.close();
        
        }catch(SQLException e){
            e.printStackTrace();
            throw new ExcepcionDeAplicacion("Error al obtener el articulo", e);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }finally{
            try {
                ds.cerrarCon(con);
            }catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return articulo;
    }
     @Override
    public void modificarArticulo(Articulo articulo) throws ExcepcionDeAplicacion {
        if (articulo == null) {
            throw new ExcepcionDeAplicacion("El artículo no puede ser null");
        }
        PreparedStatement pst = null;
        Connection con = null;
        String sql = "UPDATE articulo SET nombre = ?, descripcion = ?, tipo = ?, fabricante = ?, pvp = ?, foto = ? WHERE codigo = ?";

        try {
            con = ds.crearCon();
            pst = con.prepareStatement(sql);

            pst.setString(1, articulo.getNombre());
            pst.setString(2, articulo.getDescripcion());
            pst.setString(3, articulo.getTipo());
            pst.setString(4, articulo.getFabricante());
            pst.setDouble(5, articulo.getPvp());
            pst.setString(6, articulo.getFoto());
            pst.setString(7, articulo.getCodigo());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 0) {
                throw new ExcepcionDeAplicacion("No se encontró el artículo con código: " + articulo.getCodigo());
            }

            pst.close();

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al actualizar el artículo en la base de datos.", e);
        } finally {
            try {
                if (pst != null)
                    pst.close();
                ds.cerrarCon(con);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ExcepcionDeAplicacion("Error al cerrar recursos.", e);
            }
        }
    }
}

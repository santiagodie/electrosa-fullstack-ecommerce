package es.unirioja.paw.mysql;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import es.unirioja.paw.dao.UsuarioDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Pedido;
import es.unirioja.paw.model.Usuario;
import es.unirioja.paw.mysql.ConnectionManagerDS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Santiago Die
 */
public class UsuarioDaoMySQL implements UsuarioDAO {

    private final ConnectionManagerDS ds;

    public UsuarioDaoMySQL() {
        ds = new ConnectionManagerDS();
    }
    
    public UsuarioDaoMySQL(ConnectionManagerDS con) {
        this.ds = con;
    }
    
    @Override
    public Usuario findOneByUsername(String username, String passwordDeco) throws ExcepcionDeAplicacion {
          
        Usuario user=null;
        PreparedStatement pts = null;
        ResultSet rs = null;
        Connection con= null;
        
        String sql = "SELECT * FROM usuario WHERE username = ?";
        try{ 
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
            pts.setString(1,username);          
            rs = pts.executeQuery();
            
            if (rs.next()) {
                String cod = rs.getString("username");
                String contraseña = rs.getString("password");
                if(passwordDeco.equals(contraseña))user = new Usuario(cod);
            }
            rs.close();
            pts.close();
        
        }catch(SQLException e){
            e.printStackTrace();
            throw new ExcepcionDeAplicacion("Error al obtener el usuario", e);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }finally{
            try {
                ds.cerrarCon(con);
            }catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return user;
    
    
    }

    @Override
    public boolean esAdmin(String username) throws ExcepcionDeAplicacion {
        boolean es= false;
        PreparedStatement pts = null;
        ResultSet rs = null;
        Connection con= null;
        
        String sql = "SELECT * FROM rolesusuario WHERE username = ?";
        try{ 
            con = ds.crearCon();
            pts = con.prepareStatement(sql);
            pts.setString(1,username);          
            rs = pts.executeQuery();
            
            if (rs.next()) {
                String rol = rs.getString("rol");
                if("administrador".equals(rol) ) es = true;                
            }
            rs.close();
            pts.close();
        
        }catch(SQLException e){
            e.printStackTrace();
            throw new ExcepcionDeAplicacion("Error al obtener el rol del Usuario", e);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }finally{
            try {
                ds.cerrarCon(con);
            }catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return es;
        
    }
       
}

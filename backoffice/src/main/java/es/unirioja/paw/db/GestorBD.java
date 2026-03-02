/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.db;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
/**
 *
 * @author sadie
 */
public class GestorBD  {
    private DataSource ds;

public GestorBD() {
        MysqlDataSource dsUni = new MysqlDataSource();
        dsUni.setURL("jdbc:mysql://localhost:3306/electrosa?serverTimezone=GMT%2B2");
        dsUni.setUser("root");
        dsUni.setPassword("root");
        this.ds = dsUni;
    }

    public GestorBD(DataSource dataSource) {
        this.ds = dataSource;
    }
    
    public List<String> getArticulosLike(String nom) throws SQLException {
        
        String sql = "select nombre from Articulo where nombre LIKE ?";
        List<String> result = new ArrayList<>();
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nom + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
               
                while (rs.next()) {
                    result.add(rs.getString("nombre"));
                }
            }
        } catch (SQLException ex) {
            throw new SQLException("Error de consulta" + ex.getMessage(), ex);
        }
        return result;
    }
}

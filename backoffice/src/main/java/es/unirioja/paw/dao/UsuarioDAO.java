/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Usuario;

/**
 *
 * @author sadie
 */
public interface UsuarioDAO {
    
    public Usuario findOneByUsername(String username,String passwordDeco) throws ExcepcionDeAplicacion;
    
    public boolean esAdmin(String username)throws ExcepcionDeAplicacion;
     
}

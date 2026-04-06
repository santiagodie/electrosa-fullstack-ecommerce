/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.Cliente;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.util.List;
/**
 *
 * @author Santiago Die
 */
public interface ClienteDAO {

    public List<Cliente> findAll() throws ExcepcionDeAplicacion;

    public Cliente findOneByCodigo(String codigo) throws ExcepcionDeAplicacion;
}
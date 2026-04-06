/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.pagination.PageNumberPagination;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.util.List;


/**
 *
 * @author Santiago Die
 */
public interface ArticuloDAO {
    
public List<Articulo> findByPage(int pageNumber,int pageSize) throws ExcepcionDeAplicacion;

public PageNumberPagination buildPageNumberPagination(int pageSize, int currentPage) throws ExcepcionDeAplicacion;     
}

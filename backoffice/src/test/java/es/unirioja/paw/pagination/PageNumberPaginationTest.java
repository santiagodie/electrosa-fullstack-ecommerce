/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.pagination;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author sadie
 */
public class PageNumberPaginationTest {
    
    public PageNumberPaginationTest(){}
    
    @Test
    public void PaginaTest()throws Exception {

        int total=447;
        int tamaño=12;

       assertEquals(2,new PageNumberPagination(total,tamaño,3).previous());
       assertEquals(null,new PageNumberPagination(total,tamaño,1).previous());
       assertEquals(2,new PageNumberPagination(total,tamaño,1).next());
       assertEquals(null,new PageNumberPagination(total,tamaño,38).next());
    }
}

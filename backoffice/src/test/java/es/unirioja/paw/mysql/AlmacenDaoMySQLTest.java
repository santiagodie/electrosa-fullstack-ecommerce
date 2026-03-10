/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.model.Almacen;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Santiago Die
 */
public class AlmacenDaoMySQLTest {
    private AlmacenDaoMySQL almacen;
    public AlmacenDaoMySQLTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        almacen = new AlmacenDaoMySQL();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of findAll method, of class AlmacenDaoMySQL.
     */
    @Test
    public void testFindAllReturns() throws ExcepcionDeAplicacion {

        assertEquals(5, almacen.findAll().size(), "El tamaño de la lista devuelta por findAll debería ser " + 5);
    } 
    
}

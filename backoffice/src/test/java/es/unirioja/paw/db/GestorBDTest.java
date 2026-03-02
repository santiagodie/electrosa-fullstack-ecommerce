/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.unirioja.paw.db;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author sadie
 */
public class GestorBDTest {

    public GestorBDTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getArticulosLike method, of class GestorBD.
     */
    @Test
    public void testGetArticulosLike() throws Exception {
        System.out.println("getArticulosLike");
        String nombre = "Sci";
        GestorBD instance = new GestorBD();
        List<String> result = instance.getArticulosLike(nombre);
        assertNotNull(result);
        assertTrue(result.size() == 4);
    }

}

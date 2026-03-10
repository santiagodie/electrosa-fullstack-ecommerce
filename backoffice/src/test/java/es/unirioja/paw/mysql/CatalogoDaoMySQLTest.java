/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.model.Articulo;
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
public class CatalogoDaoMySQLTest {
    
    private CatalogoDaoMySQL catalog;
    
    public CatalogoDaoMySQLTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        catalog = new CatalogoDaoMySQL();
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of findAll method, of class CatalogoDaoMySQL.
     */
    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        CatalogoDaoMySQL instance = new CatalogoDaoMySQL();
        List<Articulo> result = instance.findAll();
        assertNotNull(result);
        assertTrue(result.size() > 0);
        System.out.println("Items found: " + result.size());
    }

    /**
     * Test of findOneByCodigo method, of class CatalogoDaoMySQL.
     */
    @Test
    public void testFindOneByCodigo() throws ExcepcionDeAplicacion {
        
        String cod = "Ede-425PA";
        String nom = "Placa 2EP-4G B/NAT - Edesa";
        double pvp = 240.00;
        String tipo = "Placa";
        String fab = "Edesa";
        String foto = "Placa/placa151.jpg";
        String desp = "Phasellus vitae neque risus, et tempus lacus. Cras tincidunt iaculis sem, quis congue lacus fermentum ut.";
        Articulo articulo = catalog.findOneByCodigo(cod);

        assertNotNull(articulo, "El artículo no debería ser nulo");
        assertEquals(cod,articulo.getCodigo(),"El código del artículo no coincide");
        assertEquals(nom,articulo.getNombre(),"El nombre del artículo no coincide");
        assertEquals(pvp,articulo.getPvp(), 0.01,"El precio del artículo no coincide");
        assertEquals(tipo,articulo.getTipo(),"El tipo del artículo no coincide");
        assertEquals(fab,articulo.getFabricante(),"El fabricante del artículo no coincide");
        assertEquals(foto,articulo.getFoto(),"La foto del artículo no coincide");
        assertEquals(desp,articulo.getDescripcion(),"La descripción del artículo no coincide");
    }

    @Test
    public void testFindOneByCodigo2() throws ExcepcionDeAplicacion {
        String cod = "codigo_malo";
        assertNull(catalog.findOneByCodigo(cod), "Debería devolver null");
    }
}

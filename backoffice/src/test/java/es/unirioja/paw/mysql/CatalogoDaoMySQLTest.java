/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.CatalogoDAO;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author sadie
 */
public class CatalogoDaoMySQLTest {

    private CatalogoDAO catalogoDAO;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        // Configurar base de datos en memoria con HSQLDB
        DataSource dataSource = new JDBCDataSource();
        ((JDBCDataSource) dataSource).setURL("jdbc:hsqldb:mem:testdb");
        jdbcTemplate = new JdbcTemplate(dataSource);

        // Crear la tabla articulo
        jdbcTemplate.execute("CREATE TABLE articulo (" +
                "codigo VARCHAR(20), " +
                "nombre VARCHAR(100), " +
                "pvp DOUBLE, " +
                "tipo VARCHAR(50), " +
                "fabricante VARCHAR(50), " +
                "foto VARCHAR(100), " +
                "descripcion VARCHAR(500), " +
                "PRIMARY KEY (codigo))");

        // Insertar 10 artículos desde la imagen
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "MIE-020LV", "Lavavajillas G 692-3 SCI PLUS ...", 719.40, "Lavavajillas", "Lavavajillas Miele", "Lavavajillas/lavavajilla...", 
                "Vivamus non magna non enim scelerisque luctus. Praesent eu mi eget erat ultricies consequat...");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "MIE-021LV", "Lavavajillas G 698-3 SCI PLUS ...", 726.00, "Lavavajillas", "Lavavajillas Miele", "Lavavajillas/lavavajilla...", 
                "Praesent posuere mauris vitae nulla malesuada scelerisque. Curabitur quis massa nunc...");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "MIE-023LV", "Lavavajillas G 694-3 SCI PLUS ...", 739.20, "Lavavajillas", "Lavavajillas Miele", "Lavavajillas/lavavajilla...", 
                "Praesent posuere mauris vitae nulla malesuada scelerisque. Curabitur quis massa nunc...");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "MIE-024LV", "Lavavajillas G 601 SCI PLUS M ...", 745.80, "Lavavajillas", "Lavavajillas Miele", "Lavavajillas/lavavajilla...", 
                "Nulla ornare mi nec quam porta eget sollicitudin ante volutpat. Aliquam scelerisque elit...");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "MIE-025LV", "Lavavajillas G 603 SCI PLUS ...", 752.40, "Lavavajillas", "Lavavajillas Miele", "Lavavajillas/lavavajilla...", 
                "Donec vitae sem nec lorem posuere pellentesque non vitae erat aliquam eu sem magna...");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "MIE-026LV", "Lavavajillas G 648 VI PLUS-MI...", 759.00, "Lavavajillas", "Lavavajillas Miele", "Lavavajillas/lavavajilla...", 
                "Integer id massa sit amet nisl sollicitudin scelerisque. Sed ac sapien vel tellus lobortis...");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "MIE-027LV", "Lavavajillas G 658-3 SCI PLUS ...", 765.60, "Lavavajillas", "Lavavajillas Miele", "Lavavajillas/lavavajilla...", 
                "Aliquam scelerisque elit vitae ligula facilisis sagittis. Quisque ut id ante tincidunt...");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "MIE-028LV", "Lavavajillas G 349 SCHE-Miele", 772.20, "Lavavajillas", "Lavavajillas Miele", "Lavavajillas/lavavajilla...", 
                "Quisque ut diam id ante tincidunt eleifend. Praesent posuere mauris vitae nulla malesuada...");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "MIE-029MI", "Multhormo H 316-3E - Miele", 180.00, "Horno", "Miele", "Horno/horno169.jpg",
                "Integer id massa sit amet nisl sollicitudin scelerisque. Donec vitae sem nec lorem posuere...");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "MIE-030MI", "Multhormo H 321 E - Miele", 183.60, "Horno", "Miele", "Horno/horno159.jpg",
                "Donec egestas sapien tempor dui pulvinar eget mollis urna fringilla. Praesent eu mi eget...");

        // Configurar el DAO con el DataSource
        ConnectionManagerDS connectionManager = new ConnectionManagerDS(dataSource);
        catalogoDAO = new CatalogoDaoMySQL(connectionManager);
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.execute("DROP TABLE articulo");
    }

    @Test
    public void testFindAllReturns() throws ExcepcionDeAplicacion {
        List<Articulo> articulos = catalogoDAO.findAll();
        assertEquals(10, articulos.size(), "Debería haber 10 artículos insertados");

        Articulo art1 = catalogoDAO.findOneByCodigo("MIE-020LV");
        Articulo art2 = catalogoDAO.findOneByCodigo("MIE-021LV");
        Articulo art3 = catalogoDAO.findOneByCodigo("MIE-023LV");
        Articulo art4 = catalogoDAO.findOneByCodigo("MIE-024LV");
        Articulo art5 = catalogoDAO.findOneByCodigo("MIE-025LV");
        Articulo art6 = catalogoDAO.findOneByCodigo("MIE-026LV");
        Articulo art7 = catalogoDAO.findOneByCodigo("MIE-027LV");
        Articulo art8 = catalogoDAO.findOneByCodigo("MIE-028LV");
        Articulo art9 = catalogoDAO.findOneByCodigo("MIE-029MI");
        Articulo art10 = catalogoDAO.findOneByCodigo("MIE-030MI");

        assertTrue(articulos.contains(art1), "La lista debería contener el artículo MIE-020LV");
        assertTrue(articulos.contains(art2), "La lista debería contener el artículo MIE-021LV");
        assertTrue(articulos.contains(art3), "La lista debería contener el artículo MIE-023LV");
        assertTrue(articulos.contains(art4), "La lista debería contener el artículo MIE-024LV");
        assertTrue(articulos.contains(art5), "La lista debería contener el artículo MIE-025LV");
        assertTrue(articulos.contains(art6), "La lista debería contener el artículo MIE-026LV");
        assertTrue(articulos.contains(art7), "La lista debería contener el artículo MIE-027LV");
        assertTrue(articulos.contains(art8), "La lista debería contener el artículo MIE-028LV");
        assertTrue(articulos.contains(art9), "La lista debería contener el artículo MIE-029MI");
        assertTrue(articulos.contains(art10), "La lista debería contener el artículo MIE-030MI");
    }

    @Test
    public void testFindOneByCodigo() throws ExcepcionDeAplicacion {
        Articulo articulo = catalogoDAO.findOneByCodigo("MIE-020LV");
        assertNotNull(articulo, "El artículo no debería ser nulo");
        assertEquals("MIE-020LV", articulo.getCodigo(), "El código del artículo no coincide");
        assertEquals("Lavavajillas G 692-3 SCI PLUS ...", articulo.getNombre(), "El nombre del artículo no coincide");
        assertEquals(719.40, articulo.getPvp(), 0.01, "El precio del artículo no coincide");
        assertEquals("Lavavajillas", articulo.getTipo(), "El tipo del artículo no coincide");
        assertEquals("Lavavajillas Miele", articulo.getFabricante(), "El fabricante del artículo no coincide");
        assertEquals("Lavavajillas/lavavajilla...", articulo.getFoto(), "La foto del artículo no coincide");
        assertEquals("Vivamus non magna non enim scelerisque luctus. Praesent eu mi eget erat ultricies consequat...", 
                articulo.getDescripcion(), "La descripción del artículo no coincide");
    }

    @Test
    public void testFindOneByCodigoInvalid() throws ExcepcionDeAplicacion {
        Articulo articulo = catalogoDAO.findOneByCodigo("codigo_malo");
        assertNull(articulo, "Debería devolver null para un código inválido");
    }

    @Test
    public void testModificarArticulo() throws ExcepcionDeAplicacion {
        Articulo articulo = catalogoDAO.findOneByCodigo("MIE-020LV");
        articulo.setNombre("Lavavajillas Modificado");
        articulo.setPvp(800.00);
        articulo.setTipo("Lavavajillas Modificado");
        articulo.setFabricante("Fabricante Modificado");
        articulo.setFoto("Foto/modificada.jpg");
        articulo.setDescripcion("Descripción modificada");


        catalogoDAO.modificarArticulo(articulo);
        Articulo articuloModificado = catalogoDAO.findOneByCodigo("MIE-020LV");
        assertNotNull(articuloModificado, "El artículo modificado no debería ser nulo");
        assertEquals("Lavavajillas Modificado", articuloModificado.getNombre(), "El nombre del artículo no coincide");
        assertEquals(800.00, articuloModificado.getPvp(), 0.01, "El precio del artículo no coincide");
        assertEquals("Lavavajillas Modificado", articuloModificado.getTipo(), "El tipo del artículo no coincide");
        assertEquals("Fabricante Modificado", articuloModificado.getFabricante(), "El fabricante del artículo no coincide");
        assertEquals("Foto/modificada.jpg", articuloModificado.getFoto(), "La foto del artículo no coincide");
        assertEquals("Descripción modificada", articuloModificado.getDescripcion(), "La descripción del artículo no coincide");
    }

    @Test
    public void testModificarArticuloNotFound() {
        Articulo articulo = new Articulo("codigo_inexistente", "Nombre", 100.00, "Tipo", "Fabricante", "foto.jpg", "Descripción");
        ExcepcionDeAplicacion exception = assertThrows(ExcepcionDeAplicacion.class, () -> {
            catalogoDAO.modificarArticulo(articulo);
        });
        assertTrue(exception.getMessage().contains("No se encontró el artículo con código: codigo_inexistente"));
    }

    @Test
    public void testModificarArticuloNull() {
        ExcepcionDeAplicacion exception = assertThrows(ExcepcionDeAplicacion.class, () -> {
            catalogoDAO.modificarArticulo(null);
        });
        assertTrue(exception.getMessage().contains("El artículo no puede ser null"));
    }
}
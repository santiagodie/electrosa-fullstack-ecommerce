/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.ArticuloDAO;
import es.unirioja.paw.dao.CatalogoDAO;
import es.unirioja.paw.pagination.PageNumberPagination;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Fabricante;
import es.unirioja.paw.model.TipoArticulo;
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
public class ArticuloDaoMySQLTest {

    private ArticuloDAO articuloDAO;
    private CatalogoDAO catalogoDAO;
    private ConnectionManagerDS connectionManager;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        // Configurar base de datos en memoria con HSQLDB
        DataSource dataSource = new JDBCDataSource();
        ((JDBCDataSource) dataSource).setURL("jdbc:hsqldb:mem:testdb");
        jdbcTemplate = new JdbcTemplate(dataSource);

        // Crear la tabla articulo
        jdbcTemplate.execute("CREATE TABLE articulo ("
                + "codigo VARCHAR(20), "
                + "nombre VARCHAR(100), "
                + "pvp DOUBLE, "
                + "tipo VARCHAR(50), "
                + "fabricante VARCHAR(50), "
                + "foto VARCHAR(100), "
                + "descripcion VARCHAR(500), "
                + "PRIMARY KEY (codigo))");

        // Insertar los 20 artículos de la tabla proporcionada
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-430PA", "Placa ZEP-4G A/RUSTICA/BUT ...", 250.00, "Placa", "Edesa", "Placa/placa153.jpg",
                "Morbi at lacus et orci dapibus aliquet. Sed ac sapien vel tellus lobortis sodales.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-431PA", "Placa E12-4G I/BUT - Edesa", 254.00, "Placa", "Edesa", "Placa/placa159.jpg",
                "Donec egestas sapien tempor dui pulvinar eget mollis urna fringilla. Cras incondunt iaculis sem, quis congue lacinia ut. Nam eu sem magna, eu mattis diam. Donec vitae nec lorem posuere pellentesque non vitae erat.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-432PA", "Placa E12-4G I/NAT - Edesa", 258.00, "Placa", "Edesa", "Placa/placa159.jpg",
                "Donec egestas sapien tempor dui pulvinar eget mollis urna fringilla. Praesent eu mi eget erat ultricies consequat. Suspendisse dictum ullamcorper enim, nec lobortis pulvinar nec. Morbi tempus dui ut leo viverra sed sodales.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-434PA", "Placa E12-4G B/NAT - Edesa", 258.00, "Placa", "Edesa", "Placa/placa162.jpg",
                "Integer id massa sit amet nisl sollicitudin scelerisque. Praesent posuere mauris vitae nulla malesuada scelerisque.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-435PA", "Placa E12-31 MI/BUT - Edesa", 260.00, "Placa", "Edesa", "Placa/placa154.jpg",
                "Sed tempor sapien at augue pretium dignissim. Phasellus vitae neque risus, et tempus lacus.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-437CA", "Campana DELTA-90 VX ISLA ...", 190.00, "Campana", "Edesa", "Campana/campana1018.jpg",
                "Morbi at lacus et orci dapibus aliquet. Cras incondunt lacinia sem, quis congue lacinia ut fermentum ut.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-438CA", "Campana CDT-90 X - Edesa", 191.90, "Campana", "Edesa", "Campana/campana1013.jpg",
                "Aliquam scelerisque elit vitae ligula facilisis sagittis. Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-439CA", "Campana DELTA-90 VX - Edesa", 193.80, "Campana", "Edesa", "Campana/campana1018.jpg",
                "Ut eget nulla ac sapien convallis mollis in eget quam. Morbi at lacus et orci dapibus aliquet.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-440CA", "Campana RD-90 I - Edesa", 195.70, "Campana", "Edesa", "Campana/campana1014.jpg",
                "Sed ac sapien vel tellus lobortis sodales. Praesent posuere mauris vitae nulla malesuada scelerisque.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-441CA", "Campana RD-90 NR - Edesa", 197.60, "Campana", "Edesa", "Campana/campana1015.jpg",
                "Integer id massa sit amet nisl sollicitudin scelerisque. Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-442CA", "Campana CD-90 X - Edesa", 199.50, "Campana", "Edesa", "Campana/campana1013.jpg",
                "Morbi at lacus et orci dapibus aliquet. Sed ac sapien vel tellus lobortis sodales. Praesent posuere mauris vitae nulla malesuada scelerisque.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-443CA", "Campana CDT-60 X - Edesa", 201.40, "Campana", "Edesa", "Campana/campana1018.jpg",
                "Aliquam scelerisque elit vitae ligula facilisis sagittis. Ut eget nulla ac sapien convallis mollis in eget quam.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-444CA", "Campana CD-60 B - Edesa", 203.30, "Campana", "Edesa", "Campana/campana1015.jpg",
                "Ut eget nulla ac sapien convallis mollis in eget quam. Praesent posuere mauris vitae nulla malesuada scelerisque.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-445CA", "Campana CD-60 N - Edesa", 205.20, "Campana", "Edesa", "Campana/campana1015.jpg",
                "Sed tempor sapien at augue pretium dignissim. Curabitur quis massa nunc, hendrerit commodo odio.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-446CA", "Campana CD-60 X - Edesa", 207.10, "Campana", "Edesa", "Campana/campana1022.jpg",
                "Morbi at lacus et orci dapibus aliquet. Vivamus non magna non enim scelerisque luctus.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Ede-447CA", "Campana CD-60 X - Edesa", 209.00, "Campana", "Edesa", "Campana/campana1018.jpg",
                "Donec vitae sem nec lorem posuere pellentesque non vitae erat. Quisque semper erat nec magna sagittis vehicula.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Fag-102HO", "Horno HM-480 X - Fagor", 241.50, "Horno", "Fagor", "Horno/horno163.jpg",
                "Quisque ut diam id ante tincidunt eleifend. Morbi at lacus et orci dapibus aliquet.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Fag-103HO", "Horno 2H-935 X - Fagor", 243.91, "Horno", "Fagor", "Horno/horno157.jpg",
                "Quisque ut diam id ante tincidunt eleifend. Vivamus non magna non enim scelerisque luctus.");

        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Fag-104HO", "Horno H-195 (AL)/(B/N) - Fagor", 246.74, "Horno", "Fagor", "Horno/horno152.jpg",
                "Ut eget nulla ac sapien convallis mollis in eget quam. Morbi at lacinia quam metus, vitae blandit purus.");
        jdbcTemplate.update("INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "Fag-105HO", "Horno H-500 AL - Fagor", 253.57, "Horno", "Fagor", "Horno/horno153.jpg",
                "Suspendisse dictum ullamcorper enim, nec lobortis pulvinar nec. Quisque ut diam id ante tincidunt eleifend.");

        // Configurar el DAO con el DataSource
        connectionManager = new ConnectionManagerDS(dataSource);
        articuloDAO = new ArticuloDaoMySQL(connectionManager);
        catalogoDAO = new CatalogoDaoMySQL(connectionManager);
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.execute("DROP TABLE articulo");
    }

    @Test
    public void testFindByPageReturns() throws ExcepcionDeAplicacion {
        List<Articulo> articulos = articuloDAO.findByPage(1, 8);
        assertEquals(8, articulos.size(), "Debería haber 8 artículos insertados");

        Articulo art1 = catalogoDAO.findOneByCodigo("Ede-430PA");
        Articulo art2 = catalogoDAO.findOneByCodigo("Ede-431PA");
        Articulo art3 = catalogoDAO.findOneByCodigo("Ede-432PA");
        Articulo art4 = catalogoDAO.findOneByCodigo("Ede-434PA");
        Articulo art5 = catalogoDAO.findOneByCodigo("Ede-435PA");
        Articulo art6 = catalogoDAO.findOneByCodigo("Ede-437CA");
        Articulo art7 = catalogoDAO.findOneByCodigo("Ede-438CA");
        Articulo art8 = catalogoDAO.findOneByCodigo("Ede-439CA");

        assertTrue(articulos.contains(art1), "La lista debería contener el artículo Ede-430PA");
        assertTrue(articulos.contains(art2), "La lista debería contener el artículo Ede-431PA");
        assertTrue(articulos.contains(art3), "La lista debería contener el artículo Ede-432PA");
        assertTrue(articulos.contains(art4), "La lista debería contener el artículo Ede-434PA");
        assertTrue(articulos.contains(art5), "La lista debería contener el artículo Ede-435PA");
        assertTrue(articulos.contains(art6), "La lista debería contener el artículo Ede-437CA");
        assertTrue(articulos.contains(art7), "La lista debería contener el artículo Ede-438CA");
        assertTrue(articulos.contains(art8), "La lista debería contener el artículo Ede-439CA");
    }

    @Test
    public void findByPagetest() throws ExcepcionDeAplicacion {
        List<Articulo> art = articuloDAO.findByPage(1, 12);
        assertEquals(12, art.size(), "El tamaño debería ser 12");

        Articulo art1 = catalogoDAO.findOneByCodigo("Ede-430PA");
        Articulo art2 = catalogoDAO.findOneByCodigo("Ede-431PA");
        Articulo art3 = catalogoDAO.findOneByCodigo("Ede-432PA");
        Articulo art4 = catalogoDAO.findOneByCodigo("Fag-103HO");

        assertTrue(art.contains(art1), "La lista debería contener el artículo Ede-430PA");
        assertTrue(art.contains(art2), "La lista debería contener el artículo Ede-431PA");
        assertTrue(art.contains(art3), "La lista debería contener el artículo Ede-432PA");
        assertFalse(art.contains(art4), "La lista no debería contener el artículo Fag-103HO");
    }

    @Test
    public void buildPageNumberPaginationTest() throws ExcepcionDeAplicacion {
        PageNumberPagination page = articuloDAO.buildPageNumberPagination(12, 1);

        assertEquals(20, page.getTotalCount(), "Debería haber 20 elementos");
        assertEquals(2, page.getTotalPages(), "Debería haber 2 páginas");
        assertEquals(null, page.previous(), "La página anterior debería ser null (estamos en la primera)");
        assertEquals(2, page.next(), "La página siguiente debería ser la 2");
    }


@Test
public void findAllFabricantesTest() throws ExcepcionDeAplicacion {
    FabricanteDaoMySQL fabricanteDAO = new FabricanteDaoMySQL(connectionManager);
    List<Fabricante> fabricantes = fabricanteDAO.findall();

    // En los datos de prueba tenemos: Edesa, Fagor
    assertNotNull(fabricantes, "La lista de fabricantes no debería ser null");
    assertEquals(2, fabricantes.size(), "Debería haber 2 fabricantes distintos");

    List<String> nombres = fabricantes.stream()
            .map(Fabricante::getNombre)
            .collect(java.util.stream.Collectors.toList());

    assertTrue(nombres.contains("Edesa"), "Debería contener el fabricante Edesa");
    assertTrue(nombres.contains("Fagor"), "Debería contener el fabricante Fagor");
}
}
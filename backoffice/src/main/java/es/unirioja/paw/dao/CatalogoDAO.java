package es.unirioja.paw.dao;

import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.util.List;

public interface CatalogoDAO {

    /**
     * @return Todos los articulos
     * @throws ExcepcionDeAplicacion
     */
    public List<Articulo> findAll() throws ExcepcionDeAplicacion;

    /**
     * @param codigo Código de artículo
     * @return Articulo
     * @throws ExcepcionDeAplicacion
     */
    public Articulo findOneByCodigo(String codigo) throws ExcepcionDeAplicacion;

    //metodo para modificar un articulo
    public void modificarArticulo(Articulo articulo)throws ExcepcionDeAplicacion;
}

package es.unirioja.paw.dao;

import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Pedido;
import java.util.List;

public interface PedidoDAO {

    public List<Pedido> findAll() throws ExcepcionDeAplicacion;
    
}
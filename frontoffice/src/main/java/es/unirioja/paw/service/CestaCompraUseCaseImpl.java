/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.service;

/**
 *
 * @author Santiago Die
 */
import es.unirioja.paw.jpa.ArticuloEntity;
import es.unirioja.paw.jpa.DireccionEntity;
import es.unirioja.paw.repository.PedidoRepository;
import es.unirioja.paw.jpa.LineaCestaCompraEntity;
import es.unirioja.paw.jpa.LineaPedidoEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.repository.ArticuloRepository;
import es.unirioja.paw.repository.CestaCompraRepository;
import es.unirioja.paw.service.data.AddToCartRequest;
import es.unirioja.paw.service.data.AddToCartResponse;
import es.unirioja.paw.service.data.FinalizarCompraRequest;
import es.unirioja.paw.service.data.FinalizarCompraResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CestaCompraUseCaseImpl implements CestaCompraUseCase {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CestaCompraRepository cestaRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Optional<AddToCartResponse> add(AddToCartRequest r) {
        if (r.cesta == null) {
            logger.warn("La cesta es null, no se puede añadir artículo");
            return Optional.empty();
        }

        // 1. Buscar el artículo en BD
        Optional<ArticuloEntity> articuloOpt = articuloRepository.findById(r.codigoArticulo);
        if (articuloOpt.isEmpty()) {
            logger.warn("Artículo no encontrado: {}", r.codigoArticulo);
            return Optional.empty();
        }
        ArticuloEntity articulo = articuloOpt.get();

        // 2. Buscar si el artículo ya está en la cesta
        Optional<LineaCestaCompraEntity> lineaExistente = r.cesta.findLineaArticulo(r.codigoArticulo);

        LineaCestaCompraEntity linea;
        if (lineaExistente.isPresent()) {
            // 2a. Ya estaba: incrementar cantidad
            linea = lineaExistente.get();
            linea.incrementCantidad(1);
            logger.info("Artículo {} ya en cesta, nueva cantidad: {}", r.codigoArticulo, linea.getCantidad());
        } else {
            // 2b. No estaba: crear nueva línea
            linea = new LineaCestaCompraEntity();
            linea.setArticulo(articulo);
            linea.setCantidad(1);
            linea.setPrecio(articulo.getPvp());
            // 3. Añadir a la cesta
            linea.setCesta(r.cesta);
            r.cesta.getLineas().add(linea);
            logger.info("Artículo {} añadido a la cesta", r.codigoArticulo);
        }

        // 4. Guardar en BD
        r.cesta = cestaRepository.save(r.cesta);
        return Optional.of(new AddToCartResponse(r.cesta, linea));
    }

    @Override
    public FinalizarCompraResponse finalizarCompra(FinalizarCompraRequest r) {
        // 1. Crear el pedido
        PedidoEntity pedido = new PedidoEntity();
        pedido.setCodigoCliente(r.cesta.getCodigoCliente());
        pedido.setFechacierre(new java.util.Date());
        pedido.setCursado(0);

        // 2. Crear dirección embebida
        DireccionEntity direccion = new DireccionEntity();
        direccion.setCalle(r.calle);
        direccion.setCp(r.codigoPostal);
        direccion.setCiudad(r.ciudad);
        direccion.setProvincia(r.provincia);
        pedido.setDireccion(direccion);

        // 3. Convertir líneas de cesta a líneas de pedido
        List<LineaPedidoEntity> lineasPedido = new ArrayList<>();
        for (LineaCestaCompraEntity lineaCesta : r.cesta.getLineas()) {
            LineaPedidoEntity linea = new LineaPedidoEntity();
            linea.setArticulo(lineaCesta.getArticulo());
            linea.setCantidad(lineaCesta.getCantidad());
            linea.setPrecioBase(lineaCesta.getPrecio());
            linea.setPrecioReal(lineaCesta.getPrecio().doubleValue() * lineaCesta.getCantidad());
            if (r.fechaEntregaDeseada != null) {
                linea.setFechaEntregaDeseada(java.sql.Date.valueOf(r.fechaEntregaDeseada));
            }
            linea.setPedido(pedido);
            lineasPedido.add(linea);
        }
        pedido.setLineas(lineasPedido);

        // 4. Guardar pedido en BD
        PedidoEntity savedPedido = pedidoRepository.save(pedido);
        logger.info("Pedido {} creado para cliente {}", savedPedido.getCodigo(), savedPedido.getCodigoCliente());

        // 5. Eliminar la cesta
        cestaRepository.delete(r.cesta);
        logger.info("Cesta eliminada tras confirmar pedido");

        return new FinalizarCompraResponse(savedPedido);
    }
}

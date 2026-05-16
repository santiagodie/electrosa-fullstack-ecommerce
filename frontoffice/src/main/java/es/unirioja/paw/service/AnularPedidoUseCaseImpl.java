/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.service;

import es.unirioja.paw.jpa.LineaPedidoEntity;
import es.unirioja.paw.jpa.LineaanuladaEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.jpa.PedidoanuladoEntity;
import es.unirioja.paw.repository.PedidoAnuladoRepository;
import es.unirioja.paw.repository.PedidoRepository;
import es.unirioja.paw.service.data.AnularPedidoRequest;
import es.unirioja.paw.service.data.AnularPedidoResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author juans
 */
@Service
public class AnularPedidoUseCaseImpl implements AnularPedidoUseCase {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoAnuladoRepository pedidoAnuladoRepository;

    @Override
    @Transactional
    public AnularPedidoResponse execute(AnularPedidoRequest request) {
        Optional<PedidoEntity> pedidoOptional = pedidoRepository.findById(request.pedidoId);
        PedidoEntity pedido;
        if (pedidoOptional.isPresent()) {
            pedido = pedidoOptional.get();
        } else {
            throw new IllegalArgumentException("Pedido no encontrado: " + request.pedidoId);
        }

        if (pedido.getCursado() != 0) {
            throw new IllegalStateException("No se puede anular un pedido cursado.");
        }

        PedidoanuladoEntity pedidoAnulado = new PedidoanuladoEntity();
        // pedidoAnulado.setCodigo(pedido.getCodigo()); ← ELIMINAR, JPA genera el UUID
        pedidoAnulado.setCodigoCliente(pedido.getCodigoCliente());
        pedidoAnulado.setFechaanulacion(new Timestamp(new Date().getTime()));
        pedidoAnulado.setFechacierre(new Timestamp(pedido.getFechacierre().getTime()));

        List<LineaanuladaEntity> lineasAnuladas = new ArrayList<>();
        for (LineaPedidoEntity linea : pedido.getLineas()) {
            LineaanuladaEntity anulada = new LineaanuladaEntity();
            anulada.setArticulo(linea.getArticulo());
            anulada.setCantidad(linea.getCantidad());
            anulada.setPedido(pedidoAnulado);
            lineasAnuladas.add(anulada);
        }
        pedidoAnulado.setLineas(lineasAnuladas);

        pedidoAnuladoRepository.save(pedidoAnulado);
        pedidoRepository.delete(pedido);

        return new AnularPedidoResponse(pedidoAnulado);
    }

}

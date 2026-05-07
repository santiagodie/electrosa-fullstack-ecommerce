/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.service;

import es.unirioja.paw.jpa.PedidoanuladoEntity;
import java.util.List;

/**
 *
 * @author juans
 */
public interface PedidoAnuladoService {
    
    public List<PedidoanuladoEntity> findAnuladosByCliente(String codigoCliente);

    public PedidoanuladoEntity findOne(String pedidoId);
}

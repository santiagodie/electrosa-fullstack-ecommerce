/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.service;

import es.unirioja.paw.jpa.PedidoanuladoEntity;
import es.unirioja.paw.repository.PedidoAnuladoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author juans
 */
@Service
public class PedidoAnuladoServiceImpl implements PedidoAnuladoService{
    
    private PedidoAnuladoRepository pedidoAnuladoRepository;
    
    @Autowired
    public PedidoAnuladoServiceImpl(PedidoAnuladoRepository par){
        this.pedidoAnuladoRepository = par;
    }

    @Override
    public List<PedidoanuladoEntity> findAnuladosByCliente(String codigoCliente) {
        List<PedidoanuladoEntity> itemCollection = pedidoAnuladoRepository.findByCodigoCliente(codigoCliente);
        return itemCollection;
    }

    @Override
    public PedidoanuladoEntity findOne(String pedidoId) {
        Optional<PedidoanuladoEntity> entity = pedidoAnuladoRepository.findById(pedidoId);
        if (entity.isPresent()) {
            return entity.get();
        }
        return null;
    }
    
}

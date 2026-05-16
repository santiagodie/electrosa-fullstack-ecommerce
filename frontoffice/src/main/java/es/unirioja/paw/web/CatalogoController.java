/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

/**
 *
 * @author Santiago Die
 */
import es.unirioja.paw.exception.ArticuloNotFoundException;
import es.unirioja.paw.jpa.ArticuloEntity;
import es.unirioja.paw.repository.ArticuloRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private ArticuloRepository articuloRepository;

    @GetMapping
    public String catalogo(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String fabricante,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String precio) {

        String fabricanteFiltro = (fabricante != null && !fabricante.isBlank()) ? fabricante : null;
        String tipoFiltro = (tipo != null && !tipo.isBlank()) ? tipo : null;
        BigDecimal precioMin = null;
        BigDecimal precioMax = null;
        if (precio != null && precio.contains("-")) {
            String[] partes = precio.split("-");
            precioMin = new BigDecimal(partes[0]);
            precioMax = new BigDecimal(partes[1]);
        }

        Pageable pageable = PageRequest.of(page, 15);
        Page<ArticuloEntity> pagina = articuloRepository.findWithFilters(
                fabricanteFiltro, tipoFiltro, precioMin, precioMax, pageable);

        model.addAttribute("articulos", pagina.getContent());
        model.addAttribute("totalPages", pagina.getTotalPages());
        model.addAttribute("currentPage", pagina.getNumber());
        model.addAttribute("totalElements", pagina.getTotalElements());
        model.addAttribute("fabricantes", articuloRepository.findDistinctFabricantes());
        model.addAttribute("tipos", articuloRepository.findDistinctTipos());
        model.addAttribute("fabricanteSeleccionado", fabricante != null ? fabricante : "");
        model.addAttribute("tipoSeleccionado", tipo != null ? tipo : "");
        model.addAttribute("precioSeleccionado", precio != null ? precio : "");

        return "catalogo";
    }

    @GetMapping("/{articuloId}")
    public String fichaArticulo(@PathVariable String articuloId, Model model) {
        Optional<ArticuloEntity> articuloOpt = articuloRepository.findById(articuloId);
        ArticuloEntity articulo = articuloOpt.orElseThrow(ArticuloNotFoundException::new);
        model.addAttribute("articulo", articulo);
        return "articulo";
    }
}

package es.unirioja.paw.web;

import es.unirioja.paw.exception.ArticuloNotFoundException;
import es.unirioja.paw.jpa.ArticuloEntity;
import es.unirioja.paw.repository.ArticuloRepository;
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
    public String catalogo(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<ArticuloEntity> pagina = articuloRepository.findAll(pageable);

        model.addAttribute("articulos", pagina.getContent());
        model.addAttribute("totalPages", pagina.getTotalPages());
        model.addAttribute("currentPage", pagina.getNumber());
        model.addAttribute("totalElements", pagina.getTotalElements());

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

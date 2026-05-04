package es.unirioja.paw.web;

import es.unirioja.paw.exception.ArticuloNotFoundException;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BlogController {

    private static final Set<String> SLUGS_VALIDOS = Set.of(
        "paw-2025",
        "tendencias-decorativas",
        "consejos-renovar-casa",
        "semana-verde"
    );

    @GetMapping(value = "/blog/{slug}")
    public String blogEntry(@PathVariable String slug, ModelMap model) {
        if (!SLUGS_VALIDOS.contains(slug)) {
            throw new ArticuloNotFoundException("Entrada de blog no encontrada: " + slug);
        }
        return "blog/" + slug;
    }

}

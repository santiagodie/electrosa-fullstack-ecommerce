/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

/**
 *
 * @author Santiago Die
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private static String texto = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur odio erat, sagittis eget auctor quis, convallis quis nunc. Integer eleifend orci et orci aliquet, id volutpat dolor rhoncus. Curabitur lacinia sapien eget nunc faucibus, ultricies accumsan ante laoreet. Suspendisse sagittis lacinia porttitor. Mauris quis odio eget lectus aliquam rhoncus. Suspendisse id ex aliquet, fringilla nunc eu, dapibus nisi. Praesent iaculis magna et lacus fringilla, ut fringilla elit accumsan. Maecenas volutpat, ex et pellentesque convallis, leo massa commodo felis, sed tincidunt neque nisi pretium neque. Nullam vel eros purus. Curabitur ultricies iaculis ex et rutrum. Duis consectetur turpis lacus, maximus sagittis eros ultricies nec. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Integer id odio orci. Mauris viverra consequat ligula, vel ultrices nibh pretium molestie.\n"
            + "\n"
            + "Mauris et volutpat eros. Aliquam accumsan vestibulum neque posuere sagittis. In felis ligula, egestas ut sapien ut, pellentesque gravida dolor. Donec accumsan elit vestibulum leo consequat, vitae ornare felis convallis. Donec volutpat ante sem, nec dignissim ligula dapibus vitae. Nulla tincidunt augue non tellus pellentesque ultricies. Maecenas fermentum ullamcorper suscipit. Sed efficitur metus in neque fermentum ultrices. Praesent sodales, eros iaculis porttitor rhoncus, velit lectus molestie ex, a accumsan nisl elit in quam. Aliquam dictum gravida ipsum eu viverra. Phasellus quis vehicula justo. Suspendisse vestibulum sodales commodo. Curabitur lobortis pharetra elit, in condimentum mi tempor id. Nunc imperdiet eros sit amet elit porta lacinia. Etiam eros nisl, ullamcorper sed odio quis, vehicula porttitor diam. Interdum et malesuada fames ac ante ipsum primis in faucibus.\n"
            + "\n"
            + "Nulla pulvinar lorem et consequat pharetra. Cras quis arcu at justo interdum suscipit vel ut metus. Suspendisse tristique dignissim lorem, eu rhoncus erat laoreet vitae. Nulla id neque at justo blandit elementum. Aliquam eu mauris justo. Curabitur egestas eget enim in dapibus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae;";
    private static final Map<String, String> CONTENIDOS = Map.of(
            "paw-2025", "Bienvenido al blog de PAW 2025." + "\n" + "   Por: Francisco " + "\n" + "   Publicado 1 octubre,2023" + "\n" + texto,
            "tendencias-decorativas", "Descubre las últimas tendencias decorativas para tu hogar." + "\n" + "   Por: Dustin" + "\n" + "   Publicado:26 Diciembre, 2024" + "\n" + texto,
            "consejos-renovar-casa", "Sigue estos consejos para renovar tu casa de forma económica y moderna." + "\n" + "   Por: Tormund " + "\n" + "   Publicado: 9 Marzo, 2025" + "\n" + texto,
            "semana-verde", "La Semana Verde trae descuentos y sostenibilidad a Electrosa." + "\n" + "   Por: Víctor " + "\n" + "   Publicado: 26 Enero, 2025" + "\n" + texto);

    @GetMapping({"", "/"})
    public String listaBlogs(Model model) {
        model.addAttribute("slugs", CONTENIDOS.keySet());
        return "blog-index";
    }

    @GetMapping("/{slug}")
    public String mostrarEntrada(@PathVariable String slug, Model model) {
        String contenido = CONTENIDOS.get(slug);
        if (contenido == null) {
            return "blog-notfound";
        }

        model.addAttribute("titulo", slug.replace("-", " ").toUpperCase());
        model.addAttribute("contenido", contenido);
        return "blog";
    }
}

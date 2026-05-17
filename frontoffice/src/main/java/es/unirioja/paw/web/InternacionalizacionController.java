/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

/**
 *
 * @author Santiago Die
 */
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

@Controller
@RequestMapping("/lang")
public class InternacionalizacionController {

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/prefs")
    public String prefs(Model model) {
        model.addAttribute("idiomas", List.of("es", "en", "fr", "de"));
        return "idioma";
    }

    @PostMapping("/prefs")
    public String setLocale(@RequestParam("lang") String lang, HttpServletRequest request, HttpServletResponse response) {
        localeResolver.setLocale(request, response, new Locale(lang));
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isBlank()) {
            return "redirect:" + referer;
        }
        return "redirect:/";
    }
}

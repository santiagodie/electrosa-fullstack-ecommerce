package es.unirioja.paw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping(value = "/")
    public String index(ModelMap model) {
        return "index";
    }

}

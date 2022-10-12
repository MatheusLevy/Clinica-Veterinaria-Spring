package com.produtos.apirest.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    // TODO: ### **Criar Página de Login**
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // TODO: ### **Criar Página de Erro**
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    // TODO: ### **Criar Logger de Erro para capturar o Erro **
}

package com.produtos.apirest.viewController;

import com.produtos.apirest.models.Dono;
import com.produtos.apirest.service.DonoService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ViewController {

    @Autowired
    public DonoService donoService;
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

package com.produtos.apirest.controller;

import com.produtos.apirest.models.Dono;
import com.produtos.apirest.service.DonoService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/dono/donoCadastro")
    public String dono_cadastro_page(){
        return "/dono/donoCadastro";
    }

    @PostMapping("/dono/donoCadastro")
    public String dono_cadastro_controll(Dono dono){
        if(Long.valueOf(dono.getDonoId()) == null){
            donoService.salvar(dono);
        }else {
            donoService.atualizar(dono);
        }
        return "redirect:/dono/donoList";
    }

    @GetMapping("/dono/donoList")
    public ModelAndView dono_list(){
        List<Dono> donos = donoService.buscarTodos();
        ModelAndView mv = new ModelAndView("/dono/donoList");
        mv.addObject( "donos", donos);
        System.out.println(mv);
        return mv;
    }

}

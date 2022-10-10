package com.produtos.apirest.viewController;

import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.service.UsarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsuarioViewController {

    @Autowired
    private UsarioService usarioService;

    @PreAuthorize("hasRole('A')")
    @GetMapping("/usuario/cadastro")
    public ModelAndView usuarioCadastro(){
        ModelAndView mv = new ModelAndView("usuarios/usuarioCadastro");
        Usuario usuario = Usuario.builder().build();
        mv.addObject("usuario", usuario);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @PostMapping("/usuario/cadastro")
    public String usuarioCadastroControll(Usuario usuario){

        if(usuario.getUsuarioId() == null){
            usarioService.salvar(usuario);
        }else {
            usarioService.atualizar(usuario);
        }
        return "redirect:/usuario/usuarioList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/usuario/atualizar/{id}")
    public ModelAndView usuarioAtualizar(@PathVariable(value = "id", required = true) Long id){

        Usuario usuario = Usuario.builder().usuarioId(id).build();
        Usuario usuarioFind = usarioService.buscarPorId(usuario);
        ModelAndView mv = new ModelAndView("/usuarios/usuarioCadastro");
        mv.addObject("usuario", usuarioFind);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/usuario/remover/{id}")
    public String usuarioRemover(@PathVariable(value = "id", required = true) Long id){
        Usuario usuario = Usuario.builder().usuarioId(id).build();
        Usuario usuarioFind = usarioService.buscarPorId(usuario);
        usarioService.remover(usuarioFind);
        return "redirect:/usuario/usuarioList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/usuario/usuarioList")
    public ModelAndView usuarioList(){
        List<Usuario> usuarios = usarioService.buscarTodos();
        List<Usuario> usuarioListDTO = new ArrayList<>();
        ModelAndView mv = new ModelAndView("/usuarios/usuarioList");
        mv.addObject("usuarios", usuarioListDTO);
        return mv;
    }
}

package com.produtos.apirest.controller;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //TODO: ### ** Substituir o Usuario por UsuarioDTO **
    @GetMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody Usuario usuario){
        try{
            Usuario atuenticado = usuarioService.autenticar(usuario.getUsername(), usuario.getSenha());
            return ResponseEntity.ok(atuenticado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir o Usuario por UsuarioDTO **
    @PreAuthorize("hasRole('A')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody Usuario usuario){
        try{
            Usuario usarioSalvo = usuarioService.salvar(usuario);
            return ResponseEntity.ok(usarioSalvo);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity removerComId(@PathVariable(value = "id", required = true) Long id){
        try{
            Usuario usuarioBuscado = usuarioService.buscarPorId(id);
            usuarioService.remover(usuarioBuscado);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir o Usuario por UsuarioDTO **
    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/feedback")
    public ResponseEntity removerComFeedback(@RequestBody Usuario usuario){
        try{
            Usuario usuarioRemovido = usuarioService.removerComFeedback(usuario.getUsuarioId());
            return ResponseEntity.ok(usuarioRemovido);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            Usuario usuarioBuscado = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuarioBuscado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscarPorUsername/{username}")
    public ResponseEntity buscarPorUsername(@PathVariable(value = "username", required = true) String username){
        try {
            Usuario usuario = usuarioService.buscarPorUsername(username);
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody Usuario usuario){
        try{
            Usuario atualizado = usuarioService.atualizar(usuario);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

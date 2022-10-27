package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.UsuarioDTO;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/autenticar")
    public ResponseEntity<?> autenticar(@RequestBody UsuarioDTO dto){
        try{
            Usuario usuarioAutenticado = usuarioService.autenticar(dto.getUsername(), dto.getSenha());
            UsuarioDTO dtoRetorno = usuarioAutenticado.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody UsuarioDTO dto){
        try{
            Usuario usuario =  dto.toUsuario();
            Usuario usuarioSalvo = usuarioService.salvar(usuario);
            UsuarioDTO dtoRetorno = usuarioSalvo.toDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removerComId(@PathVariable(value = "id") Long id){
        try{
            Usuario usuarioBuscado = usuarioService.buscarPorId(id);
            usuarioService.remover(usuarioBuscado);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            Usuario usuarioRemovido = usuarioService.removerComFeedback(id);
            UsuarioDTO dtoRetorno = usuarioRemovido.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try{
            Usuario usuarioBuscado = usuarioService.buscarPorId(id);
            UsuarioDTO dtoRetorno = usuarioBuscado.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/buscarPorUsername/{username}")
    public ResponseEntity<?> buscarPorUsername(@PathVariable(value = "username") String username){
        try {
            Usuario usuarioEncontrado = usuarioService.buscarPorUsername(username);
            UsuarioDTO dtoRetorno = usuarioEncontrado.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody UsuarioDTO dto){
        try{
            Usuario usuario = dto.toUsuario();
            Usuario atualizado = usuarioService.atualizar(usuario);
            UsuarioDTO dtoRetorno = atualizado.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
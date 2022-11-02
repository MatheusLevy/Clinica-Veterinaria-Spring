package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.UserDTO;
import com.produtos.apirest.models.User;
import com.produtos.apirest.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/autenticar")
    public ResponseEntity<?> autenticar(@RequestBody UserDTO dto){
        try{
            User usuarioAutenticado = usuarioService.authenticate(dto.getUsername(), dto.getPassword());
            UserDTO dtoRetorno = usuarioAutenticado.toUserDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody UserDTO dto){
        try{
            User usuario =  dto.toUser();
            User usuarioSalvo = usuarioService.save(usuario);
            UserDTO dtoRetorno = usuarioSalvo.toUserDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removerComId(@PathVariable(value = "id") Long id){
        try{
            User usuarioBuscado = usuarioService.findById(id);
            usuarioService.remove(usuarioBuscado);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            User usuarioRemovido = usuarioService.removeByIdWithFeedback(id);
            UserDTO dtoRetorno = usuarioRemovido.toUserDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try{
            User usuarioBuscado = usuarioService.findById(id);
            UserDTO dtoRetorno = usuarioBuscado.toUserDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/buscarPorUsername/{username}")
    public ResponseEntity<?> buscarPorUsername(@PathVariable(value = "username") String username){
        try {
            User usuarioEncontrado = usuarioService.findByUsername(username);
            UserDTO dtoRetorno = usuarioEncontrado.toUserDTO();
            return ResponseEntity.ok(dtoRetorno);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody UserDTO dto){
        try{
            User usuario = dto.toUser();
            User atualizado = usuarioService.update(usuario);
            UserDTO dtoRetorno = atualizado.toUserDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
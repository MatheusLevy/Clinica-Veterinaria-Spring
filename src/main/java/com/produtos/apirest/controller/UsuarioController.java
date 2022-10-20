package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.UsuarioDTO;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO dto){
        try{
            Usuario atuenticado = usuarioService.autenticar(dto.getUsername(), dto.getSenha());
            UsuarioDTO dtoRetorno = UsuarioDTO.builder()
                    .id(atuenticado.getUsuarioId())
                    .username(atuenticado.getUsername())
                    //.nivel(atuenticado.getNivel())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto){
        try{
            Usuario usuario =  Usuario.builder()
                    .username(dto.getUsername())
                    .senha(dto.getSenha())
                    .roles(dto.getRoles())
                    .build();
            Usuario usarioSalvo = usuarioService.salvar(usuario);

            UsuarioDTO dtoRetorno = UsuarioDTO.builder()
                    .id(usarioSalvo.getUsuarioId())
                    .username(usarioSalvo.getUsername())
                    .roles(usarioSalvo.getRoles())
                    .build();
            return new ResponseEntity(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


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


    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity removerComFeedback(@PathVariable(value = "id",required = true) Long id){
        try{
            Usuario usuarioRemovido = usuarioService.removerComFeedback(id);
            UsuarioDTO dtoRetorno = UsuarioDTO.builder()
                    .id(usuarioRemovido.getUsuarioId())
                    .username(usuarioRemovido.getUsername())
                    .roles(usuarioRemovido.getRoles())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            Usuario usuarioBuscado = usuarioService.buscarPorId(id);

            UsuarioDTO dtoRetorno = UsuarioDTO.builder()
                    .id(usuarioBuscado.getUsuarioId())
                    .username(usuarioBuscado.getUsername())
                    .roles(usuarioBuscado.getRoles())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/buscarPorUsername/{username}")
    public ResponseEntity buscarPorUsername(@PathVariable(value = "username", required = true) String username){
        try {
            Usuario usuario = usuarioService.buscarPorUsername(username);
            UsuarioDTO dtoRetorno = UsuarioDTO.builder()
                    .id(usuario.getUsuarioId())
                    .username(usuario.getUsername())
                    .roles(usuario.getRoles())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody UsuarioDTO dto){
        try{
            Usuario usuario = Usuario.builder()
                    .usuarioId(dto.getId())
                    .username(dto.getUsername())
                    .senha(dto.getSenha())
                    .roles(dto.getRoles())
                    .build();
            Usuario atualizado = usuarioService.atualizar(usuario);

            UsuarioDTO dtoRetorno = UsuarioDTO.builder()
                    .id(atualizado.getUsuarioId())
                    .username(atualizado.getUsername())
                    .roles(atualizado.getRoles())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

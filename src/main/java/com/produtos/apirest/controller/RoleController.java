package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.RoleDTO;
import com.produtos.apirest.models.Role;
import com.produtos.apirest.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody RoleDTO roleDTO){
        try {
            Role role = roleDTO.toRole();
            Role roleSalva = roleService.save(role);
            RoleDTO dtoRetorno = roleSalva.toRoleDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody RoleDTO roleDTO){
        try {
            Role role = roleDTO.toRole();
            Role roleAtualizada = roleService.update(role);
            RoleDTO dtoRetorno = roleAtualizada.toRoleDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id){
        try {
            roleService.removeById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id) {
        try {
            Role roleFeedback = roleService.removeByIdWithFeedback(id);
            RoleDTO dtoRetorno = roleFeedback.toRoleDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try {
            Role role = roleService.findById(id);
            RoleDTO dtoRetorno = role.toRoleDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
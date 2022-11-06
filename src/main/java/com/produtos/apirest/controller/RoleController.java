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

    @PostMapping
    public ResponseEntity<?> save(@RequestBody RoleDTO roleDTO){
        try {
            Role role = roleDTO.toRole();
            Role roleSaved = roleService.save(role);
            RoleDTO dtoResponse = roleSaved.toRoleDTO();
            return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody RoleDTO roleDTO){
        try {
            Role role = roleDTO.toRole();
            Role roleUpdated = roleService.update(role);
            RoleDTO dtoResponse = roleUpdated.toRoleDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(value = "id") Long id){
        try {
            roleService.removeById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id) {
        try {
            Role roleRemoved = roleService.removeByIdWithFeedback(id);
            RoleDTO dtoResponse = roleRemoved.toRoleDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        try {
            Role roleFind = roleService.findById(id);
            RoleDTO dtoResponse = roleFind.toRoleDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
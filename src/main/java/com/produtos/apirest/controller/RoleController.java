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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RoleDTO save(@RequestBody RoleDTO roleDTO){
        Role role = roleDTO.toRole();
        Role roleSaved = roleService.save(role);
        return roleSaved.toRoleDTO();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody RoleDTO roleDTO){
        Role role = roleDTO.toRole();
        Role roleUpdated = roleService.update(role);
        RoleDTO dtoResponse = roleUpdated.toRoleDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(value = "id") Long id){
        roleService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id) {
        Role roleRemoved = roleService.removeByIdWithFeedback(id);
        RoleDTO dtoResponse = roleRemoved.toRoleDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        Role roleFind = roleService.findById(id);
        RoleDTO dtoResponse = roleFind.toRoleDTO();
        return ResponseEntity.ok(dtoResponse);
    }
}
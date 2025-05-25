package com.UsuarioProgramming.User_Service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.UsuarioProgramming.User_Service.dto.UserServiceDto;
import com.UsuarioProgramming.User_Service.model.User;
import com.UsuarioProgramming.User_Service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService usuarioService;

    @PostMapping
    public ResponseEntity<User> crearUsuario(@Valid @RequestBody UserServiceDto usuarioDto) {
        // Convertir DTO a entidad User
        User nuevoUsuario = new User(
            0,  // id para que se genere automáticamente
            usuarioDto.getName(),
            usuarioDto.getLastName(),
            usuarioDto.getPassword(),
            usuarioDto.getEmail(),
            usuarioDto.getEdad(),
            usuarioDto.getPeso(),
            usuarioDto.getHeight()
        );

        User guardado = usuarioService.guardarUsuario(nuevoUsuario);
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<User> obtenerPorCorreo(@PathVariable String correo) {
        return usuarioService.obtenerPorCorreo(correo)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable int id) {
        boolean eliminado = usuarioService.eliminarUsuario(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> actualizarUsuario(
        @PathVariable int id,
        @Valid @RequestBody UserServiceDto usuarioDto) {

        User usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioDto);
        return ResponseEntity.ok(usuarioActualizado);
}
}
package com.UsuarioProgramming.User_Service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.UsuarioProgramming.User_Service.dto.UserServiceDto;
import com.UsuarioProgramming.User_Service.model.User;

@Service
public interface UserService {
    User guardarUsuario(User usuario);
    Optional<User> obtenerPorCorreo(String correo);
    List<User> obtenerTodos();

    boolean eliminarUsuario(int id);
    Optional<User> obtenerPorId(int id);
    User actualizarUsuario(int id, UserServiceDto usuarioDto);
}

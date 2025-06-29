package com.UsuarioProgramming.User_Service.service;

import org.springframework.stereotype.Service;

import com.UsuarioProgramming.User_Service.dto.UserServiceDto;
import com.UsuarioProgramming.User_Service.model.User;
import com.UsuarioProgramming.User_Service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository usuarioRepository;

    @Override
    public User guardarUsuario(User usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<User> obtenerPorCorreo(String correo) {
        return usuarioRepository.findByEmail(correo);
    }

    @Override
    public List<User> obtenerTodos() {
        return usuarioRepository.findAll();
    }
    
    @Override
    public boolean eliminarUsuario(int id) {
        User user = usuarioRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Usuario no existe"));
        usuarioRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<User> obtenerPorId(int id) {
    return usuarioRepository.findById(id);
    }

@Override
public User actualizarUsuario(int id, UserServiceDto usuarioDto) {
    User usuarioExistente = usuarioRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Usuario no existe"));

    if (usuarioDto.getName() != null) {
        usuarioExistente.setName(usuarioDto.getName());
    }
    if (usuarioDto.getUsername() != null) {
        usuarioExistente.setUsername(usuarioDto.getUsername());
    }
    if (usuarioDto.getPassword() != null) {
        usuarioExistente.setPassword(usuarioDto.getPassword());
    }
    if (usuarioDto.getEmail() != null) {
        usuarioExistente.setEmail(usuarioDto.getEmail());
    }
    if (usuarioDto.getEdad() != null) {
        usuarioExistente.setEdad(usuarioDto.getEdad());
    }
    return usuarioRepository.save(usuarioExistente);
}
}

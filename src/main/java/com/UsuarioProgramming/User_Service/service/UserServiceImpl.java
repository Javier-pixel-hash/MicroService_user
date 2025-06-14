package com.UsuarioProgramming.User_Service.service;

import org.springframework.stereotype.Service;

import com.UsuarioProgramming.User_Service.dto.UserServiceDto;
import com.UsuarioProgramming.User_Service.model.User;
import com.UsuarioProgramming.User_Service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.List;

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
        Optional<User> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    @Override
public Optional<User> obtenerPorId(int id) {
    return usuarioRepository.findById(id);
}

@Override
public User actualizarUsuario(int id, UserServiceDto usuarioDto) {
    User usuarioExistente = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no existe"));

    if (usuarioDto.getName() != null) {
        usuarioExistente.setName(usuarioDto.getName());
    }
    if (usuarioDto.getLastName() != null) {
        usuarioExistente.setLastName(usuarioDto.getLastName());
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

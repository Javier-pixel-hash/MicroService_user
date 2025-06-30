package com.UsuarioProgramming.User_Service;

import com.UsuarioProgramming.User_Service.controller.UserController;
import com.UsuarioProgramming.User_Service.dto.UserServiceDto;
import com.UsuarioProgramming.User_Service.model.User;
import com.UsuarioProgramming.User_Service.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserServiceDto userDto;
    private User user;

    @BeforeEach
    void setup() {
        userDto = new UserServiceDto("Juan Pérez", "juan123", "juancontrasena", "juanpass@email.com", 28);
        user = new User(1, "Juan Pérez", "juan123", "juancontrasena", "juan@email.com", 28);
    }

    @Test
    void testCrearUsuario() throws Exception {
        when(userService.guardarUsuario(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan@email.com"));
    }

    @Test
    void testObtenerTodos() throws Exception {
        List<User> users = List.of(user);
        when(userService.obtenerTodos()).thenReturn(users);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("juan123"));
    }

    @Test
    void testObtenerPorCorreo_Encontrado() throws Exception {
        when(userService.obtenerPorCorreo("juan@email.com")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/usuarios/correo/juan@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Pérez"));
    }

    @Test
    void testObtenerPorCorreo_NoEncontrado() throws Exception {
        when(userService.obtenerPorCorreo("no@existe.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/correo/no@existe.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarUsuario_Exitoso() throws Exception {
        when(userService.eliminarUsuario(1)).thenReturn(true);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarUsuario_NoExiste() throws Exception {
        when(userService.eliminarUsuario(1)).thenReturn(false);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarUsuario() throws Exception {
        when(userService.actualizarUsuario(eq(1), any(UserServiceDto.class))).thenReturn(user);

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("juan123"));
    }
}
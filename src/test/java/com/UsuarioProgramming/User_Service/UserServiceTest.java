package com.UsuarioProgramming.User_Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.UsuarioProgramming.User_Service.dto.UserServiceDto;
import com.UsuarioProgramming.User_Service.model.User;
import com.UsuarioProgramming.User_Service.repository.UserRepository;
import com.UsuarioProgramming.User_Service.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUser() {
        User user = new User(1, "pedrito", "pedrito123", "elmanolo1234567", "pedrito123@email.com",15);
        User savedUser = new User(1, "pedrito", "pedrito123", "elmanolo1234567", "pedrito123@email.com",15);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.guardarUsuario(user);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("pedrito", result.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById_Found() {
        User user = new User(1, "pedrito", "pedrito123", "elmanolo1234567", "pedrito123@email.com",15);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.obtenerPorId(1);

        assertTrue(result.isPresent());
        assertEquals("pedrito", result.get().getName());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Optional<User> result = userService.obtenerPorId(1);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(
            new User(1, "Nombre1", "username1", "email1@mail.com", "password1", 20),
            new User(2, "Nombre2", "username2", "email2@mail.com", "password2", 30)
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.obtenerTodos();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser_Found() {
        User existingUser = new User(1,"Old Name", "olduser", "old@mail.com", "oldpass", 25);
        UserServiceDto updatedUser = new UserServiceDto("New Name", "newuser", "new@mail.com", "newpass", 26);

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.actualizarUsuario(1, updatedUser);

        assertEquals("New Name", result.getName());
        assertEquals("newuser", result.getUsername());
        assertEquals(26, result.getEdad());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        UserServiceDto updatedUser = new UserServiceDto("New Name", "newuser", "new@mail.com", "newpass", 26);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.actualizarUsuario(1, updatedUser));

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testDeleteUser_Found() {
        User user = new User(1, "Nombre", "username", "email@mail.com", "password", 25);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1);

        userService.eliminarUsuario(1);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.eliminarUsuario(1));

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, never()).delete(any());
    }
}
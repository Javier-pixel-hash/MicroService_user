package com.UsuarioProgramming.User_Service.config;

import com.UsuarioProgramming.User_Service.model.User;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.UsuarioProgramming.User_Service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        // Solo insertar si la base de datos está vacía
        if (userRepository.count() == 0) {
            List<User> users = new ArrayList<>();
            for (int i = 0; i < 900; i++) {
                User user = new User();
                user.setName(faker.name().fullName());
                user.setUsername(faker.internet().username());
                user.setPassword(faker.internet().password(8, 16));
                user.setEmail(faker.internet().emailAddress());
                user.setEdad(faker.number().numberBetween(1, 100));
                users.add(user);
            }
            userRepository.saveAll(users);
            System.out.println("Datos generados exitosamente.");
        }
    }
}

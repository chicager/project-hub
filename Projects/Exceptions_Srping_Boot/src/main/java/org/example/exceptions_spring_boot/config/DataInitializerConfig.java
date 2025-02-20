package org.example.exceptions_spring_boot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions_spring_boot.model.User;
import org.example.exceptions_spring_boot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializerConfig {

    private final UserRepository userRepository;

    //CommandLineRunner - интерфейс Spring Boot, который выполняется сразу после старта приложения
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            log.info("Starting data initialization...");

            var user1 = User.builder()
                .name("Иван Петров")
                .email("ivan@example.com")
                .age(25)
                .build();
            userRepository.save(user1);

            var user2 = User.builder()
                    .name("Мария Сидорова")
                    .email("maria@example.com")
                    .age(30)
                    .build();
            userRepository.save(user2);

            var user3 = User.builder()
                    .name("Александр Иванов")
                    .email("alex@example.com")
                    .age(35)
                    .build();
            userRepository.save(user3);

            var user4 = User.builder()
                    .name("Елена Смирнова")
                    .email("elena@example.com")
                    .age(28)
                    .build();
            userRepository.save(user4);

            var user5 = User.builder()
                    .name("Дмитрий Козлов")
                    .email("dmitry@example.com")
                    .age(40)
                    .build();
            userRepository.save(user5);

            log.info("Data initialization completed. Created {} users", userRepository.count());
        };
    }
}

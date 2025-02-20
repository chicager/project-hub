package org.example.exceptions_spring_boot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions_spring_boot.exception.UserEmailExistsException;
import org.example.exceptions_spring_boot.exception.UserNotFoundException;
import org.example.exceptions_spring_boot.model.User;
import org.example.exceptions_spring_boot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /*
    Преимущества readOnly = true:
      База данных оптимизирует запросы для чтения
      Не создаются блокировки для записи
      Не отслеживаются изменения сущностей

    Когда использовать:
      Для методов, которые только читают данные
      Для методов, где не ожидается изменений в БД
      Когда важна производительность
      Когда нужно явно показать, что метод безопасен
    Когда НЕ использовать:
      Для методов, которые изменяют данные
      Когда нужно отслеживать изменения сущностей
      Когда требуется поддержка транзакционных блокировок
    */
    //Здесь транзакция не нужна, просто написана для примера
    @Transactional(readOnly = true) // Только чтение
    public User getUser(Long id) {
        log.debug("Getting user with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    //Здесь транзакция не нужна, просто написана для примера
    @Transactional // Чтение и запись
    public User createUser(User user) {
        log.debug("Creating new user: {}", user);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserEmailExistsException(user.getEmail());
        }
        return userRepository.save(user);
    }
}

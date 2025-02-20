package org.example.exceptions_spring_boot.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

//@Data для Entity лучше не использовать
//@EqualsAndHashCode для Entity лучше не использовать

@Entity
@Getter
@Setter
@Table(name = "t_user")
@ToString(exclude = "id")  // toString без определенных полей
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) //обычно всегда делают protected, для безопасности
@Builder(toBuilder = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @Email(message = "Некорректный email")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @Min(value = 18, message = "Возраст должен быть не менее 18 лет")
    private int age;

    /*
    Сравнивает только ID
    Учитывает случай, когда ID равен null (для новых объектов)
    Не вызывает загрузку связанных сущностей
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id != null && id.equals(user.getId());
    }

    /*
    Почему такая реализация лучше:
      Для сохраненных сущностей (с id):
        hashCode() будет основан на id
        Разные объекты будут иметь разные хэш-коды
        Одинаковые сущности (с одним id) будут иметь одинаковый хэш-код
      Для новых сущностей (id == null):
        hashCode() будет использовать реализацию из Object (super.hashCode())
        Это гарантирует уникальный хэш-код для каждого нового объекта
        Предотвращает коллизии в HashSet/HashMap для несохраненных сущностей
    Это важно, потому что:
        Сохраненные сущности с одинаковым id должны считаться равными
        Новые сущности (до сохранения) должны считаться разными объектами
        При добавлении в HashSet/HashMap объекты будут корректно распределяться

    Пример использования:
    User user1 = new User("John", "john@example.com", 25); // id == null
    User user2 = new User("John", "john@example.com", 25); // id == null
    User user3 = userRepository.save(user1); // id != null
    User user4 = userRepository.findById(user3.getId()).get(); // тот же id

    Set<User> users = new HashSet<>();
    users.add(user1); // OK, уникальный hashCode от Object
    users.add(user2); // OK, другой уникальный hashCode от Object
    users.add(user3); // OK, hashCode от id
    users.add(user4); // Не добавится, так как equals() вернет true (тот же id)
    */
    @Override
    public int hashCode() {
        // Используем id если он есть, иначе используем super.hashCode()
        return id != null ? id.hashCode() : super.hashCode();
    }
}

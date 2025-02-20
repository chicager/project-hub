package org.example.exceptions_spring_boot.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/*
Этот класс ErrorResponse используется для формирования структурированного ответа
об ошибке в REST API. Когда происходит ошибка, создается объект этого класса,
заполняется соответствующей информацией и отправляется клиенту.
Это помогает клиенту понять:
  Когда произошла ошибка (timestamp)
  Что именно пошло не так (message)
  Какой тип ошибки (code)
  Где произошла ошибка (path)
*/
@Data
@Builder(toBuilder = true)
public class ErrorResponse {

    private LocalDateTime timestamp; // время возникновения ошибки
    private String message; // текст сообщения об ошибке
    private String code; // код ошибки (например, "ERR001" или "404")
    private String path; // путь URL, на котором произошла ошибка
}

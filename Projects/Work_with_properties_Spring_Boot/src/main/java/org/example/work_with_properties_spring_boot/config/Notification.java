package org.example.work_with_properties_spring_boot.config;

import lombok.Data;

@Data
public class Notification {
    private Email email;

    //вложенный класс здесь просто для примера, его также можно вынести в отдельный класс
    @Data
    public static class Email {
        private String host;
        private int port;
        private String from;
    }
}

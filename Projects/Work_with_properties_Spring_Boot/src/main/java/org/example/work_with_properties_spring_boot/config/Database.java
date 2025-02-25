package org.example.work_with_properties_spring_boot.config;

import lombok.Data;

@Data
public class Database {
    private String url;
    private String username;
    private int timeout;
}

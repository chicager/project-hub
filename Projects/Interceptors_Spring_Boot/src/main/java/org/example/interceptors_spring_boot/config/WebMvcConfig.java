package org.example.interceptors_spring_boot.config;

import lombok.RequiredArgsConstructor;
import org.example.interceptors_spring_boot.interceptor.AuthenticationInterceptor;
import org.example.interceptors_spring_boot.interceptor.LoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
WebMvcConfigurer - это интерфейс Spring MVC, который позволяет настраивать различные
аспекты Spring MVC через Java-код, без использования XML-конфигурации.
Основные методы, которые можно переопределить:
  Перехватчики
  Форматтеры и конвертеры
  Обработка статических ресурсов
  Аргументы и возвращаемые значения

Преимущества использования WebMvcConfigurer:
  Конфигурация через код вместо XML
  Типобезопасность
  Возможность использовать условную логику
  Легкое тестирование
  Автодополнение в IDE
*/
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Логирующий перехватчик для всех запросов
        registry.addInterceptor(loggingInterceptor);

        // Перехватчик аутентификации только для API запросов
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/api/**");
    }
}

package com.example.greeter.autoconfigure;

import com.example.greeter.Greeter;
import com.example.greeter.formatter.GreetingFormatter;
import com.example.greeter.formatter.HtmlFormatter;
import com.example.greeter.formatter.JsonFormatter;
import com.example.greeter.formatter.TextFormatter;
import com.example.greeter.template.GreetingTemplate;
import com.example.greeter.template.SimpleGreetingTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/*
@EnableConfigurationProperties(GreeterProperties.class) говорит Spring, что нужно:
  Создать бин класса GreeterProperties (т. е. без этой аннотации Spring не создаст экземпляр этого класса и не заполнит его значениями из конфигурационного файла.)
  Привязать к нему свойства из файлов конфигурации (например, application.yml или application.properties)
  Сделать этот бин доступным для внедрения в другие компоненты
*/

@Configuration
@EnableConfigurationProperties(GreeterProperties.class)
public class GreeterAutoConfiguration {

    /*
    Эти форматтеры будут автоматически собраны спрингом в List<GreetingFormatter>
    для передачи в конструктор создания бина Greeter
     */
    @Bean
    public TextFormatter textFormatter(GreeterProperties properties) {
        return new TextFormatter(properties.getFormatting().getDatetimePattern());
    }

    @Bean
    public HtmlFormatter htmlFormatter(GreeterProperties properties) {
        GreeterProperties.HtmlProperties htmlProps = properties.getFormatting().getHtml();
        return new HtmlFormatter(
                properties.getFormatting().getDatetimePattern(),
                htmlProps.getWrapperClass(),
                htmlProps.getMessageClass(),
                htmlProps.getMetadataClass()
        );
    }

    @Bean
    public JsonFormatter jsonFormatter(GreeterProperties properties) {
        GreeterProperties.JsonProperties jsonProps = properties.getFormatting().getJson();
        return new JsonFormatter(
                properties.getFormatting().getDatetimePattern(),
                jsonProps.isIncludeMetadata(),
                jsonProps.isPrettyPrint()
        );
    }

    @Bean
    public GreetingTemplate greetingTemplate(GreeterProperties properties) {
        return new SimpleGreetingTemplate(properties.getPrefix(), properties.getSuffix());
    }

    /*
    Когда Spring создает бин Greeter, он:
      1. Видит, что нужен List<GreetingFormatter>
      2. Находит все бины, которые реализуют интерфейс GreetingFormatter
      3. В нашем случае это TextFormatter, HtmlFormatter и JsonFormatter
      4. Собирает их в список
      5. Передает этот список в конструктор Greeter
    Это стандартный механизм Spring для внедрения коллекций бинов.
    Можно также явно контролировать порядок бинов в списке с помощью аннотации @Order
    или интерфейса Ordered, если это необходимо.
    */
    @Bean
    public Greeter greeter(GreetingTemplate template, List<GreetingFormatter> formatters) {
        return new Greeter(template, formatters);
    }
}

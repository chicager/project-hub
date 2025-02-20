package org.example.exceptions_spring_boot.config;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

//Форматтер, используется в spy.properties, для красивого вывода SQL в консоль
public class P6SpyFormatter implements MessageFormattingStrategy {
    private static final long SLOW_QUERY_THRESHOLD = 1000; // порог в миллисекундах (1 секунда)

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (sql == null || sql.trim().isEmpty()) {
            return "";
        }

        if (category.equals("statement")) {
            StringBuilder message = new StringBuilder()
                    .append("\u001B[33m");  // желтый цвет

            // Добавляем предупреждение для медленных запросов
            if (elapsed > SLOW_QUERY_THRESHOLD) {
                message.append("\u001B[31m*** SLOW QUERY *** ").append(elapsed).append("ms\u001B[33m\n");
            }

            // Для prepared statements показываем оба варианта запроса
            if (prepared != null && !prepared.isEmpty() && !prepared.equals(sql)) {
                message.append(String.format("%dms | Original SQL: %s\n", elapsed, sql))
                        .append(String.format("    | Prepared SQL: %s", prepared));
            } else {
                message.append(String.format("%dms | %s", elapsed, sql));
            }

            message.append("\u001B[0m");  // сброс цвета
            return message.toString();
        }

        return "";
    }
}

/*
Можно использовать такой вариант, но нужно тогда так же расскоментить код в spy.properties
Тут нет разделения на OriginalSQL и PreparedSQL, а так же нет предупреждения о медленных запросах

public class P6SpyFormatter implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (sql == null || sql.trim().isEmpty()) {
            return "";
        }
        // Используем sql для запросов с параметрами
        if (category.equals("statement")) {
            return String.format("\u001B[33m%dms | %s\u001B[0m", elapsed, sql);
        }
        // Для всех остальных случаев возвращаем пустую строку
        return "";
    }
}

*/
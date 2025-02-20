package org.example.interceptors_spring_boot.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
HandlerInterceptor - это интерфейс Spring MVC,
который позволяет перехватывать HTTP-запросы до и после их обработки контроллером.
Работает на уровне Spring MVC
Имеет три метода:
  preHandle() - до обработки запроса
  postHandle() - после обработки, но до рендеринга view
  afterCompletion() - после полного завершения запроса
*/
@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Перед выполнением запроса: {} {}", request.getMethod(), request.getRequestURI());
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("После выполнения запроса: {} {}", request.getMethod(), request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        log.info("Время выполнения запроса: {} мс", endTime - startTime);
        if (ex != null) {
            log.error("Произошла ошибка при выполнении запроса: {}", ex.getMessage());
        }
    }
}

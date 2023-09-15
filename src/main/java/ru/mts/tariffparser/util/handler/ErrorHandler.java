package ru.mts.tariffparser.util.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mts.tariffparser.util.exception.ParseFailException;

import java.time.LocalDateTime;

import static ru.mts.tariffparser.util.Constants.FORMATTER;

@Slf4j
@RestControllerAdvice("ru.mts.tariffparser")
public class ErrorHandler {
    @ExceptionHandler({ParseFailException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleInternalError(final Throwable e) {
        String errorStatus = "Внутренняя ошибка сервера";
        String errorReason = "Ошибка парсинга данных";
        String errorMessage = e.getMessage();
        String errorTimestamp = LocalDateTime.now().format(FORMATTER);

        log.warn("{}. {}", errorReason, errorMessage);
        return new Error(errorStatus, errorReason, errorMessage, errorTimestamp);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleUnexpectedError(final Throwable e) {
        String errorStatus = "Внутренняя ошибка сервера";
        String errorReason = "Непредвиденная ошибка сервера";
        String errorMessage = e.getMessage();
        String errorTimestamp = LocalDateTime.now().format(FORMATTER);

        log.warn("{}. {}", errorReason, errorMessage);
        return new Error(errorStatus, errorReason, errorMessage, errorTimestamp);
    }
}
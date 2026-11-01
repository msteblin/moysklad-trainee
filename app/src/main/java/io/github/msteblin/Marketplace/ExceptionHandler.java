package io.github.msteblin.Marketplace;

import io.github.msteblin.Marketplace.exception.ErrorResponse;
import io.github.msteblin.Marketplace.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
@SuppressWarnings("unused")
@Slf4j
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException exception) {
        // TODO: commonize
        StringWriter s = new StringWriter();
        exception.printStackTrace(new PrintWriter(s));
        log.trace("Stack trace: {}", s);
        return new ErrorResponse("not found", exception.getMessage());
    }
}

package io.github.msteblin.Marketplace.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
        log.trace("Stack trace: {}", Arrays.toString(getStackTrace()));
    }
}

package com.comarch.fbi.internship.todolg.model.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Wyjątek do obsługi 404.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RestNotFoundException extends RuntimeException {

    /**
     * Konstruktor.
     *
     * @param message opis błędu.
     */
    public RestNotFoundException(final String message) {
        super(message);
    }

}
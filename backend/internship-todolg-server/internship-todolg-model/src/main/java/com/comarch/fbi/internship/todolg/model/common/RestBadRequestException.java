package com.comarch.fbi.internship.todolg.model.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Wyjątek do obsługi złych danych wejsciowych.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RestBadRequestException extends RuntimeException {

    /**
     * Konstruktor.
     *
     * @param message opis błędu.
     */
    public RestBadRequestException(final String message) {
        super(message);
    }
}
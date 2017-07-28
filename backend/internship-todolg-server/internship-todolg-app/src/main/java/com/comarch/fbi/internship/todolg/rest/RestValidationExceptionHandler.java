package com.comarch.fbi.internship.todolg.rest;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.comarch.fbi.internship.todolg.endpoints.api.dto.BaseResponse;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.ResponseMessages;

/**
 * Asystent dla kontrolerów REST.
 * <p>
 * Umożliwia między innymi przechwytywanie wyjątków.
 */
@ControllerAdvice
public class RestValidationExceptionHandler {

    /**
     * Obsługa błędów walidacyjnych (opartych na adnotacjach).
     *
     * @param violationException - wyjątek z informacjami po-walidacyjnymi
     *
     * @return - odpowiedź dla klienta HTTP
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleValidationErrors(
            final ConstraintViolationException violationException) {
        BaseResponse defaultResult = new BaseResponse();
        final ResponseMessages msg = new ResponseMessages();
        msg.setType(ResponseMessages.TypeEnum.ERROR);

        violationException.getConstraintViolations().forEach(error ->
                msg.addMetaItem(error.getPropertyPath() + " " + error.getMessage()));

        defaultResult.addMessagesItem(msg);
        return ResponseEntity.badRequest().body(defaultResult);
    }
}
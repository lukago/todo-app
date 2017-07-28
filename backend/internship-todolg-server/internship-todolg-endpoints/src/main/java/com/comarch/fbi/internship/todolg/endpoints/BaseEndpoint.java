package com.comarch.fbi.internship.todolg.endpoints;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Klasa abstrakcyjna dla endpointów.
 * Pozwala na zdefiniowanie adresu, pod którym będą dostępne usługi.
 */
@RequestMapping("${api.path}")
@CrossOrigin(origins = "*", maxAge = 3600)
public abstract class BaseEndpoint {
}
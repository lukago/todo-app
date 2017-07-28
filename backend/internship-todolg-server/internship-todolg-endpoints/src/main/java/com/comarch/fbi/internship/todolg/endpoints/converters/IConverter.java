package com.comarch.fbi.internship.todolg.endpoints.converters;

/**
 * Interfejs konwertera obiektów.
 *
 * @param <T> Typ obiektu źródłowego.
 * @param <U> Typ obiektu docelowego.
 */
public interface IConverter<T, U> {

    /**
     * Konwertuje podany obiekt źródłowy na obiekt docelowy.
     *
     * @param object Obiekt źródłowy (do konwersji).
     *
     * @return Obiekt docelowy (obiekt skonwertowany).
     */
    U convert(T object);

}
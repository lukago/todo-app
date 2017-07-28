package com.comarch.fbi.internship.todolg.model.repositories.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.querydsl.core.types.EntityPath;

/**
 * Klasa pomocnicza znajdująca ścieżkę do encji.
 *
 * @param <T> typ encji
 */
public class EntityPathResolver<T> {

    private static final String NO_CLASS_FOUND_TEMPLATE = "Did not find a query class %s for "
            + "domain class %s!";
    private static final String NO_FIELD_FOUND_TEMPLATE = "Did not find a static field of the "
            + "same type in %s!";

    /**
     * Creates an {@link EntityPath} instance for the given domain class. Tries to lookup a class
     * matching the naming
     * convention (prepend Q to the simple name of the class, same package) and find a static
     * field of the same type in
     * it.
     *
     * @param domainClass typ domenowy encji
     *
     * @return ścieżka do encji
     */
    @SuppressWarnings("unchecked")
    public EntityPath<T> createPath(final Class<T> domainClass) {
        String pathClassName = getQueryClassName(domainClass);

        try {
            Class<?> pathClass = ClassUtils.forName(pathClassName, domainClass.getClassLoader());
            Field field = getStaticFieldOfType(pathClass);

            if (field == null) {
                throw new IllegalStateException(String.format(NO_FIELD_FOUND_TEMPLATE, pathClass));
            }
            else {
                return (EntityPath<T>) ReflectionUtils.getField(field, null);
            }

        }
        catch (final ClassNotFoundException e) {
            throw new IllegalArgumentException(
                    String.format(NO_CLASS_FOUND_TEMPLATE, pathClassName, domainClass.getName()),
                    e);
        }
    }

    /**
     * Returns the first static field of the given type inside the given type.
     *
     * @param type
     *
     * @return
     */
    private Field getStaticFieldOfType(final Class<?> type) {
        for (Field field : type.getDeclaredFields()) {
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            boolean hasSameType = type.equals(field.getType());

            if (isStatic && hasSameType) {
                return field;
            }
        }

        Class<?> superclass = type.getSuperclass();
        return Object.class.equals(superclass) ? null : getStaticFieldOfType(superclass);
    }

    /**
     * Returns the name of the query class for the given domain class.
     *
     * @param domainClass
     *
     * @return
     */
    private String getQueryClassName(final Class<?> domainClass) {
        String simpleClassName = ClassUtils.getShortName(domainClass);
        return String.format("%s.Q%s%s", domainClass.getPackage().getName(),
                getClassBase(simpleClassName), domainClass.getSimpleName());
    }

    /**
     * Analyzes the short class name and potentially returns the outer class.
     *
     * @param shortName
     *
     * @return
     */
    private String getClassBase(final String shortName) {
        String[] parts = shortName.split("\\.");

        if (parts.length < 2) {
            return "";
        }

        return parts[0] + "_";
    }
}
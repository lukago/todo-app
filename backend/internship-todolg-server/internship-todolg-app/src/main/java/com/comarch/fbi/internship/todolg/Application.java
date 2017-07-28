package com.comarch.fbi.internship.todolg;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Główny klasa aplikacji serwerowej.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    /**
     * Główna metoda aplikacji serwerowej.
     *
     * @param args argumenty przekazane do aplikacji w momencie uruchomiania
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    /**
     * Inicjalizacja aplikacji.
     */
    @PostConstruct
    public void init() {
        log.info("Spring Application starts successfully.");
    }
}
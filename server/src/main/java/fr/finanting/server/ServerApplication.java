package fr.finanting.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Launcher of the application
 * @author Jean-Baptiste WITTNER
 */
@SpringBootApplication
public class WebApplication {

    /**
     * Main of the application
     */
    public static void main(final String[] args) {

        SpringApplication.run(WebApplication.class, args);

    }

}
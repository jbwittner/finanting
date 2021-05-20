package fr.finanting.server.tools.handler;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import fr.finanting.server.exception.FunctionalException;

/**
 * Class used to manage the exception and transfert the informations with the answer of the REST request
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Method used to manage the exception and transfert the informations with the answer of the REST request
     */
    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<?> globleExcpetionHandler(final Exception ex, final WebRequest request) throws Exception {

        ErrorDetails errorDetails;

        if(ex instanceof FunctionalException){
            
            this.logger.info("Handling - exception : " + ex.getClass().getSimpleName() + " / message : " + ex.getMessage());

            errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false), ex.getClass().getSimpleName());
            
        } else if(ex instanceof UndeclaredThrowableException) {

            final UndeclaredThrowableException undeclaredThrowableException = (UndeclaredThrowableException) ex;
            
            final Throwable throwable = undeclaredThrowableException.getUndeclaredThrowable();

            if(throwable instanceof FunctionalException){
                this.logger.info("Handling - exception : " + throwable.getClass().getSimpleName() + " / message : " + throwable.getMessage());
                errorDetails = new ErrorDetails(new Date(), throwable.getMessage(), request.getDescription(false), throwable.getClass().getSimpleName());
            }else {
                this.logger.info("Handling - exception : " + ex.getClass().getSimpleName() + " / message : " + ex.getMessage());
                throw ex;
            }
            
        } else {
            throw ex;
        }

        return new ResponseEntity<>(errorDetails, HttpStatus.FAILED_DEPENDENCY);
        
    }
}
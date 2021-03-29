package fr.finanting.server.aop.interceptor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;

import fr.finanting.server.exception.ValidationDataException;
import fr.finanting.server.validation.InputServiceValidator;

public class GeneralInterceptor {

    public Object logExecutionTime(final ProceedingJoinPoint joinPoint, final Logger logger) throws Throwable {
        final long start = System.currentTimeMillis();
        final List<Object> list = Arrays.asList(joinPoint.getArgs());
        final Iterator<Object> iterator =  list.iterator();

        String method = joinPoint.getSignature().getDeclaringTypeName();
        method += "." + joinPoint.getSignature().getName();

        logger.info("ENTERING :: " + method);        

        Object object;

        while(iterator.hasNext()){
            object = iterator.next();
            logger.debug("Args list :: " + method + " :: " + object.toString());
        }
        
        final Object proceed = joinPoint.proceed();

        final long executionTime = System.currentTimeMillis() - start;

        logger.info("EXITING :: " + method + " executed in " + executionTime + "ms");

        return proceed;
    }

    protected void validationInputData(final JoinPoint joinPoint, final Logger logger) throws ValidationDataException {

        final List<Object> list = Arrays.asList(joinPoint.getArgs());
        final Iterator<Object> iterator = list.iterator();

        String method = joinPoint.getSignature().getDeclaringTypeName();
        method += "." + joinPoint.getSignature().getName();

        Object object;

        final InputServiceValidator<Object> validator = new InputServiceValidator<>();

        logger.info("INPUT VALIDATION :: " + method);

        while (iterator.hasNext()) {
            object = iterator.next();
            logger.debug("VALIDATION OF :: " + object.toString());
            validator.validate(object);
        }

    }

}
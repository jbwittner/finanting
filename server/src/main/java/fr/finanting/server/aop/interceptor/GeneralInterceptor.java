package fr.finanting.server.aop.interceptor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;

/**
 * Mother class for the Interceptor
 */
public class GeneralInterceptor {

    /**
     * General execution logger time
     * @param joinPoint Event intercepted by the aop
     * @param logger Logger used to log informations
     * @return Proceed of the event
     * @throws Throwable
     */
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

}
package fr.finanting.server.aop.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Interceptor for Repositories
 */
@Aspect
@Component
public class RepositoriesInterceptor extends GeneralInterceptor {

    Logger logger = LoggerFactory.getLogger(RepositoriesInterceptor.class);

    /**
     * Log interceptor to log the methode, the time to proceed and the arguments of
     * repositories methods
     * @param joinPoint Event intercepted by the aop
     * @return Proceed of the event
     * @throws Throwable
     */
    @Around("execution(* fr.finanting.server.repository.*.*(..))")
    public Object logInterceptor(final ProceedingJoinPoint joinPoint) throws Throwable {

        final Object proceed = this.logExecutionTime(joinPoint, this.logger);

        return proceed;
    }

}
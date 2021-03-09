package fr.finanting.server.aop.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.finanting.server.exception.ValidationDataException;

/**
 * Interceptor for services
 */
@Aspect
@Component
public class ServiceInterceptor extends GeneralInterceptor {

    protected final Logger logger = LoggerFactory.getLogger(ServiceInterceptor.class);

    /**
     * Log interceptor to log the method, the time to proceed and the arguments of
     * repositories methods
     * @param joinPoint Event intercepted by the aop
     * @return Proceed of the event
     * @throws Throwable
     */
    @Around("execution(* fr.finanting.server.*.*.*(..))" +
            "&& !execution(* org.springframework.data.repository.CrudRepository.*(..))" +
            "&& !execution(* org.springframework.data.jpa.repository.JpaRepository.*(..))")
    public Object logInterceptor(final ProceedingJoinPoint joinPoint) throws Throwable {
        return this.logExecutionTime(joinPoint, this.logger);
    }

    /**
     * Interceptor used to check the input data validity
     */
    @Before("execution(* fr.opendoha.myguild.server.service.*.*(..))")
    public void validationInterceptor(final JoinPoint joinPoint) throws ValidationDataException {
        this.validationInputData(joinPoint, this.logger);
    }

}
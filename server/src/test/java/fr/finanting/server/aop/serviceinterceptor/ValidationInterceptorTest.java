package fr.finanting.server.aop.serviceinterceptor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import fr.finanting.server.aop.interceptor.ServiceInterceptor;
import fr.finanting.server.exception.ValidationDataException;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import lombok.Data;

public class ValidationInterceptorTest extends AbstractMotherIntegrationTest {

    @Mock
    protected JoinPoint joinPoint;

    @Override
    protected void initDataBeforeEach() {}

    @Test
    public void validationOk() throws ValidationDataException {
        final ServiceInterceptor serviceInterceptor = new ServiceInterceptor();

        final ObjectToValidate objectToValidate = new ObjectToValidate();
        objectToValidate.setEmail(this.testFactory.getUniqueRandomEmail());
        objectToValidate.setName(this.testFactory.getRandomAlphanumericString());

        final Object[] objects = new Object[1];
        objects[0] = objectToValidate;
        Mockito.when(joinPoint.getArgs()).thenReturn(objects);

        final SignatureImpl signature = new SignatureImpl();

        Mockito.when(joinPoint.getSignature()).thenReturn(signature);

        serviceInterceptor.validationInterceptor(this.joinPoint);
    }

    @Test
    public void validationFail() throws ValidationDataException {
        final ServiceInterceptor serviceInterceptor = new ServiceInterceptor();

        final ObjectToValidate objectToValidate = new ObjectToValidate();
        objectToValidate.setName("");

        final Object[] objects = new Object[1];
        objects[0] = objectToValidate;
        Mockito.when(joinPoint.getArgs()).thenReturn(objects);

        final SignatureImpl signature = new SignatureImpl();

        Mockito.when(joinPoint.getSignature()).thenReturn(signature);

        Assertions.assertThrows(ValidationDataException.class,
            () -> serviceInterceptor.validationInterceptor(joinPoint));
    }

    private static class SignatureImpl implements Signature{

        @Override
        public String toShortString() {
            return null;
        }
    
        @Override
        public String toLongString() {
            return null;
        }
    
        @Override
        public String getName() {
            return null;
        }
    
        @Override
        public int getModifiers() {
            return 0;
        }
    
        @Override
        public Class<?> getDeclaringType() {
            return SignatureImpl.class;
        }
    
        @Override
        public String getDeclaringTypeName() {
            return "SignatureImpl";
        }
    
    }
    
    @Data
    private static class ObjectToValidate {
    
        @NotNull
        private String email;

        @NotEmpty
        private String name;
    }

}
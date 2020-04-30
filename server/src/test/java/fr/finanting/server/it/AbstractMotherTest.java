package fr.finanting.server.it;

import fr.finanting.server.TestObjectFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mother class for integrations tests
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public abstract class AbstractMotherTest {

    protected TestObjectFactory factory = new TestObjectFactory();

    /**
     * Method launch before each test
     */
    @BeforeEach
    public void beforeEach(){
        this.initDataBeforeEach();
    }

    /**
     * Method launch after each test
     */
    @AfterEach
    public void afterEach(){
        this.factory.resetListString();
    }

    /**
     * Method used to prepare the data of tests
     */
    abstract protected void initDataBeforeEach();

}

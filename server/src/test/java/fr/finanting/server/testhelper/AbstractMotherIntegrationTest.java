package fr.finanting.server.testhelper;

import javax.transaction.Transactional;

import com.github.javafaker.Faker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Mother class for integrations tests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public abstract class AbstractMotherIntegrationTest {

    protected final TestObjectFactory factory = new TestObjectFactory();
    protected final Faker faker = new Faker();

    /**
     * Method launch before each test
     */
    @BeforeEach
    public void beforeEach() throws Exception {
        this.factory.resetAllList();
        this.initDataBeforeEach();
    }

    /**
     * Method launch after each test
     */
    @AfterEach
    public void afterEach(){
        this.factory.resetAllList();
    }

    /**
     * Method used to prepare the data of tests
     */
    abstract protected void initDataBeforeEach() throws Exception;

}

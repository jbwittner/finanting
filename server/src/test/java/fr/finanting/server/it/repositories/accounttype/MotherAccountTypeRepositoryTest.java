package fr.finanting.server.it.repositories.accounttype;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.it.AbstractMotherTest;
import fr.finanting.server.model.AccountType;
import fr.finanting.server.repositorie.AccountTypeRepository;

/**
 * Class to prepare test of AccountType
 */
public class MotherAccountTypeRepositoryTest extends AbstractMotherTest {

    static final double MAX_ACCOUNT_TYPE = 50;
    static final double MIN_ACCOUNT_TYPE = 25;
    private double numberAccountType;

    final protected List<AccountType> accountTypeList = new ArrayList<>();

    @Autowired
    protected AccountTypeRepository accountTypeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initDataBeforeEach() {
        this.numberAccountType = MIN_ACCOUNT_TYPE + (Math.random() * (MAX_ACCOUNT_TYPE - MIN_ACCOUNT_TYPE));

        AccountType accountType;

        for (int i = 0; i < numberAccountType; i++) {
            accountType = this.factory.createRandomAccountType();
            this.accountTypeList.add(accountType);
            this.accountTypeRepository.save(accountType);
        }

    }

}
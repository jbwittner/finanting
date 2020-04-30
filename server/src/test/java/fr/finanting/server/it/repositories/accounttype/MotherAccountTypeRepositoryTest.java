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

    static final Integer MAX_ACCOUNT_TYPE = 50;
    static final Integer MIN_ACCOUNT_TYPE = 25;

    final protected List<AccountType> accountTypeList = new ArrayList<>();

    protected AccountType randomAccountType;

    @Autowired
    protected AccountTypeRepository accountTypeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initDataBeforeEach() {

        Integer numberAccountType = this.factory.getRandomInteger(MIN_ACCOUNT_TYPE, MAX_ACCOUNT_TYPE);

        AccountType accountType;

        for (int i = 0; i < numberAccountType; i++) {
            accountType = this.factory.createAccountType();
            this.accountTypeList.add(accountType);
            this.accountTypeRepository.save(accountType);
        }

        this.randomAccountType = accountTypeList.get(this.factory.getRandomInteger(0, numberAccountType));

        this.accountTypeRepository.flush();

    }

}
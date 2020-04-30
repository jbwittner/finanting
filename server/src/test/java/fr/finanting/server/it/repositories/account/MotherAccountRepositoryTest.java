package fr.finanting.server.it.repositories.account;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.it.AbstractMotherTest;
import fr.finanting.server.model.Account;
import fr.finanting.server.model.AccountType;
import fr.finanting.server.repositorie.AccountRepository;
import fr.finanting.server.repositorie.AccountTypeRepository;

/**
 * Class to prepare test of AccountType
 */
public class MotherAccountRepositoryTest extends AbstractMotherTest {

    static final Integer MAX_ACCOUNT = 50;
    static final Integer MIN_ACCOUNT = 25;
    static final Integer NUMBER_ACCOUNT_TYPE = 5;

    final protected List<AccountType> accountTypeList = new ArrayList<>();
    final protected List<Account> accountList = new ArrayList<>();
    Map<AccountType, List<Account>> accountMapByType = new LinkedHashMap<>();

    protected Account randomAccount;

    @Autowired
    protected AccountTypeRepository accountTypeRepository;

    @Autowired
    protected AccountRepository accountRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initDataBeforeEach() {

        Integer numberAccount = this.factory.getRandomInteger(MIN_ACCOUNT, MAX_ACCOUNT);
        Integer randomNumberId;

        AccountType accountType;

        for (int i = 0; i < NUMBER_ACCOUNT_TYPE; i++) {
            accountType = this.factory.createAccountType();
            this.accountTypeList.add(accountType);
            this.accountTypeRepository.save(accountType);
            this.accountMapByType.put(accountType, new ArrayList<Account>());

        }

        Account account;
        List<Account> accountListbyType;

        for (int i = 0; i < numberAccount; i++) {
            randomNumberId = this.factory.getRandomInteger(0, NUMBER_ACCOUNT_TYPE);
            accountType = accountTypeList.get(randomNumberId);
            account = this.factory.createAccount(accountType);
            accountListbyType = this.accountMapByType.remove(accountType);
            accountListbyType.add(account);
            this.accountMapByType.put(accountType, accountListbyType);
            this.accountList.add(account);
            this.accountRepository.save(account);
        }

        this.randomAccount = accountList.get(this.factory.getRandomInteger(0, numberAccount));

        this.accountTypeRepository.flush();
        this.accountRepository.flush();

    }

}
package fr.finanting.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.finanting.server.dto.AccountDTO;
import fr.finanting.server.model.Account;
import fr.finanting.server.parameter.CreateAccountParameter;
import fr.finanting.server.repository.AccountRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    
    private AccountRepository accountRepository;
    private GroupRepository groupRepository;
    private UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(final AccountRepository accountRepository,
        final GroupRepository groupRepository, final UserRepository userRepository){
            this.accountRepository = accountRepository;
            this.groupRepository = groupRepository;
            this.userRepository = userRepository;
        }

    public AccountDTO createAccount(final CreateAccountParameter createAccountParameter){

        Account account = new Account();
        AccountDTO accountDTO = new AccountDTO();

        

        return accountDTO;        
    }

}

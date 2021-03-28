package fr.finanting.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.finanting.server.dto.AccountDTO;
import fr.finanting.server.exception.AccountNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.NotAdminGroupException;
import fr.finanting.server.exception.NotUserAccountException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.model.Account;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.model.embeddable.Address;
import fr.finanting.server.model.embeddable.BankDetails;
import fr.finanting.server.parameter.CreateAccountParameter;
import fr.finanting.server.parameter.DeleteAccountParameter;
import fr.finanting.server.parameter.UpdateAccountParameter;
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

    public AccountDTO createAccount(final CreateAccountParameter createAccountParameter, final String userName)
            throws UserNotExistException, GroupNotExistException{

        Account account = new Account();

        if(createAccountParameter.getGroupName() == null){
            final User user = this.userRepository.findByUserName(userName)
                    .orElseThrow(() -> new UserNotExistException(userName));
            account.setUser(user);
        } else {
            final String groupName = createAccountParameter.getGroupName();
            final Group group = this.groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new GroupNotExistException(groupName));
            account.setGroup(group);
        }

        account.setAbbreviation(createAccountParameter.getAbbreviation().toUpperCase());
        account.setInitialBalance(createAccountParameter.getInitialBalance());
        account.setLabel(createAccountParameter.getLabel());

        final Address address = new Address();
        address.setAddress(createAccountParameter.getAddressParameter().getAddress());
        address.setCity(createAccountParameter.getAddressParameter().getCity());
        address.setStreet(createAccountParameter.getAddressParameter().getStreet());
        address.setZipCode(createAccountParameter.getAddressParameter().getZipCode());
        account.setAddress(address);

        final BankDetails bankDetails = new BankDetails();
        bankDetails.setAccountNumber(createAccountParameter.getBankDetailsParameter().getAccountNumber());
        bankDetails.setIban(createAccountParameter.getBankDetailsParameter().getIban());
        bankDetails.setBankName(createAccountParameter.getBankDetailsParameter().getBankName());
        account.setBankDetails(bankDetails);

        account = this.accountRepository.save(account);

        final AccountDTO accountDTO = new AccountDTO(account);
        accountDTO.setBalance(account.getInitialBalance());

        return accountDTO;
    }

    private void checkIsUserAccount(final Account account, final String userName)
            throws NotAdminGroupException, NotUserAccountException{

        final Group group = account.getGroup();

        if(group == null) {
            if(!account.getUser().getUserName().equals(userName)){
                throw new NotUserAccountException(userName, account);
            }
        } else {
            if(!group.getUserAdmin().getUserName().equals(userName)){
                throw new NotAdminGroupException(group);
            }
        }

    }

    public void deleteAccount(final DeleteAccountParameter deleteAccountParameter, final String userName)
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException{

        final  Account account = this.accountRepository.findById(deleteAccountParameter.getId())
            .orElseThrow(() -> new AccountNotExistException(deleteAccountParameter.getId()));

        this.checkIsUserAccount(account, userName);

        this.accountRepository.delete(account);

    }

    public AccountDTO updateAccount(final UpdateAccountParameter updateAccountParameter, final String userName)
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException{

        Account account = this.accountRepository.findById(updateAccountParameter.getAccountId())
            .orElseThrow(() -> new AccountNotExistException(updateAccountParameter.getAccountId()));

        this.checkIsUserAccount(account, userName);

        account.setAbbreviation(updateAccountParameter.getAbbreviation().toUpperCase());
        account.setInitialBalance(updateAccountParameter.getInitialBalance());
        account.setLabel(updateAccountParameter.getLabel());

        final Address address = new Address();
        address.setAddress(updateAccountParameter.getAddressParameter().getAddress());
        address.setCity(updateAccountParameter.getAddressParameter().getCity());
        address.setStreet(updateAccountParameter.getAddressParameter().getStreet());
        address.setZipCode(updateAccountParameter.getAddressParameter().getZipCode());
        account.setAddress(address);

        final BankDetails bankDetails = new BankDetails();
        bankDetails.setAccountNumber(updateAccountParameter.getBankDetailsParameter().getAccountNumber());
        bankDetails.setIban(updateAccountParameter.getBankDetailsParameter().getIban());
        bankDetails.setBankName(updateAccountParameter.getBankDetailsParameter().getBankName());
        account.setBankDetails(bankDetails);

        account = this.accountRepository.save(account);

        final AccountDTO accountDTO = new AccountDTO(account);
        accountDTO.setBalance(account.getInitialBalance());

        return accountDTO;

    }

}

package fr.finanting.server.service.implementation;

import fr.finanting.server.dto.AccountsDTO;
import fr.finanting.server.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.finanting.server.dto.AccountDTO;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.model.embeddable.Address;
import fr.finanting.server.model.embeddable.BankDetails;
import fr.finanting.server.parameter.CreateAccountParameter;
import fr.finanting.server.parameter.DeleteAccountParameter;
import fr.finanting.server.parameter.UpdateAccountParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.AccountService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    
    private final BankingAccountRepository accountRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(final BankingAccountRepository accountRepository,
        final GroupRepository groupRepository, final UserRepository userRepository){
            this.accountRepository = accountRepository;
            this.groupRepository = groupRepository;
            this.userRepository = userRepository;
        }

    @Override
    public AccountDTO createAccount(final CreateAccountParameter createAccountParameter, final String userName)
            throws UserNotExistException, GroupNotExistException{

        BankingAccount account = new BankingAccount();

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

    private void checkIsAdminAccount(final BankingAccount account, final String userName)
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

    private void checkIsUserAccount(final BankingAccount account, final String userName)
            throws NotUserAccountException, UserNotInGroupException {

        final Group group = account.getGroup();

        if(group == null) {
            if(!account.getUser().getUserName().equals(userName)){
                throw new NotUserAccountException(userName, account);
            }
        } else {
            boolean isInGroup = false;

            for(final User user : group.getUsers()){
                if(user.getUserName().equals(userName)){
                    isInGroup = true;
                    break;
                }
            }

            if(!isInGroup){
                final User user = this.userRepository.findByUserName(userName).orElseThrow();
                throw new UserNotInGroupException(user, group);
            }
        }

    }

    @Override
    public void deleteAccount(final DeleteAccountParameter deleteAccountParameter, final String userName)
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException{

        final  BankingAccount account = this.accountRepository.findById(deleteAccountParameter.getId())
            .orElseThrow(() -> new AccountNotExistException(deleteAccountParameter.getId()));

        this.checkIsAdminAccount(account, userName);

        this.accountRepository.delete(account);

    }

    @Override
    public AccountDTO updateAccount(final UpdateAccountParameter updateAccountParameter, final String userName)
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException{

        BankingAccount account = this.accountRepository.findById(updateAccountParameter.getAccountId())
            .orElseThrow(() -> new AccountNotExistException(updateAccountParameter.getAccountId()));

        this.checkIsAdminAccount(account, userName);

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

    @Override
    public AccountsDTO getUserAccounts(final String userName){
        final AccountsDTO accountsDTO = new AccountsDTO();
        final List<AccountDTO> userAccountDTOList = new ArrayList<>();
        final List<AccountDTO> groupAccountDTOList = new ArrayList<>();

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        final List<BankingAccount> userAccounts = this.accountRepository.findByUser(user);

        AccountDTO accountDTO;

        for(final BankingAccount account : userAccounts){
            accountDTO = new AccountDTO(account);
            accountDTO.setBalance(account.getInitialBalance());
            userAccountDTOList.add(accountDTO);
        }

        accountsDTO.setUserAccountDTO(userAccountDTOList);

        for(final Group group : user.getGroups()){
            for(final BankingAccount groupAccount : group.getAccounts()){
                accountDTO = new AccountDTO(groupAccount);
                accountDTO.setBalance(groupAccount.getInitialBalance());
                groupAccountDTOList.add(accountDTO);
            }
        }

        accountsDTO.setGroupAccountDTO(groupAccountDTOList);

        return accountsDTO;
    }

    @Override
    public AccountDTO getAccount(final Integer accountId, final String userName)
            throws AccountNotExistException, NotUserAccountException, UserNotInGroupException {

        final BankingAccount account = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotExistException(accountId));

        this.checkIsUserAccount(account, userName);

        final AccountDTO accountDTO = new AccountDTO(account);
        accountDTO.setBalance(account.getInitialBalance());

        return accountDTO;
    }

}

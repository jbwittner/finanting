package fr.finanting.server.service.implementation;

import fr.finanting.server.dto.BankingAccountsDTO;
import fr.finanting.server.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.finanting.server.dto.BankingAccountDTO;
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
import fr.finanting.server.service.BankingAccountService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankingAccountServiceImpl implements BankingAccountService {
    
    private final BankingAccountRepository bankingAccountRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public BankingAccountServiceImpl(final BankingAccountRepository bankingAccountRepository,
        final GroupRepository groupRepository, final UserRepository userRepository){
            this.bankingAccountRepository = bankingAccountRepository;
            this.groupRepository = groupRepository;
            this.userRepository = userRepository;
        }

    @Override
    public BankingAccountDTO createAccount(final CreateAccountParameter createAccountParameter, final String userName)
            throws UserNotExistException, GroupNotExistException{

        BankingAccount bankingAccount = new BankingAccount();

        if(createAccountParameter.getGroupName() == null){
            final User user = this.userRepository.findByUserName(userName)
                    .orElseThrow(() -> new UserNotExistException(userName));
            bankingAccount.setUser(user);
        } else {
            final String groupName = createAccountParameter.getGroupName();
            final Group group = this.groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new GroupNotExistException(groupName));
            bankingAccount.setGroup(group);
        }

        bankingAccount.setAbbreviation(createAccountParameter.getAbbreviation().toUpperCase());
        bankingAccount.setInitialBalance(createAccountParameter.getInitialBalance());
        bankingAccount.setLabel(createAccountParameter.getLabel());

        final Address address = new Address();
        address.setAddress(createAccountParameter.getAddressParameter().getAddress());
        address.setCity(createAccountParameter.getAddressParameter().getCity());
        address.setStreet(createAccountParameter.getAddressParameter().getStreet());
        address.setZipCode(createAccountParameter.getAddressParameter().getZipCode());
        bankingAccount.setAddress(address);

        final BankDetails bankDetails = new BankDetails();
        bankDetails.setAccountNumber(createAccountParameter.getBankDetailsParameter().getAccountNumber());
        bankDetails.setIban(createAccountParameter.getBankDetailsParameter().getIban());
        bankDetails.setBankName(createAccountParameter.getBankDetailsParameter().getBankName());
        bankingAccount.setBankDetails(bankDetails);

        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final BankingAccountDTO bankingAccountDTO = new BankingAccountDTO(bankingAccount);
        bankingAccountDTO.setBalance(bankingAccount.getInitialBalance());

        return bankingAccountDTO;
    }

    private void checkIsAdminAccount(final BankingAccount bankingAccount, final String userName)
            throws NotAdminGroupException, NotUserAccountException{

        final Group group = bankingAccount.getGroup();

        if(group == null) {
            if(!bankingAccount.getUser().getUserName().equals(userName)){
                throw new NotUserAccountException(userName, bankingAccount);
            }
        } else {
            if(!group.getUserAdmin().getUserName().equals(userName)){
                throw new NotAdminGroupException(group);
            }
        }

    }

    private void checkIsUserAccount(final BankingAccount bankingAccount, final String userName)
            throws NotUserAccountException, UserNotInGroupException {

        final Group group = bankingAccount.getGroup();

        if(group == null) {
            if(!bankingAccount.getUser().getUserName().equals(userName)){
                throw new NotUserAccountException(userName, bankingAccount);
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

        final  BankingAccount bankingAccount = this.bankingAccountRepository.findById(deleteAccountParameter.getId())
            .orElseThrow(() -> new AccountNotExistException(deleteAccountParameter.getId()));

        this.checkIsAdminAccount(bankingAccount, userName);

        this.bankingAccountRepository.delete(bankingAccount);

    }

    @Override
    public BankingAccountDTO updateAccount(final UpdateAccountParameter updateAccountParameter, final String userName)
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException{

        BankingAccount bankingAccount = this.bankingAccountRepository.findById(updateAccountParameter.getAccountId())
            .orElseThrow(() -> new AccountNotExistException(updateAccountParameter.getAccountId()));

        this.checkIsAdminAccount(bankingAccount, userName);

        bankingAccount.setAbbreviation(updateAccountParameter.getAbbreviation().toUpperCase());
        bankingAccount.setInitialBalance(updateAccountParameter.getInitialBalance());
        bankingAccount.setLabel(updateAccountParameter.getLabel());

        final Address address = new Address();
        address.setAddress(updateAccountParameter.getAddressParameter().getAddress());
        address.setCity(updateAccountParameter.getAddressParameter().getCity());
        address.setStreet(updateAccountParameter.getAddressParameter().getStreet());
        address.setZipCode(updateAccountParameter.getAddressParameter().getZipCode());
        bankingAccount.setAddress(address);

        final BankDetails bankDetails = new BankDetails();
        bankDetails.setAccountNumber(updateAccountParameter.getBankDetailsParameter().getAccountNumber());
        bankDetails.setIban(updateAccountParameter.getBankDetailsParameter().getIban());
        bankDetails.setBankName(updateAccountParameter.getBankDetailsParameter().getBankName());
        bankingAccount.setBankDetails(bankDetails);

        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final BankingAccountDTO bankingAccountDTO = new BankingAccountDTO(bankingAccount);
        bankingAccountDTO.setBalance(bankingAccount.getInitialBalance());

        return bankingAccountDTO;

    }

    @Override
    public BankingAccountsDTO getUserAccounts(final String userName){
        final BankingAccountsDTO bankingAccountsDTO = new BankingAccountsDTO();
        final List<BankingAccountDTO> userAccountDTOList = new ArrayList<>();
        final List<BankingAccountDTO> groupAccountDTOList = new ArrayList<>();

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        final List<BankingAccount> userAccounts = this.bankingAccountRepository.findByUser(user);

        BankingAccountDTO bankingAccountDTO;

        for(final BankingAccount bankingAccount : userAccounts){
            bankingAccountDTO = new BankingAccountDTO(bankingAccount);
            bankingAccountDTO.setBalance(bankingAccount.getInitialBalance());
            userAccountDTOList.add(bankingAccountDTO);
        }

        bankingAccountsDTO.setUserAccountDTO(userAccountDTOList);

        for(final Group group : user.getGroups()){
            for(final BankingAccount groupAccount : group.getAccounts()){
                bankingAccountDTO = new BankingAccountDTO(groupAccount);
                bankingAccountDTO.setBalance(groupAccount.getInitialBalance());
                groupAccountDTOList.add(bankingAccountDTO);
            }
        }

        bankingAccountsDTO.setGroupAccountDTO(groupAccountDTOList);

        return bankingAccountsDTO;
    }

    @Override
    public BankingAccountDTO getAccount(final Integer accountId, final String userName)
            throws AccountNotExistException, NotUserAccountException, UserNotInGroupException {

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotExistException(accountId));

        this.checkIsUserAccount(bankingAccount, userName);

        final BankingAccountDTO bankingAccountDTO = new BankingAccountDTO(bankingAccount);
        bankingAccountDTO.setBalance(bankingAccount.getInitialBalance());

        return bankingAccountDTO;
    }

}

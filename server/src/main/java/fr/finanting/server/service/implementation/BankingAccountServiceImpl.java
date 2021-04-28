package fr.finanting.server.service.implementation;

import fr.finanting.server.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.finanting.server.dto.BankingAccountDTO;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Currency;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.model.embeddable.Address;
import fr.finanting.server.model.embeddable.BankDetails;
import fr.finanting.server.parameter.CreateBankingAccountParameter;
import fr.finanting.server.parameter.DeleteBankingAccountParameter;
import fr.finanting.server.parameter.UpdateBankingAccountParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.CurrencyRepository;
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
    private final CurrencyRepository currencyRepository;

    @Autowired
    public BankingAccountServiceImpl(final BankingAccountRepository bankingAccountRepository,
        final GroupRepository groupRepository, final UserRepository userRepository, final CurrencyRepository currencyRepository){
            this.bankingAccountRepository = bankingAccountRepository;
            this.groupRepository = groupRepository;
            this.userRepository = userRepository;
            this.currencyRepository = currencyRepository;
        }

    @Override
    public BankingAccountDTO createAccount(final CreateBankingAccountParameter createBankingAccountParameter, final String userName)
            throws GroupNotExistException, CurrencyNotExistException{

        BankingAccount bankingAccount = new BankingAccount();

        if(createBankingAccountParameter.getGroupName() == null){
            final User user = this.userRepository.findByUserName(userName).orElseThrow();
            bankingAccount.setUser(user);
        } else {
            final String groupName = createBankingAccountParameter.getGroupName();
            final Group group = this.groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new GroupNotExistException(groupName));
            bankingAccount.setGroup(group);
        }

        final Currency currency = this.currencyRepository.findByIsoCode(createBankingAccountParameter.getDefaultCurrencyISOCode())
            .orElseThrow(() -> new CurrencyNotExistException(createBankingAccountParameter.getDefaultCurrencyISOCode()));

        bankingAccount.setDefaultCurrency(currency);

        bankingAccount.setAbbreviation(createBankingAccountParameter.getAbbreviation().toUpperCase());
        bankingAccount.setInitialBalance(createBankingAccountParameter.getInitialBalance());
        bankingAccount.setLabel(createBankingAccountParameter.getLabel());

        if(createBankingAccountParameter.getAddressParameter() != null){
            final Address address = new Address();
            address.setAddress(createBankingAccountParameter.getAddressParameter().getAddress());
            address.setCity(createBankingAccountParameter.getAddressParameter().getCity());
            address.setStreet(createBankingAccountParameter.getAddressParameter().getStreet());
            address.setZipCode(createBankingAccountParameter.getAddressParameter().getZipCode());
            bankingAccount.setAddress(address);
        }
        
        if(createBankingAccountParameter.getBankDetailsParameter() != null){
            final BankDetails bankDetails = new BankDetails();
            bankDetails.setAccountNumber(createBankingAccountParameter.getBankDetailsParameter().getAccountNumber());
            bankDetails.setIban(createBankingAccountParameter.getBankDetailsParameter().getIban());
            bankDetails.setBankName(createBankingAccountParameter.getBankDetailsParameter().getBankName());
            bankingAccount.setBankDetails(bankDetails);
        }

        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final BankingAccountDTO bankingAccountDTO = new BankingAccountDTO(bankingAccount);
        bankingAccountDTO.setBalance(bankingAccount.getInitialBalance());

        return bankingAccountDTO;
    }

    private void checkIsAdminAccount(final BankingAccount bankingAccount, final String userName)
            throws NotAdminGroupException, NotUserBankingAccountException{

        final Group group = bankingAccount.getGroup();

        if(group == null) {
            if(!bankingAccount.getUser().getUserName().equals(userName)){
                throw new NotUserBankingAccountException(userName, bankingAccount);
            }
        } else {
            if(!group.getUserAdmin().getUserName().equals(userName)){
                throw new NotAdminGroupException(group);
            }
        }

    }

    private void checkIsUserAccount(final BankingAccount bankingAccount, final User user)
            throws NotUserBankingAccountException, UserNotInGroupException {

        final Group group = bankingAccount.getGroup();

        if(group == null) {
            if(!bankingAccount.getUser().getUserName().equals(user.getUserName())){
                throw new NotUserBankingAccountException(user.getUserName(), bankingAccount);
            }
        } else {
            group.checkAreInGroup(user);
        }

    }

    @Override
    public void deleteAccount(final DeleteBankingAccountParameter deleteBankingAccountParameter, final String userName)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException{

        final  BankingAccount bankingAccount = this.bankingAccountRepository.findById(deleteBankingAccountParameter.getId())
            .orElseThrow(() -> new BankingAccountNotExistException(deleteBankingAccountParameter.getId()));

        this.checkIsAdminAccount(bankingAccount, userName);

        this.bankingAccountRepository.delete(bankingAccount);

    }

    @Override
    public BankingAccountDTO updateAccount(final UpdateBankingAccountParameter updateBankingAccountParameter, final String userName)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException, CurrencyNotExistException{

        BankingAccount bankingAccount = this.bankingAccountRepository.findById(updateBankingAccountParameter.getAccountId())
            .orElseThrow(() -> new BankingAccountNotExistException(updateBankingAccountParameter.getAccountId()));

        this.checkIsAdminAccount(bankingAccount, userName);

        final Currency currency = this.currencyRepository.findByIsoCode(updateBankingAccountParameter.getDefaultCurrencyISOCode())
            .orElseThrow(() -> new CurrencyNotExistException(updateBankingAccountParameter.getDefaultCurrencyISOCode()));

        bankingAccount.setDefaultCurrency(currency);

        bankingAccount.setAbbreviation(updateBankingAccountParameter.getAbbreviation().toUpperCase());
        bankingAccount.setInitialBalance(updateBankingAccountParameter.getInitialBalance());
        bankingAccount.setLabel(updateBankingAccountParameter.getLabel());

        final Address address = new Address();
        address.setAddress(updateBankingAccountParameter.getAddressParameter().getAddress());
        address.setCity(updateBankingAccountParameter.getAddressParameter().getCity());
        address.setStreet(updateBankingAccountParameter.getAddressParameter().getStreet());
        address.setZipCode(updateBankingAccountParameter.getAddressParameter().getZipCode());
        bankingAccount.setAddress(address);

        final BankDetails bankDetails = new BankDetails();
        bankDetails.setAccountNumber(updateBankingAccountParameter.getBankDetailsParameter().getAccountNumber());
        bankDetails.setIban(updateBankingAccountParameter.getBankDetailsParameter().getIban());
        bankDetails.setBankName(updateBankingAccountParameter.getBankDetailsParameter().getBankName());
        bankingAccount.setBankDetails(bankDetails);

        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final BankingAccountDTO bankingAccountDTO = new BankingAccountDTO(bankingAccount);
        bankingAccountDTO.setBalance(bankingAccount.getInitialBalance());

        return bankingAccountDTO;

    }

    @Override
    public List<BankingAccountDTO> getUserBankingAccounts(final String userName){
        final List<BankingAccountDTO> userAccountDTOList = new ArrayList<>();

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        final List<BankingAccount> userAccounts = this.bankingAccountRepository.findByUser(user);

        BankingAccountDTO bankingAccountDTO;

        for(final BankingAccount bankingAccount : userAccounts){
            bankingAccountDTO = new BankingAccountDTO(bankingAccount);
            bankingAccountDTO.setBalance(bankingAccount.getInitialBalance());
            userAccountDTOList.add(bankingAccountDTO);
        }

        return userAccountDTOList;
    }

    @Override
    public List<BankingAccountDTO> getGroupBankingAccounts(final String groupName, final String userName) throws UserNotInGroupException, GroupNotExistException {
        final List<BankingAccountDTO> groupAccountDTOList = new ArrayList<>();

        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        
        final Group group = this.groupRepository.findByGroupName(groupName)
            .orElseThrow(() -> new GroupNotExistException(groupName));

        group.checkAreInGroup(user);

        final List<BankingAccount> accounts = this.bankingAccountRepository.findByGroup(group);

        BankingAccountDTO bankingAccountDTO;

        for(final BankingAccount bankingAccount : accounts){
            bankingAccountDTO = new BankingAccountDTO(bankingAccount);
            bankingAccountDTO.setBalance(bankingAccount.getInitialBalance());
            groupAccountDTOList.add(bankingAccountDTO);
        }

        return groupAccountDTOList;
    }

    @Override
    public BankingAccountDTO getBankingAccount(final Integer accountId, final String userName)
            throws BankingAccountNotExistException, NotUserBankingAccountException, UserNotInGroupException {

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankingAccountNotExistException(accountId));

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        this.checkIsUserAccount(bankingAccount, user);

        final BankingAccountDTO bankingAccountDTO = new BankingAccountDTO(bankingAccount);
        bankingAccountDTO.setBalance(bankingAccount.getInitialBalance());

        return bankingAccountDTO;
    }



}

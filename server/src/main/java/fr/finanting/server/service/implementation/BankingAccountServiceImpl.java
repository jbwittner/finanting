package fr.finanting.server.service.implementation;

import fr.finanting.server.generated.model.BankingAccountDTO;
import fr.finanting.server.generated.model.BankingAccountParameter;
import fr.finanting.server.generated.model.UpdateBankingAccountParameter;
import fr.finanting.server.dto.BankingAccountDTOBuilder;
import fr.finanting.server.exception.*;
import fr.finanting.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Currency;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.model.embeddable.Address;
import fr.finanting.server.model.embeddable.BankDetails;
import fr.finanting.server.service.BankingAccountService;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@Transactional
public class BankingAccountServiceImpl implements BankingAccountService {
    
    private final BankingAccountRepository bankingAccountRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final BankingTransactionRepository bankingTransactionRepository;

    private static final BankingAccountDTOBuilder BANKING_ACCOUNT_DTO_BUILDER = new BankingAccountDTOBuilder();

    @Autowired
    public BankingAccountServiceImpl(final BankingAccountRepository bankingAccountRepository,
                                     final GroupRepository groupRepository,
                                     final UserRepository userRepository,
                                     final CurrencyRepository currencyRepository,
                                     final BankingTransactionRepository bankingTransactionRepository){
            this.bankingAccountRepository = bankingAccountRepository;
            this.groupRepository = groupRepository;
            this.userRepository = userRepository;
            this.currencyRepository = currencyRepository;
            this.bankingTransactionRepository = bankingTransactionRepository;
        }

    @Override
    public BankingAccountDTO createAccount(final BankingAccountParameter bankingAccountParameter,
                                           final String userName) {

        BankingAccount bankingAccount = new BankingAccount();

        if(bankingAccountParameter.getGroupName() == null){
            final User user = this.userRepository.findByUserName(userName).orElseThrow();
            bankingAccount.setUser(user);
        } else {
            final String groupName = bankingAccountParameter.getGroupName();
            final Group group = this.groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new GroupNotExistException(groupName));
            bankingAccount.setGroup(group);
        }

        final Currency currency = this.currencyRepository.findByIsoCode(bankingAccountParameter.getDefaultCurrencyISOCode())
            .orElseThrow(() -> new CurrencyNotExistException(bankingAccountParameter.getDefaultCurrencyISOCode()));

        bankingAccount.setDefaultCurrency(currency);

        bankingAccount.setAbbreviation(bankingAccountParameter.getAbbreviation().toUpperCase());
        bankingAccount.setInitialBalance(bankingAccountParameter.getInitialBalance());
        bankingAccount.setLabel(bankingAccountParameter.getLabel());

        if(bankingAccountParameter.getAddressParameter() != null){
            final Address address = new Address();
            address.setAddress(bankingAccountParameter.getAddressParameter().getAddress());
            address.setCity(bankingAccountParameter.getAddressParameter().getCity());
            address.setStreet(bankingAccountParameter.getAddressParameter().getStreet());
            address.setZipCode(bankingAccountParameter.getAddressParameter().getZipCode());
            bankingAccount.setAddress(address);
        }
        
        if(bankingAccountParameter.getBankDetailsParameter() != null){
            final BankDetails bankDetails = new BankDetails();
            bankDetails.setAccountNumber(bankingAccountParameter.getBankDetailsParameter().getAccountNumber());
            bankDetails.setIban(bankingAccountParameter.getBankDetailsParameter().getIban());
            bankDetails.setBankName(bankingAccountParameter.getBankDetailsParameter().getBankName());
            bankingAccount.setBankDetails(bankDetails);
        }

        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final BankingAccountDTO bankingAccountDTO = BANKING_ACCOUNT_DTO_BUILDER.transform(bankingAccount);
        bankingAccountDTO.setBalance(bankingAccount.getInitialBalance());

        return bankingAccountDTO;
    }

    private void checkIsAdminAccount(final BankingAccount bankingAccount, final String userName) {

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

    private void checkIsUserAccount(final BankingAccount bankingAccount, final User user) {

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
    public void deleteAccount(final Integer bankingAccountId, final String userName) {

        final  BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountId)
            .orElseThrow(() -> new BankingAccountNotExistException(bankingAccountId));

        this.checkIsAdminAccount(bankingAccount, userName);

        this.bankingAccountRepository.delete(bankingAccount);

    }

    @Override
    public BankingAccountDTO updateAccount(final Integer bankingAccountId,
                                           final UpdateBankingAccountParameter updateBankingAccountParameter,
                                           final String userName) {

        BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountId)
            .orElseThrow(() -> new BankingAccountNotExistException(bankingAccountId));

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

        final BankingAccountDTO bankingAccountDTO = BANKING_ACCOUNT_DTO_BUILDER.transform(bankingAccount);
        bankingAccountDTO.setBalance(bankingAccount.getInitialBalance());

        return bankingAccountDTO;

    }

    private List<BankingAccountDTO> getBankingAccountDTOList(final List<BankingAccount> accounts){

        List<BankingAccountDTO> bankingAccountDTOList = BANKING_ACCOUNT_DTO_BUILDER.transformAll(accounts);

        bankingAccountDTOList = bankingAccountDTOList.stream().peek(bankingAccountDTO -> {
            final BankingAccount bankingAccount = accounts.stream().filter(o -> o.getId().equals(bankingAccountDTO.getId())).findAny().orElseThrow();
            final Double sum = bankingAccount.getInitialBalance();
            Double sumAmount = this.bankingTransactionRepository.sumAmountByAccountId(bankingAccount.getId());
            sumAmount = sumAmount == null ? 0.0 : sumAmount;
            bankingAccountDTO.setBalance(sum + sumAmount);
        }).collect(Collectors.toList());

        return bankingAccountDTOList;
    }

    @Override
    public List<BankingAccountDTO> getUserBankingAccounts(final String userName){
        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        final List<BankingAccount> userAccounts = this.bankingAccountRepository.findByUser(user);
        return this.getBankingAccountDTOList(userAccounts);
    }

    @Override
    public List<BankingAccountDTO> getGroupBankingAccounts(final Integer groupId, final String userName) {
        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        
        final Group group = this.groupRepository.findById(groupId)
            .orElseThrow(() -> new GroupNotExistException(groupId));

        group.checkAreInGroup(user);

        final List<BankingAccount> accounts = this.bankingAccountRepository.findByGroup(group);
        return this.getBankingAccountDTOList(accounts);
    }

    @Override
    public BankingAccountDTO getBankingAccount(final Integer accountId, final String userName) {

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankingAccountNotExistException(accountId));

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        this.checkIsUserAccount(bankingAccount, user);

        final BankingAccountDTO bankingAccountDTO = BANKING_ACCOUNT_DTO_BUILDER.transform(bankingAccount);
        Double sum = this.bankingTransactionRepository.sumAmountByAccountId(bankingAccount.getId());
        sum = sum == null ? 0.0 : sum;
        sum = sum + bankingAccount.getInitialBalance();
        bankingAccountDTO.setBalance(sum);

        return bankingAccountDTO;
    }

}

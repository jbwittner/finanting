package fr.finanting.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class BankingAccountsDTO {

    private List<BankingAccountDTO> userAccountDTO;
    private List<BankingAccountDTO> groupAccountDTO;

}

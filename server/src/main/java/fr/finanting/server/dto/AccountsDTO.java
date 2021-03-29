package fr.finanting.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountsDTO {

    private List<AccountDTO> userAccountDTO;
    private List<AccountDTO> groupAccountDTO;

}

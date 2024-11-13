package org.example.model.mappers;

import org.example.model.accounts.SavingAccount;
import org.example.model.dto.SavingAccountDTO;
import org.example.model.dto.StandardAccountDTO;

public class SavingAccountDTOMapper {
    public static SavingAccountDTO toDTO(SavingAccount savingAccount) {
        if (savingAccount == null) {
            return null;
        }
        return new SavingAccountDTO(
                savingAccount.getId(),
                savingAccount.getBalance(),
                ClientDTOMapper.toDTO(savingAccount.getClient()),
                savingAccount.getActive(),
                savingAccount.getCreationDate(),
                savingAccount.getCloseDate(),
                savingAccount.getInterestRate()
                );
    }

    public static SavingAccount fromDTO(SavingAccountDTO savingAccountDTO) {
        if (savingAccountDTO == null) {
            return null;
        }
        return new SavingAccount(
                savingAccountDTO.getId(),
                savingAccountDTO.getBalance(),
                ClientDTOMapper.fromDTO(savingAccountDTO.getClient()),
                savingAccountDTO.getActive(),
                savingAccountDTO.getCreationDate(),
                savingAccountDTO.getCloseDate(),
                savingAccountDTO.getInterestRate()
        );
    }
}

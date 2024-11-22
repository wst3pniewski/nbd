package org.example.model.mappers;

import org.example.model.accounts.BankAccount;
import org.example.model.accounts.JuniorAccount;
import org.example.model.accounts.SavingAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.dto.BankAccountDTO;
import org.example.model.dto.JuniorAccountDTO;
import org.example.model.dto.SavingAccountDTO;
import org.example.model.dto.StandardAccountDTO;

public class BankAccountDTOMapper {
    public static BankAccountDTO toDTO(BankAccount bankAccount) {
        if (bankAccount instanceof SavingAccount) {
            return SavingAccountDTOMapper.toDTO((SavingAccount) bankAccount);
        } else if (bankAccount instanceof JuniorAccount) {
            return JuniorAccountDTOMapper.toDTO((JuniorAccount) bankAccount);
        } else if (bankAccount instanceof StandardAccount) {
            return StandardAccountDTOMapper.toDTO((StandardAccount) bankAccount);
        }
        else {
            throw new IllegalArgumentException("Unsupported BankAccount type: " + bankAccount.getClass().getName());
        }
    }

    public static BankAccount fromDTO(BankAccountDTO bankAccountDTO) {
        if (bankAccountDTO instanceof SavingAccountDTO) {
            return SavingAccountDTOMapper.fromDTO((SavingAccountDTO) bankAccountDTO);
        } else if (bankAccountDTO instanceof JuniorAccountDTO) {
            return JuniorAccountDTOMapper.fromDTO((JuniorAccountDTO) bankAccountDTO);
        } else if (bankAccountDTO instanceof StandardAccountDTO) {
            return StandardAccountDTOMapper.fromDTO((StandardAccountDTO) bankAccountDTO);
        }
        else {
            throw new IllegalArgumentException("Unsupported BankAccount type: " + bankAccountDTO.getClass().getName());
        }
    }
}

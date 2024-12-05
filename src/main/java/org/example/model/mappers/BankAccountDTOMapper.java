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
        return switch (bankAccount.getClass().getSimpleName()) {
            case "SavingAccount" -> SavingAccountDTOMapper.toDTO((SavingAccount) bankAccount);
            case "JuniorAccount" -> JuniorAccountDTOMapper.toDTO((JuniorAccount) bankAccount);
            case "StandardAccount" -> StandardAccountDTOMapper.toDTO((StandardAccount) bankAccount);
            default ->
                    throw new IllegalArgumentException("Unsupported BankAccount type: " + bankAccount.getClass().getName());
        };
    }

    public static BankAccount fromDTO(BankAccountDTO bankAccountDTO) {
        return switch (bankAccountDTO.getClass().getSimpleName()) {
            case "SavingAccountDTO" -> SavingAccountDTOMapper.fromRedis((SavingAccountDTO) bankAccountDTO);
            case "JuniorAccountDTO" -> JuniorAccountDTOMapper.fromDTO((JuniorAccountDTO) bankAccountDTO);
            case "StandardAccountDTO" -> StandardAccountDTOMapper.fromDTO((StandardAccountDTO) bankAccountDTO);
            default ->
                    throw new IllegalArgumentException("Unsupported BankAccount type: " + bankAccountDTO.getClass().getName());
        };
    }
}

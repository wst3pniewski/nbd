package org.example.model.mappers;

import org.example.model.accounts.StandardAccount;
import org.example.model.dto.StandardAccountDTO;

public class StandardAccountDTOMapper {
    public static StandardAccountDTO toDTO(StandardAccount standardAccount) {
        if (standardAccount == null) {
            return null;
        }
        return new StandardAccountDTO(
                standardAccount.getId(),
                standardAccount.getBalance(),
                ClientDTOMapper.toDTO(standardAccount.getClient()),
                standardAccount.getActive(),
                standardAccount.getCreationDate(),
                standardAccount.getCloseDate(),
                standardAccount.getDebitLimit(),
                standardAccount.getDebit()
        );
    }

    public static StandardAccount fromDTO(StandardAccountDTO standardAccountDTO) {
        if (standardAccountDTO == null) {
            return null;
        }
        return new StandardAccount(
                standardAccountDTO.getId(),
                standardAccountDTO.getBalance(),
                ClientDTOMapper.fromDTO(standardAccountDTO.getClient()),
                standardAccountDTO.getActive(),
                standardAccountDTO.getCreationDate(),
                standardAccountDTO.getCloseDate(),
                standardAccountDTO.getDebitLimit(),
                standardAccountDTO.getDebit()
        );
    }
}
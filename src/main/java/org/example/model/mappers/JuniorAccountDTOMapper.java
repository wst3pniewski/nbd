package org.example.model.mappers;

import org.example.model.accounts.JuniorAccount;
import org.example.model.dto.JuniorAccountDTO;

public class JuniorAccountDTOMapper {
    public static JuniorAccountDTO toDTO(JuniorAccount juniorAccount) {
        if (juniorAccount == null) {
            return null;
        }
        return new JuniorAccountDTO(
                juniorAccount.getId(),
                juniorAccount.getBalance(),
                ClientDTOMapper.toDTO(juniorAccount.getClient()),
                juniorAccount.getActive(),
                juniorAccount.getCreationDate(),
                juniorAccount.getCloseDate(),
                ClientDTOMapper.toDTO(juniorAccount.getParent())
        );
    }

    public static JuniorAccount fromDTO(JuniorAccountDTO juniorAccountDTO) {
        if (juniorAccountDTO == null) {
            return null;
        }
        return new JuniorAccount(
                juniorAccountDTO.getId(),
                juniorAccountDTO.getBalance(),
                ClientDTOMapper.fromDTO(juniorAccountDTO.getClient()),
                juniorAccountDTO.getActive(),
                juniorAccountDTO.getCreationDate(),
                juniorAccountDTO.getCloseDate(),
                ClientDTOMapper.fromDTO(juniorAccountDTO.getParent())
        );
    }
}

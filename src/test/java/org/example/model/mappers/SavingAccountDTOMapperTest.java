package org.example.model.mappers;

import org.example.model.accounts.SavingAccount;
import org.example.model.clients.Client;
import org.example.model.dto.SavingAccountDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SavingAccountDTOMapperTest {

    public static Client client = new Client("John", "Doe", LocalDate.of(2000, 1, 1),
            Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");


    @Test
    void toDTO_withValidSavingAccount_returnsSavingAccountDTO() {
        SavingAccount savingAccount = new SavingAccount(UUID.randomUUID(), BigDecimal.ZERO, client, true,
                LocalDate.now(), null, BigDecimal.ZERO);
        SavingAccountDTO dto = SavingAccountDTOMapper.toDTO(savingAccount);
        assertNotNull(dto);
        assertEquals(savingAccount.getClient().getId(), dto.getClient().getId());
        assertEquals(savingAccount.getId(), dto.getId());
        assertEquals(savingAccount.getBalance(), dto.getBalance());
        assertEquals(savingAccount.getActive(), dto.getActive());
        assertEquals(savingAccount.getCreationDate(), dto.getCreationDate());
        assertEquals(savingAccount.getInterestRate(), dto.getInterestRate());
    }

    @Test
    void toDTO_withNullSavingAccount_returnsNull() {
        SavingAccountDTO dto = SavingAccountDTOMapper.toDTO(null);
        assertNull(dto);
    }

    @Test
    void fromDTO_withValidSavingAccountDTO_returnsSavingAccount() {
        SavingAccountDTO dto = new SavingAccountDTO(UUID.randomUUID(), BigDecimal.ZERO, ClientDTOMapper.toDTO(client),
                true, LocalDate.now(), null, BigDecimal.ZERO);
        SavingAccount savingAccount = SavingAccountDTOMapper.fromDTO(dto);
        assertNotNull(savingAccount);
        assertEquals(dto.getClient().getId(), savingAccount.getClient().getId());
        assertEquals(dto.getId(), savingAccount.getId());
        assertEquals(dto.getBalance(), savingAccount.getBalance());
        assertEquals(dto.getActive(), savingAccount.getActive());
        assertEquals(dto.getCreationDate(), savingAccount.getCreationDate());
        assertEquals(dto.getInterestRate(), savingAccount.getInterestRate());
    }

    @Test
    void fromDTO_withNullSavingAccountDTO_returnsNull() {
        SavingAccount savingAccount = SavingAccountDTOMapper.fromDTO(null);
        assertNull(savingAccount);
    }
}

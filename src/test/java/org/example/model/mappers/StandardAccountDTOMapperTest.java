package org.example.model.mappers;

import org.example.model.accounts.StandardAccount;
import org.example.model.clients.Client;
import org.example.model.dto.StandardAccountDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class StandardAccountDTOMapperTest {

    public static Client client = new Client("John", "Doe", LocalDate.of(2000, 1, 1),
            Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");

    @Test
    void toDTO_withValidStandardAccount_returnsStandardAccountDTO() {
        StandardAccount standardAccount = new StandardAccount(UUID.randomUUID(), BigDecimal.ZERO, null,
                true, LocalDate.now(), null, BigDecimal.ZERO, BigDecimal.ZERO);
        StandardAccountDTO dto = StandardAccountDTOMapper.toDTO(standardAccount);
        assertNotNull(dto);
        assertEquals(standardAccount.getId(), dto.getId());
        assertEquals(standardAccount.getBalance(), dto.getBalance());
        assertEquals(standardAccount.getActive(), dto.getActive());
        assertEquals(standardAccount.getCreationDate(), dto.getCreationDate());
        assertEquals(standardAccount.getCloseDate(), dto.getCloseDate());
        assertEquals(standardAccount.getDebitLimit(), dto.getDebitLimit());
        assertEquals(standardAccount.getDebit(), dto.getDebit());
    }

    @Test
    void toDTO_withNullStandardAccount_returnsNull() {
        StandardAccountDTO dto = StandardAccountDTOMapper.toDTO(null);
        assertNull(dto);
    }

    @Test
    void fromDTO_withValidStandardAccountDTO_returnsStandardAccount() {
        StandardAccountDTO dto = new StandardAccountDTO(UUID.randomUUID(), BigDecimal.ZERO, ClientDTOMapper.toDTO(client),
                true, LocalDate.now(), null, BigDecimal.ZERO, BigDecimal.ZERO);
        StandardAccount standardAccount = StandardAccountDTOMapper.fromDTO(dto);
        assertNotNull(standardAccount);
        assertEquals(dto.getId(), standardAccount.getId());
        assertEquals(dto.getBalance(), standardAccount.getBalance());
        assertEquals(dto.getActive(), standardAccount.getActive());
        assertEquals(dto.getCreationDate(), standardAccount.getCreationDate());
        assertEquals(dto.getCloseDate(), standardAccount.getCloseDate());
        assertEquals(dto.getDebitLimit(), standardAccount.getDebitLimit());
        assertEquals(dto.getDebit(), standardAccount.getDebit());
    }

    @Test
    void fromDTO_withNullStandardAccountDTO_returnsNull() {
        StandardAccount standardAccount = StandardAccountDTOMapper.fromDTO(null);
        assertNull(standardAccount);
    }
}

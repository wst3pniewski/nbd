package org.example.model.mappers;

import org.example.model.accounts.JuniorAccount;
import org.example.model.clients.Client;
import org.example.model.dto.JuniorAccountDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class JuniorAccountDTOMapperTest {

    public static Client parent = new Client("Parent", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");
    public static Client child = new Client("Junior", "Doe", LocalDate.of(2008, 1, 1), Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");

    @Test
    void toDTO_withValidJuniorAccount_returnsJuniorAccountDTO() {
        JuniorAccount juniorAccount = new JuniorAccount(UUID.randomUUID(), BigDecimal.ZERO, child, true, LocalDate.now(), null, parent);
        JuniorAccountDTO dto = JuniorAccountDTOMapper.toDTO(juniorAccount);
        assertNotNull(dto);
        assertEquals(juniorAccount.getClient().getId(), dto.getClient().getId());
        assertEquals(juniorAccount.getParent().getId(), dto.getParent().getId());
        assertEquals(juniorAccount.getId(), dto.getId());
        assertEquals(juniorAccount.getBalance(), dto.getBalance());
        assertEquals(juniorAccount.getActive(), dto.getActive());
        assertEquals(juniorAccount.getCreationDate(), dto.getCreationDate());
    }

    @Test
    void toDTO_withNullJuniorAccount_returnsNull() {
        JuniorAccountDTO dto = JuniorAccountDTOMapper.toDTO(null);
        assertNull(dto);
    }

    @Test
    void fromDTO_withValidJuniorAccountDTO_returnsJuniorAccount() {
        JuniorAccountDTO dto = new JuniorAccountDTO(UUID.randomUUID(), BigDecimal.ZERO, ClientDTOMapper.toDTO(child),
                true, LocalDate.now(), null, ClientDTOMapper.toDTO(parent));
        JuniorAccount juniorAccount = JuniorAccountDTOMapper.fromDTO(dto);
        assertNotNull(juniorAccount);
        assertEquals(dto.getClient().getId(), juniorAccount.getClient().getId());
        assertEquals(dto.getParent().getId(), juniorAccount.getParent().getId());
        assertEquals(dto.getId(), juniorAccount.getId());
        assertEquals(dto.getBalance(), juniorAccount.getBalance());
        assertEquals(dto.getActive(), juniorAccount.getActive());
        assertEquals(dto.getCreationDate(), juniorAccount.getCreationDate());
    }

    @Test
    void fromDTO_withNullJuniorAccountDTO_returnsNull() {
        JuniorAccount juniorAccount = JuniorAccountDTOMapper.fromDTO(null);
        assertNull(juniorAccount);
    }
}

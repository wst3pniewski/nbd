package org.example.model.mappers;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.JuniorAccount;
import org.example.model.accounts.SavingAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.clients.Client;
import org.example.model.dto.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankAccountDTOMapperTest {

    public static Client client = new Client("John", "Doe", LocalDate.of(2000, 1, 1), Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");
    public static Client parent = new Client("Parent", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");
    public static Client child = new Client("Junior", "Doe", LocalDate.of(2008, 1, 1), Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");

    @Test
    void toDTO_withSavingAccount_returnsSavingAccountDTO() {
        SavingAccount savingAccount = new SavingAccount(client, BigDecimal.ZERO);
        BankAccountDTO dto = BankAccountDTOMapper.toDTO(savingAccount);
        assertTrue(dto instanceof SavingAccountDTO);
    }

    @Test
    void toDTO_withJuniorAccount_returnsJuniorAccountDTO() {
        JuniorAccount juniorAccount = new JuniorAccount(child, parent);
        BankAccountDTO dto = BankAccountDTOMapper.toDTO(juniorAccount);
        assertTrue(dto instanceof JuniorAccountDTO);
    }

    @Test
    void toDTO_withStandardAccount_returnsStandardAccountDTO() {
        StandardAccount standardAccount = new StandardAccount(client, BigDecimal.ZERO);
        BankAccountDTO dto = BankAccountDTOMapper.toDTO(standardAccount);
        assertTrue(dto instanceof StandardAccountDTO);
    }

    @Test
    void fromDTO_withSavingAccountDTO_returnsSavingAccount() {
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO(UUID.randomUUID(), BigDecimal.ZERO, ClientDTOMapper.toDTO(client), true, LocalDate.now(), null, BigDecimal.ZERO);
        BankAccount account = BankAccountDTOMapper.fromDTO(savingAccountDTO);
        assertTrue(account instanceof SavingAccount);
    }


    @Test
    void fromDTO_withJuniorAccountDTO_returnsJuniorAccount() {
        JuniorAccountDTO juniorAccountDTO = new JuniorAccountDTO(UUID.randomUUID(), BigDecimal.ZERO, ClientDTOMapper.toDTO(child), true, LocalDate.now(), null, ClientDTOMapper.toDTO(parent));
        BankAccount account = BankAccountDTOMapper.fromDTO(juniorAccountDTO);
        assertTrue(account instanceof JuniorAccount);
    }


    @Test
    void fromDTO_withStandardAccountDTO_returnsStandardAccount() {
        StandardAccountDTO standardAccountDTO = new StandardAccountDTO(UUID.randomUUID(), BigDecimal.ZERO, ClientDTOMapper.toDTO(client), true, LocalDate.now(), null, BigDecimal.ZERO, BigDecimal.ZERO);
        BankAccount account = BankAccountDTOMapper.fromDTO(standardAccountDTO);
        assertTrue(account instanceof StandardAccount);
    }
}

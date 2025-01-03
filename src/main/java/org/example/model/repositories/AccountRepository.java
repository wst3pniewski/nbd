package org.example.model.repositories;

import org.example.model.accounts.BankAccount;
import org.example.model.accounts.SavingAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.clients.Client;
import org.example.model.dto.BankAccountDTO;
import org.example.model.dto.JuniorAccountDTO;
import org.example.model.dto.SavingAccountDTO;
import org.example.model.dto.StandardAccountDTO;
import org.example.model.mappers.BankAccountDTOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AccountRepository implements Repository<BankAccount> {

    public AccountRepository() {
        super();
    }

    public BankAccount add(BankAccount account) {
        if (account == null) {
            return null;
        }
//        bankAccounts.insertOne(BankAccountDTOMapper.toDTO(account));

        return account;
    }

    public List<BankAccount> findAll() {
//        return bankAccounts.find().into(new ArrayList<>()).stream().map(BankAccountDTOMapper::fromDTO).toList();
    }


    public BankAccount findById(UUID id) {
//        Bson filter = Filters.eq("_id", id);
//        BankAccountDTO account = bankAccounts.find(filter).first();
        if (account == null) {
            return null;
        }
        return BankAccountDTOMapper.fromDTO(account);
    }


    public BankAccount update(BankAccount account) {
//        if (account == null) {
//            return null;
//        }
//        BankAccountDTO accountDTO = BankAccountDTOMapper.toDTO(account);
//        Bson filter = Filters.eq("_id", account.getId());
//        Bson setUpdate = null;
//        if (accountDTO instanceof StandardAccountDTO) {
//            setUpdate = Updates.combine(
//                    Updates.set("balance", account.getBalance()),
//                    Updates.set("active", account.getActive()),
//                    Updates.set("closeDate", account.getCloseDate()),
//                    Updates.set("debit", ((StandardAccount) account).getDebit()),
//                    Updates.set("debitLimit", ((StandardAccount) account).getDebitLimit())
//            );
//        } else if (accountDTO instanceof SavingAccountDTO) {
//            setUpdate = Updates.combine(
//                    Updates.set("balance", account.getBalance()),
//                    Updates.set("active", account.getActive()),
//                    Updates.set("closeDate", account.getCloseDate()),
//                    Updates.set("interestRate", ((SavingAccount) account).getInterestRate())
//            );
//        } else if (accountDTO instanceof JuniorAccountDTO) {
//            setUpdate = Updates.combine(
//                    Updates.set("balance", account.getBalance()),
//                    Updates.set("active", account.getActive()),
//                    Updates.set("closeDate", account.getCloseDate())
//            );
//        }
//        if (setUpdate == null) {
//            return null;
//        }
//        bankAccounts.updateOne(filter, setUpdate);
//        return account;
    }

    public List<BankAccount> getAccountsByClientId(UUID clientId) {
//        Bson filter = Filters.eq("client._id", clientId);
//        return bankAccounts.find(filter).into(new ArrayList<>()).stream().map(BankAccountDTOMapper::fromDTO).toList();
    }


    public long countActiveByClientId(UUID clientId) {
//        Bson filter = Filters.and(
//                Filters.eq("client._id", clientId),
//                Filters.eq("active", true)
//        );
//        return bankAccounts.countDocuments(filter);
    }

    public boolean updateClient(Client client) {
//        Bson filter = Filters.eq("client._id", client.getId());
//        Bson setUpdate = Updates.set("client", client);
//        UpdateResult updateResult = bankAccounts.updateMany(filter, setUpdate);
//        return updateResult.getModifiedCount() != 0;
    }

    public void close() {}
}

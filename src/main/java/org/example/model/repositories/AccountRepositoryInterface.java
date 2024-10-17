package org.example.model.repositories;

import org.example.model.accounts.BankAccount;

import java.util.List;

public interface AccountRepositoryInterface {
    public List<BankAccount> getAccountsByClientId(Long clientId);
    public long countActiveByClientId(Long clientId);
    public BankAccount update(BankAccount account);
}

package org.example.model.repositories;

import org.example.model.accounts.BankAccount;

import java.util.List;

public interface AccountRepositoryInterface{
    BankAccount addAccount(BankAccount account);

    List<BankAccount> findAll();

    BankAccount findById(Long id);

    BankAccount updateAccount(BankAccount account);

    BankAccount deleteAccount(Long id);
}

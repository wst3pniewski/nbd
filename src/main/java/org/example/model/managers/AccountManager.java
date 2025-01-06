//package org.example.model.managers;
//
//
//import org.example.model.accounts.*;
//import org.example.model.clients.Client;
//import org.example.model.repositories.AccountRepository;
//import org.example.model.repositories.ClientRepository;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//public class AccountManager {
//    private final AccountRepository accountRepository;
//    private final ClientRepository clientRepository;
//
//
//    public AccountManager() {
//        this.accountRepository = new AccountRepository();
//        this.clientRepository = new ClientRepository();
//
//    }
//    // TODO: This doesn't seem to work with concurrency
//    public BankAccount createStandardAccount(UUID clientId, BigDecimal debitLimit) {
//
//        Client client = clientRepository.findById(clientId);
//
//        try (var session = mongoClient.startSession()) {
//            TransactionBody<BankAccount> transactionBody = (TransactionBody<BankAccount>) () -> {
//                int activeAccounts = client.getActiveAccounts();
//                if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
//                    throw new IllegalArgumentException("Client exceeded the limit of accounts");
//                }
//
//                client.setActiveAccounts((activeAccounts + 1));
//                clientRepository.update(client);
//
//                BankAccount account = new StandardAccount(client, debitLimit);
//                accountRepository.add(account);
//
//                return account;
//            };
//            return session.withTransaction(transactionBody);
//        }
//    }
//
//    public BankAccount createSavingAccount(UUID clientId, BigDecimal interestRate) {
//
//        Client client = clientRepository.findById(clientId);
//
//        try (var session = mongoClient.startSession()) {
//            TransactionBody<BankAccount> transactionBody = (TransactionBody<BankAccount>) () -> {
//                int activeAccounts = client.getActiveAccounts();
//                if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
//                    throw new IllegalArgumentException("Client exceeded the limit of accounts");
//                }
//
//                client.setActiveAccounts((activeAccounts + 1));
//                clientRepository.update(client);
//
//                BankAccount account = new SavingAccount(client, interestRate);
//                accountRepository.add(account);
//
//                return account;
//            };
//            return session.withTransaction(transactionBody);
//        }
//    }
//
//    public BankAccount createJuniorAccount(UUID clientId, UUID parentId) {
//
//        Client client = clientRepository.findById(clientId);
//        int clientAge = client.getDateOfBirth().until(LocalDate.now()).getYears();
//
//        if (clientAge >= 18) {
//            throw new IllegalArgumentException("Client is not a child");
//        }
//        Client parent = clientRepository.findById(parentId);
//        if (parent == null) {
//            throw new IllegalArgumentException("Parent not found");
//        }
//
//
//        try (var session = mongoClient.startSession()) {
//            TransactionBody<BankAccount> transactionBody = (TransactionBody<BankAccount>) () -> {
//                int activeAccounts = client.getActiveAccounts();
//                if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
//                    throw new IllegalArgumentException("Parent exceeded the limit of accounts");
//                }
//                client.setActiveAccounts((activeAccounts + 1));
//                clientRepository.update(client);
//                BankAccount account = new JuniorAccount(client, parent);
//
//                accountRepository.add(account);
//
//                return account;
//            };
//
//            return session.withTransaction(transactionBody);
//
//        }
//    }
//
//    public BankAccount closeAccount(UUID accountId) {
//
//        BankAccount account = accountRepository.findById(accountId);
//
//        try (var session = mongoClient.startSession()) {
//            TransactionBody<BankAccount> transactionBody = (TransactionBody<BankAccount>) () -> {
//                Client client = clientRepository.findById(account.getClient().getId());
//                int activeAccounts = client.getActiveAccounts();
//
//                account.setActive(false);
//                account.setCloseDate(LocalDate.now());
//                accountRepository.update(account);
//
//                return account;
//            };
//
//            return session.withTransaction(transactionBody);
//
//        }
//    }
//
//    public BankAccount depositMoney(UUID accountId, BigDecimal amount) {
//        BankAccount account = null;
//
//        account = accountRepository.findById(accountId);
//        account.setBalance(amount);
//        accountRepository.update(account);
//
//        return account;
//    }
//
//    public BankAccount withdrawMoney(UUID accountId, BigDecimal amount) {
//        BankAccount account = null;
//
//        account = accountRepository.findById(accountId);
//        BigDecimal balance = account.getBalance();
//        if (balance.compareTo(amount) < 0) {
//            throw new IllegalArgumentException("Insufficient balance");
//        }
//        account.setBalance(balance.subtract(amount));
//        accountRepository.update(account);
//
//        return account;
//    }
//
//    public BankAccount interestCalculation(UUID accountId) {
//        BankAccount account = null;
//
//        account = accountRepository.findById(accountId);
//        if (account instanceof SavingAccount) {
//            BigDecimal interestRate = ((SavingAccount) account).getInterestRate();
//            BigDecimal balance = account.getBalance();
//            BigDecimal interest = balance.multiply(interestRate);
//            account.setBalance(balance.add(interest));
//            accountRepository.update(account);
//        } else {
//            throw new IllegalArgumentException("Account is not a saving account");
//        }
//
//
//        return account;
//    }
//
//    public BankAccount payDebt(UUID accountId, BigDecimal amount) {
//        BankAccount account = null;
//
//        account = accountRepository.findById(accountId);
//        if (account instanceof StandardAccount) {
//            BigDecimal debit = ((StandardAccount) account).getDebit();
//            BigDecimal amountDebitSubtract = amount.subtract(debit);
//            if (amountDebitSubtract.compareTo(BigDecimal.ZERO) >= 0) {
//                account.setBalance(account.getBalance().add(amountDebitSubtract));
//                ((StandardAccount) account).setDebit(BigDecimal.ZERO);
//            } else {
//                ((StandardAccount) account).setDebit(debit.subtract(amount));
//            }
//            accountRepository.update(account);
//        } else {
//
//            throw new IllegalArgumentException("Account is not a standard account");
//        }
//
//        return account;
//    }
//
//    public BankAccount findById(UUID id) {
//        return accountRepository.findById(id);
//    }
//
//    public List<BankAccount> findAll() {
//        return accountRepository.findAll();
//    }
//
//    public BankAccount update(BankAccount account) {
//        return accountRepository.update(account);
//    }
//
//    public List<BankAccount> getAccountsByClientId(UUID clientId) {
//        return accountRepository.getAccountsByClientId(clientId);
//    }
//
//    public long countActiveByClientId(UUID clientId) {
//        return accountRepository.countActiveByClientId(clientId);
//    }
//
//}

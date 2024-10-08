package org.example.model.accounts;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import org.example.model.clients.Client;

import java.math.BigDecimal;

@Entity
@Access(AccessType.FIELD)
public class CurrencyAccount extends BankAccount{
    public enum Currency {
        USD(new BigDecimal("3.8")),
        CHF(new BigDecimal("4.5")),
        EUR(new BigDecimal("4.3")),
        PLN(new BigDecimal("1.0"));

        private final BigDecimal value;

        Currency(BigDecimal value) {
            this.value = value;
        }

        public BigDecimal getValue() {
            return value;
        }
    }

    Currency currency;

    public CurrencyAccount(Client client, Currency currency) {
        super(client);
        this.currency = currency;
    }

    @Override
    public String getAccountType() {
        return "currency";
    }

    @Override
    public String getAccountInfo() {
        return "";
    }
}

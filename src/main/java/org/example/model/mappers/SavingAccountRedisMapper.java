package org.example.model.mappers;

import org.example.model.accounts.SavingAccount;
import org.example.model.redis.SavingAccountRedis;

public class SavingAccountRedisMapper {
    public static SavingAccountRedis toRedis(SavingAccount savingAccount) {
        if (savingAccount == null) {
            return null;
        }
        return new SavingAccountRedis(
                savingAccount.getId(),
                savingAccount.getBalance(),
                ClientRedisMapper.toRedis(savingAccount.getClient()),
                savingAccount.getActive(),
                savingAccount.getCreationDate(),
                savingAccount.getCloseDate(),
                savingAccount.getInterestRate()
        );
    }

    public static SavingAccount fromRedis(SavingAccountRedis savingAccountRedis) {
        if (savingAccountRedis == null) {
            return null;
        }
        return new SavingAccount(
                savingAccountRedis.getId(),
                savingAccountRedis.getBalance(),
                ClientRedisMapper.fromRedis(savingAccountRedis.getClient()),
                savingAccountRedis.getActive(),
                savingAccountRedis.getCreationDate(),
                savingAccountRedis.getCloseDate(),
                savingAccountRedis.getInterestRate()
        );
    }
}

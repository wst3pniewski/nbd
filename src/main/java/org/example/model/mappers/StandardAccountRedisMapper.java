package org.example.model.mappers;

import org.example.model.accounts.StandardAccount;
import org.example.model.redis.StandardAccountRedis;

public class StandardAccountRedisMapper {
    public static StandardAccountRedis toRedis(StandardAccount standardAccount) {
        if (standardAccount == null) {
            return null;
        }
        return new StandardAccountRedis(
                standardAccount.getId(),
                standardAccount.getBalance(),
                ClientRedisMapper.toRedis(standardAccount.getClient()),
                standardAccount.getActive(),
                standardAccount.getCreationDate(),
                standardAccount.getCloseDate(),
                standardAccount.getDebitLimit(),
                standardAccount.getDebit()
        );
    }

    public static StandardAccount fromRedis(StandardAccountRedis standardAccountRedis) {
        if (standardAccountRedis == null) {
            return null;
        }
        return new StandardAccount(
                standardAccountRedis.getId(),
                standardAccountRedis.getBalance(),
                ClientRedisMapper.fromRedis(standardAccountRedis.getClient()),
                standardAccountRedis.getActive(),
                standardAccountRedis.getCreationDate(),
                standardAccountRedis.getCloseDate(),
                standardAccountRedis.getDebitLimit(),
                standardAccountRedis.getDebit()
        );
    }
}

package org.example.model.mappers;

import org.example.model.accounts.JuniorAccount;
import org.example.model.clients.Client;
import org.example.model.dto.JuniorAccountDTO;
import org.example.model.redis.JuniorAccountRedis;

public class JuniorAccountRedisMapper {
    public static JuniorAccountRedis toRedis(JuniorAccount juniorAccount) {
        if (juniorAccount == null) {
            return null;
        }
        return new JuniorAccountRedis(
                juniorAccount.getId(),
                juniorAccount.getBalance(),
                ClientRedisMapper.toRedis(juniorAccount.getClient()),
                juniorAccount.getActive(),
                juniorAccount.getCreationDate(),
                juniorAccount.getCloseDate(),
                ClientRedisMapper.toRedis(juniorAccount.getParent())
        );
    }

    public static JuniorAccount fromRedis(JuniorAccountRedis juniorAccountRedis) {
        if (juniorAccountRedis== null) {
            return null;
        }
        return new JuniorAccount(
                juniorAccountRedis.getId(),
                juniorAccountRedis.getBalance(),
                ClientRedisMapper.fromRedis(juniorAccountRedis.getClient()),
                juniorAccountRedis.getActive(),
                juniorAccountRedis.getCreationDate(),
                juniorAccountRedis.getCloseDate(),
                ClientRedisMapper.fromRedis(juniorAccountRedis.getParent())
        );
    }
}

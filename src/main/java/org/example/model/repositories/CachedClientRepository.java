package org.example.model.repositories;


import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.example.model.RedisCache;
import org.example.model.clients.Client;
import org.example.model.dto.ClientDTO;
import org.example.model.mappers.ClientDTOMapper;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CachedClientRepository implements Repository<Client> {
    private final RedisCache redisCache;
    private final ClientRepository mongoClientRepository;
    private final Jsonb jsonb = JsonbBuilder.create();;
    private final String hashPrefix = "client:";

    public CachedClientRepository(ClientRepository clientRepository, RedisCache redisCache) {
        this.mongoClientRepository = clientRepository;
        this.redisCache = redisCache;
    }

    public Client add(Client client){
        if (client == null) {
            return null;
        }

        mongoClientRepository.add(client);
        try {
            String json = jsonb.toJson(ClientDTOMapper.toDTO(client));
            String res = redisCache.jsonSet(hashPrefix + client.getId().toString(), json);
            if (Objects.equals(res, "OK")) {
                return ClientDTOMapper.fromDTO(jsonb.fromJson(json, ClientDTO.class));
            }
        } catch (Exception e) {
            System.err.println("Failed write to Redis: " + e.getMessage());
        }

        return null;
    }

    public Client findById(UUID id){
        try{
            Object obj = redisCache.jsonGet(hashPrefix + id.toString());
            if (obj != null) {
                String json = jsonb.toJson(obj);
                return ClientDTOMapper.fromDTO(jsonb.fromJson(json, ClientDTO.class));
            }
        // TODO: catch specific exception
        } catch (Exception e) {
            System.err.println("Redis connection failed, falling back to MongoDB: " + e.getMessage());
        }

        Client client = mongoClientRepository.findById(id);
        if (client != null) {
            try {
                String json = jsonb.toJson(client);
                redisCache.jsonSet(hashPrefix + client.getId().toString(), json);
            // TODO: catch specific exception
            } catch (Exception e) {
                System.err.println("Failed write to Redis: " + e.getMessage());
            }
        }


        return client;
    }

    public List<Client> findAll(){
        return mongoClientRepository.findAll();
    }

    public boolean delete(UUID id){
        Boolean mongoUpdStatus = mongoClientRepository.delete(id);
        try{
            long res = redisCache.jsonDel(hashPrefix + id.toString());
            return res != 0;
        } catch (Exception e) {
            System.err.println("Failed write to Redis: " + e.getMessage());
        }
        return mongoUpdStatus;
    }

    public Client update(Client client){
        if (client == null) {
            return null;
        }
        try{
            String json = jsonb.toJson(client);
            redisCache.jsonSet(hashPrefix + client.getId().toString(), json);
            return client;
        } catch (Exception e) {
            System.err.println("Failed write to Redis: " + e.getMessage());
        }
        mongoClientRepository.update(client);
        return client;
    }
}

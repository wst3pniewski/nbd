package org.example.model;

import org.example.model.clients.Client;
import org.example.model.repositories.CachedClientRepository;
import org.example.model.repositories.ClientRepository;
import org.openjdk.jmh.annotations.*;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class PerformanceTest {
    private RedisCache redisCache;
    private CachedClientRepository cachedRepo;
    private ClientRepository mongoRepo;

    private Client client;

    @Setup
    public void setup() {
        redisCache = new RedisCache();
        mongoRepo = new ClientRepository();
        cachedRepo = new CachedClientRepository(mongoRepo, redisCache);

        LocalDate  dateOfBirth = LocalDate.of(2000, 1, 1);
        client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Street",
                "City", "42");

        cachedRepo.add(client);
    }

    @Benchmark
    public void testMongoDBRead() {
        mongoRepo.findById(client.getId());
    }

    @Benchmark
    public void testRedisRead() {
        cachedRepo.findById(client.getId());
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}

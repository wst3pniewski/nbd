package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import org.example.model.vehicles.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HibernateExampleTest {

    private SessionFactory sessionFactory;
    private static EntityManagerFactory emf;
    private static EntityManager em;


    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU");
        em = emf.createEntityManager();
    }
    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }
    @Test
    public void test() {
        Vehicle vehicle = em.find(Car.class, Long.valueOf(1));
        assertNull(vehicle);
    }

    @Test
    public void insertAndSelectVehicle() {
        Vehicle vehicle = new Car("abc123", 150, 1500, Car.Segment.B);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(vehicle);
        transaction.commit();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vehicle> query = cb.createQuery(Vehicle.class);
        From<Vehicle, Vehicle> from = query.from(Vehicle.class);
        query.select(from).where(cb.equal(from.get(Vehicle_.plateNumber), "abc123"));
//        query.select(from);
        Vehicle found = em.createQuery(query).getSingleResult();
        assertEquals("abc123", found.getPlateNumber());
    }

    @Test
    public void countVehicles() {
        Vehicle vehicle = new Car("123abc", 150, 1500, Car.Segment.B);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(vehicle);
        transaction.commit();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        From<Vehicle, Vehicle> from = query.from(Vehicle.class);
        query.select(cb.count(from));
        System.out.println(em.createQuery(query).getSingleResult());
    }
}

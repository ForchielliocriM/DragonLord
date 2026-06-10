package it.unicam.cs.mpgc.rpg118716.persistence.repository;

import it.unicam.cs.mpgc.rpg118716.persistence.HibernateUtil;
import it.unicam.cs.mpgc.rpg118716.persistence.entity.HeroEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

/**
 * Hibernate implementation of HeroRepository.
 * Only handles DB operations for HeroEntity.
 */
public class HeroRepositoryImpl implements HeroRepository {

    @Override
    public void save(HeroEntity hero) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(hero); // insert or update
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Failed to save hero", e);
        }
    }

    @Override
    public Optional<HeroEntity> loadLatest() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            HeroEntity hero = session.createQuery(
                            "FROM HeroEntity ORDER BY id DESC",
                            HeroEntity.class)
                    .setMaxResults(1)
                    .uniqueResult();
            return Optional.ofNullable(hero);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load hero", e);
        }
    }

    @Override
    public void deleteAll() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createMutationQuery("DELETE FROM HeroEntity").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Failed to delete heroes", e);
        }
    }
}

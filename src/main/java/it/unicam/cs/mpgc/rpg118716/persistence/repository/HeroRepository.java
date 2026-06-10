package it.unicam.cs.mpgc.rpg118716.persistence.repository;

import it.unicam.cs.mpgc.rpg118716.persistence.entity.HeroEntity;

import java.util.Optional;

/**
 * Repository interface for Hero persistence operations.
 * The service layer depends on this abstraction, not on the concrete Hibernate implementation.
 * A different implementation (e.g. JSON, XML save) can be swapped without
 * changing the service layer.
 */
public interface HeroRepository {

    /**
     * Saves or updates the given HeroEntity in the database.
     *
     * @param hero the entity to persist
     */
    void save(HeroEntity hero);

    /**
     * Loads the most recent saved Hero from the database.
     *
     * @return an Optional containing the HeroEntity, or empty if no save exists
     */
    Optional<HeroEntity> loadLatest();

    /**
     * Deletes all saved heroes from the database.
     * Used to start a new game.
     */
    void deleteAll();
}
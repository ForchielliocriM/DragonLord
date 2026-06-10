package it.unicam.cs.mpgc.rpg118716.persistence;

import it.unicam.cs.mpgc.rpg118716.model.character.Hero;
import it.unicam.cs.mpgc.rpg118716.model.character.Stats;
import it.unicam.cs.mpgc.rpg118716.persistence.entity.HeroEntity;

/**
 * Converts between the domain Hero and the persistence HeroEntity.
 * Only handles the mapping between layers.
 * Keeps the domain model and persistence layer decoupled.
 */
public class HeroMapper {

    private HeroMapper() {}

    /**
     * Converts a domain Hero into a HeroEntity ready to be persisted.
     *
     * @param hero      the domain Hero
     * @param positionX the current X position on the map
     * @param positionY the current Y position on the map
     * @return a HeroEntity representing the current state
     */
    public static HeroEntity toEntity(Hero hero, int positionX, int positionY) {
        return new HeroEntity(
                hero.getName(),
                hero.getLevel(),
                hero.getExperience(),
                hero.getGold(),
                hero.getStats().getMaxHp(),
                hero.getStats().getCurrentHp(),
                hero.getStats().getAttack(),
                hero.getStats().getDefense(),
                hero.getStats().getSpeed(),
                positionX,
                positionY
        );
    }

    /**
     * Converts a HeroEntity from the DB back into a domain Hero.
     *
     * @param entity the persisted entity
     * @return a fully restored Hero
     */
    public static Hero toDomain(HeroEntity entity) {
        Stats stats = new Stats(
                entity.getMaxHp(),
                entity.getAttack(),
                entity.getDefense(),
                entity.getSpeed()
        );

        // Restores the current saved HP
        stats.setCurrentHp(entity.getCurrentHp());

        Hero hero = new Hero(entity.getName(), stats);

        // Restore level, experience and gold
        hero.setLevel(entity.getLevel());
        hero.setExperience(entity.getExperience());
        hero.setGold(entity.getGold());

        return hero;
    }
}
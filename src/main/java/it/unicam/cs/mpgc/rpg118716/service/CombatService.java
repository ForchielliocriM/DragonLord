package it.unicam.cs.mpgc.rpg118716.service;

import it.unicam.cs.mpgc.rpg118716.model.character.Hero;
import it.unicam.cs.mpgc.rpg118716.model.character.Monster;

/**
 * Service interface for turn-based combat logic.
 * Only handles combat resolution.
 * UI depends on this interface, not the implementation.
 */
public interface CombatService {

    /**
     * Initializes a new combat session between the hero and a monster.
     *
     * @param hero    the player's hero
     * @param monster the encountered monster
     */
    void startCombat(Hero hero, Monster monster);

    /**
     * Executes the hero's attack against the monster.
     *
     * @return the damage dealt
     */
    int heroAttacks();

    /**
     * Executes the monster's attack against the hero.
     *
     * @return the damage dealt
     */
    int monsterAttacks();

    /**
     * Attempts to flee from combat.
     *
     * @return true if the hero successfully escapes
     */
    boolean heroRuns();

    /**
     * Returns whether the combat is still ongoing.
     *
     * @return true if both combatants are alive and hero has not fled
     */
    boolean isCombatOngoing();

    /**
     * Returns whether the hero won the combat.
     *
     * @return true if the monster is defeated
     */
    boolean isHeroVictorious();

    /**
     * Returns the monster currently being fought.
     *
     * @return the current Monster
     */
    Monster getCurrentMonster();
}

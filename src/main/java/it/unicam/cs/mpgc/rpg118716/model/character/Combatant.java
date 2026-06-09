package it.unicam.cs.mpgc.rpg118716.model.character;

/**
 * Represents any entity that can participate in combat.
 * Extends StatsOwner since combat requires stats.
 */
public interface Combatant extends StatsOwner {

    /**
     * Returns the name of this combatant.
     *
     * @return the name
     */
    String getName();

    /**
     * Returns whether this combatant is still alive.
     *
     * @return true if HP > 0
     */
    boolean isAlive();

    /**
     * Applies damage to this combatant, reducing current HP.
     *
     * @param amount the amount of damage to apply (must be >= 0)
     */
    void takeDamage(int amount);
}

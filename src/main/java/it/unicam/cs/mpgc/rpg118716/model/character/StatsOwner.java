package it.unicam.cs.mpgc.rpg118716.model.character;

/**
 * Represents any entity that owns a set of stats.
 */
public interface StatsOwner {

    /**
     * Returns the stats of this entity.
     *
     * @return the Stats object associated with this entity
     */
    Stats getStats();
}

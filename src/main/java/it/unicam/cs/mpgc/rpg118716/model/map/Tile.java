package it.unicam.cs.mpgc.rpg118716.model.map;

/**
 * Represents a single cell on the game map.
 */
public interface Tile {

    TileType getType();

    /**
     * Returns whether the hero can walk on this tile.
     */
    boolean isWalkable();
}

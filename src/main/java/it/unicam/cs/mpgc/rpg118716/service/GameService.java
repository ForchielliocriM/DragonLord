package it.unicam.cs.mpgc.rpg118716.service;

import it.unicam.cs.mpgc.rpg118716.model.character.Hero;
import it.unicam.cs.mpgc.rpg118716.model.map.GameMap;

/**
 * Main service interface for game logic.
 * UI depends on this interface, not the implementation.
 */
public interface GameService {

    /**
     * Starts a new game with the given hero name.
     *
     * @param heroName the name chosen by the player
     */
    void startNewGame(String heroName);

    /**
     * Loads the most recently saved game from the database.
     *
     * @return true if a save was found and loaded, false otherwise
     */
    boolean loadGame();

    /**
     * Saves the current game state to the database.
     */
    void saveGame();

    /**
     * Moves the hero in the given direction if the tile is walkable.
     *
     * @param direction the direction to move
     * @return true if the move was successful
     */
    boolean moveHero(Direction direction);

    /**
     * Returns the current hero instance.
     *
     * @return the Hero
     */
    Hero getHero();

    /**
     * Returns the current game map.
     *
     * @return the GameMap
     */
    GameMap getGameMap();

    /**
     * Returns the hero's current X position on the map.
     */
    int getHeroX();

    /**
     * Returns the hero's current Y position on the map.
     */
    int getHeroY();

    /**
     * Returns whether a combat encounter should trigger
     * based on the current tile.
     *
     * @return true if a random encounter should start
     */
    boolean shouldTriggerEncounter();
}
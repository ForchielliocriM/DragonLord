package it.unicam.cs.mpgc.rpg118716.model.map;

/**
 * Concrete implementation of a map tile.
 */
public class GameTile implements Tile {

    private final TileType type;

    public GameTile(TileType type) {
        this.type = type;
    }

    @Override
    public TileType getType() { return type; }

    @Override
    public boolean isWalkable() {
        return type != TileType.WALL;
    }
}

package it.unicam.cs.mpgc.rpg118716.model.map;

/**
 * Represents the game world map as a 2D grid of tiles.
 */
public class GameMap {

    private final int width;
    private final int height;
    private final Tile[][] tiles;

    public GameMap(int width, int height, Tile[][] tiles) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
    }

    public int getWidth()  { return width; }
    public int getHeight() { return height; }

    /**
     * Returns the tile at the given coordinates.
     *
     * @param x column index
     * @param y row index
     * @return the Tile at (x, y)
     * @throws IllegalArgumentException if coordinates are out of bounds
     */
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException("Coordinates out of bounds: " + x + ", " + y);
        return tiles[y][x];
    }

    /**
     * Returns whether the hero can move to the given position.
     */
    public boolean isWalkable(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return false;
        return tiles[y][x].isWalkable();
    }
}

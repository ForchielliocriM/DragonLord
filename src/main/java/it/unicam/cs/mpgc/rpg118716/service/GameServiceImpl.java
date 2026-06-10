package it.unicam.cs.mpgc.rpg118716.service;

import it.unicam.cs.mpgc.rpg118716.model.character.Hero;
import it.unicam.cs.mpgc.rpg118716.model.map.GameMap;
import it.unicam.cs.mpgc.rpg118716.model.map.GameTile;
import it.unicam.cs.mpgc.rpg118716.model.map.Tile;
import it.unicam.cs.mpgc.rpg118716.model.map.TileType;
import it.unicam.cs.mpgc.rpg118716.model.character.Stats;
import it.unicam.cs.mpgc.rpg118716.persistence.HeroMapper;
import it.unicam.cs.mpgc.rpg118716.persistence.entity.HeroEntity;
import it.unicam.cs.mpgc.rpg118716.persistence.repository.HeroRepository;

import java.util.Optional;
import java.util.Random;

/**
 * Implements core game logic: new game, load, save, and hero movement.
 */
public class GameServiceImpl implements GameService {

    private static final double ENCOUNTER_CHANCE = 0.2;

    private final HeroRepository heroRepository;
    private final Random random = new Random();

    private Hero hero;
    private GameMap gameMap;
    private int heroX;
    private int heroY;

    /**
     * Constructor injection — depends on the HeroRepository abstraction,
     * not on the concrete implementation.
     *
     * @param heroRepository the repository used for persistence
     */
    public GameServiceImpl(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
        this.gameMap = buildStarterMap();
    }

    @Override
    public void startNewGame(String heroName) {
        Stats startingStats = new Stats(30, 10, 5, 5);
        hero = new Hero(heroName, startingStats);
        heroX = 1;
        heroY = 1;
        heroRepository.deleteAll();
    }

    @Override
    public boolean loadGame() {
        Optional<HeroEntity> saved = heroRepository.loadLatest();
        if (saved.isEmpty()) return false;

        HeroEntity entity = saved.get();
        hero = HeroMapper.toDomain(entity);
        heroX = entity.getPositionX();
        heroY = entity.getPositionY();
        return true;
    }

    @Override
    public void saveGame() {
        if (hero == null) return;
        HeroEntity entity = HeroMapper.toEntity(hero, heroX, heroY);
        heroRepository.save(entity);
    }

    @Override
    public boolean moveHero(Direction direction) {
        int newX = heroX;
        int newY = heroY;

        switch (direction) {
            case UP    -> newY--;
            case DOWN  -> newY++;
            case LEFT  -> newX--;
            case RIGHT -> newX++;
        }

        if (!gameMap.isWalkable(newX, newY)) return false;

        heroX = newX;
        heroY = newY;
        return true;
    }

    @Override
    public boolean shouldTriggerEncounter() {
        TileType currentTile = gameMap.getTile(heroX, heroY).getType();
        // Monsters only appear on grass and forest
        if (currentTile != TileType.GRASS && currentTile != TileType.FOREST)
            return false;
        return random.nextDouble() < ENCOUNTER_CHANCE;
    }

    @Override
    public Hero getHero()       { return hero; }

    @Override
    public GameMap getGameMap() { return gameMap; }

    @Override
    public int getHeroX()       { return heroX; }

    @Override
    public int getHeroY()       { return heroY; }

    /**
     * Builds the starter map for the mini-level.
     * In the future this could load from XML or a file.
     */
    private GameMap buildStarterMap() {
        TileType[][] layout = {
                { TileType.WALL,   TileType.WALL,   TileType.WALL,   TileType.WALL,   TileType.WALL   },
                { TileType.WALL,   TileType.TOWN,   TileType.GRASS,  TileType.GRASS,  TileType.WALL   },
                { TileType.WALL,   TileType.GRASS,  TileType.FOREST, TileType.GRASS,  TileType.WALL   },
                { TileType.WALL,   TileType.GRASS,  TileType.GRASS,  TileType.DUNGEON,TileType.WALL   },
                { TileType.WALL,   TileType.WALL,   TileType.WALL,   TileType.WALL,   TileType.WALL   }
        };

        int height = layout.length;
        int width  = layout[0].length;
        Tile[][] tiles = new Tile[height][width];

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                tiles[y][x] = new GameTile(layout[y][x]);

        return new GameMap(width, height, tiles);
    }
}

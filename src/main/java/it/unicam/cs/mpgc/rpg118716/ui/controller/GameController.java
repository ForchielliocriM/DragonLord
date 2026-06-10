package it.unicam.cs.mpgc.rpg118716.ui.controller;

import it.unicam.cs.mpgc.rpg118716.model.character.Monster;
import it.unicam.cs.mpgc.rpg118716.model.map.GameMap;
import it.unicam.cs.mpgc.rpg118716.model.map.TileType;
import it.unicam.cs.mpgc.rpg118716.service.*;
import it.unicam.cs.mpgc.rpg118716.ui.SceneManager;
import it.unicam.cs.mpgc.rpg118716.xml.MonsterXmlLoader;
import it.unicam.cs.mpgc.rpg118716.xml.XmlLoadException;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

/**
 * Controller for the main game screen.
 * Handles map rendering, keyboard input, and encounter triggering.
 */
public class GameController {

    private static final int TILE_SIZE = 64;

    @FXML private Canvas mapCanvas;
    @FXML private Label heroNameLabel;
    @FXML private Label heroHpLabel;
    @FXML private Label heroLevelLabel;
    @FXML private Label heroGoldLabel;
    @FXML private Label logLabel;
    @FXML private javafx.scene.layout.BorderPane rootPane;


    private GameService gameService;
    private List<Monster> availableMonsters;
    private final Random random = new Random();

    /**
     * Called by SceneManager after loading the FXML.
     * Injects the GameService and sets up the view.
     */
    public void initGame(GameService gameService) {
        this.gameService = gameService;
        loadMonsters();
        updateStatsBar();
        drawMap();
        setupKeyHandler();
    }

    /**
     * Loads monsters from XML for random encounters.
     */
    private void loadMonsters() {
        try {
            availableMonsters = new MonsterXmlLoader().load("data/monsters.xml");
        } catch (XmlLoadException e) {
            availableMonsters = new java.util.ArrayList<>();
            logLabel.setText("Attenzione: impossibile caricare i mostri.");
        }
    }

    /**
     * Registers the keyboard handler on the canvas scene.
     */
    private void setupKeyHandler() {
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::handleKeyPress);
            }
        });
    }

    /**
     * Handles WASD key input for movement.
     */
    private void handleKeyPress(KeyEvent event) {
        Direction direction = switch (event.getCode()) {
            case W -> Direction.UP;
            case S -> Direction.DOWN;
            case A -> Direction.LEFT;
            case D -> Direction.RIGHT;
            default -> null;
        };

        if (direction == null) return;

        boolean moved = gameService.moveHero(direction);
        if (!moved) {
            logLabel.setText("Non puoi andare in quella direzione!");
            return;
        }

        updateStatsBar();
        drawMap();
        logLabel.setText("Ti sei mosso verso " + direction.name().toLowerCase() + ".");

        if (gameService.shouldTriggerEncounter() && !availableMonsters.isEmpty()) {
            Monster monster = availableMonsters.get(
                    random.nextInt(availableMonsters.size()));
            triggerEncounter(monster);
        }
    }

    /**
     * Starts a combat encounter with the given monster.
     */
    private void triggerEncounter(Monster monster) {
        logLabel.setText("Un " + monster.getName() + " appare!");
        CombatService combatService = new CombatServiceImpl();
        combatService.startCombat(gameService.getHero(), monster);
        SceneManager.getInstance().showCombatView(gameService, combatService, monster);
    }

    /**
     * Draws the game map and the hero on the canvas.
     */
    private void drawMap() {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        GameMap map = gameService.getGameMap();

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                TileType type = map.getTile(x, y).getType();
                gc.setFill(tileColor(type));
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                // Game grid
                gc.setStroke(Color.rgb(0, 0, 0, 0.3));
                gc.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                // Emoji tile
                gc.fillText(tileEmoji(type),
                        x * TILE_SIZE + 20,
                        y * TILE_SIZE + 38);
            }
        }

        // Draw the hero
        gc.setFill(Color.WHITE);
        gc.fillText("🧙",
                gameService.getHeroX() * TILE_SIZE + 18,
                gameService.getHeroY() * TILE_SIZE + 40);
    }

    private void updateStatsBar() {
        heroNameLabel.setText(gameService.getHero().getName());
        heroHpLabel.setText("HP: " + gameService.getHero().getStats().getCurrentHp()
                + "/" + gameService.getHero().getStats().getMaxHp());
        heroLevelLabel.setText("Lv: " + gameService.getHero().getLevel());
        heroGoldLabel.setText("G: " + gameService.getHero().getGold());
    }

    @FXML
    public void onSave() {
        gameService.saveGame();
        logLabel.setText("Partita salvata!");
    }

    @FXML
    public void onMenu() {
        SceneManager.getInstance().showMainMenu();
    }

    private Color tileColor(TileType type) {
        return switch (type) {
            case GRASS   -> Color.rgb(80, 160, 80);
            case FOREST  -> Color.rgb(30, 100, 30);
            case TOWN    -> Color.rgb(180, 140, 80);
            case DUNGEON -> Color.rgb(60, 60, 80);
            case WALL    -> Color.rgb(30, 30, 30);
        };
    }

    private String tileEmoji(TileType type) {
        return switch (type) {
            case GRASS   -> "🌿";
            case FOREST  -> "🌲";
            case TOWN    -> "🏰";
            case DUNGEON -> "⚔";
            case WALL    -> "  ";
        };
    }
}


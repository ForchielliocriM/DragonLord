package it.unicam.cs.mpgc.rpg118716.ui;

import it.unicam.cs.mpgc.rpg118716.model.character.Monster;
import it.unicam.cs.mpgc.rpg118716.service.CombatService;
import it.unicam.cs.mpgc.rpg118716.service.GameService;
import it.unicam.cs.mpgc.rpg118716.ui.controller.CombatController;
import it.unicam.cs.mpgc.rpg118716.ui.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Singleton that manages scene transitions.
 * Only handles navigation between screens.
 * New scenes can be added without modifying existing controllers.
 */
public class SceneManager {

    private static final int WIDTH  = 600;
    private static final int HEIGHT = 500;

    private static SceneManager instance;
    private Stage primaryStage;

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) instance = new SceneManager();
        return instance;
    }

    public void initialize(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Shows the main menu screen.
     */
    public void showMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/main-menu.fxml"));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        } catch (IOException e) {
            throw new RuntimeException("Cannot load main-menu.fxml", e);
        }
    }

    /**
     * Shows the game screen, injecting the GameService into the controller.
     *
     * @param gameService the service managing game state
     */
    public void showGameView(GameService gameService) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/game-view.fxml"));
            Parent root = loader.load();

            GameController controller = loader.getController();
            controller.initGame(gameService);

            Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load game-view.fxml", e);
        }
    }

    /**
     * Shows the combat screen.
     *
     * @param gameService   the current game service
     * @param combatService the service managing combat
     * @param monster       the monster being fought
     */
    public void showCombatView(GameService gameService,
                               CombatService combatService,
                               Monster monster) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/combat-view.fxml"));
            Parent root = loader.load();

            CombatController controller = loader.getController();
            controller.initCombat(gameService, combatService, monster);

            Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load combat-view.fxml", e);
        }
    }
}

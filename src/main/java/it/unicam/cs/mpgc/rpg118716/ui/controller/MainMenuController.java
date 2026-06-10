package it.unicam.cs.mpgc.rpg118716.ui.controller;

import it.unicam.cs.mpgc.rpg118716.persistence.repository.HeroRepositoryImpl;
import it.unicam.cs.mpgc.rpg118716.service.GameService;
import it.unicam.cs.mpgc.rpg118716.service.GameServiceImpl;
import it.unicam.cs.mpgc.rpg118716.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Controller for the main menu screen.
 * Handles only main menu user interactions.
 */
public class MainMenuController {

    @FXML
    public void onNewGame() {
        TextInputDialog dialog = new TextInputDialog("Hero");
        dialog.setTitle("Nuova Partita");
        dialog.setHeaderText("Come si chiama il tuo eroe?");
        dialog.setContentText("Nome:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (name.isBlank()) return;
            GameService gameService = buildGameService();
            gameService.startNewGame(name.trim());
            SceneManager.getInstance().showGameView(gameService);
        });
    }

    @FXML
    public void onLoadGame() {
        GameService gameService = buildGameService();
        boolean loaded = gameService.loadGame();

        if (loaded) {
            SceneManager.getInstance().showGameView(gameService);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nessun salvataggio");
            alert.setHeaderText(null);
            alert.setContentText("Nessuna partita salvata trovata.");
            alert.showAndWait();
        }
    }

    @FXML
    public void onExit() {
        javafx.application.Platform.exit();
    }

    /**
     * Builds the GameService with its concrete dependencies.
     */
    private GameService buildGameService() {
        return new GameServiceImpl(new HeroRepositoryImpl());
    }
}

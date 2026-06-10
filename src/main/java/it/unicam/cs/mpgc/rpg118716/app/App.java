package it.unicam.cs.mpgc.rpg118716.app;

import it.unicam.cs.mpgc.rpg118716.persistence.HibernateUtil;
import it.unicam.cs.mpgc.rpg118716.ui.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX Application entry point.
 * Responsible for initializing the stage and launching the main menu.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DragonLord");
        primaryStage.setResizable(false);

        SceneManager.getInstance().initialize(primaryStage);
        SceneManager.getInstance().showMainMenu();

        primaryStage.show();
    }

    @Override
    public void stop() {
        // Correctly closes Hibernate when the window is closed
        HibernateUtil.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
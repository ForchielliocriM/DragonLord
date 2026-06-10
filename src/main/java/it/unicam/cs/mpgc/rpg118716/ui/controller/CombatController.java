package it.unicam.cs.mpgc.rpg118716.ui.controller;

import it.unicam.cs.mpgc.rpg118716.model.character.Monster;
import it.unicam.cs.mpgc.rpg118716.service.CombatService;
import it.unicam.cs.mpgc.rpg118716.service.GameService;
import it.unicam.cs.mpgc.rpg118716.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Controller for the combat screen.
 * Handles only combat UI interactions.
 */
public class CombatController {

    @FXML private Label heroNameLabel;
    @FXML private Label heroHpLabel;
    @FXML private Label monsterNameLabel;
    @FXML private Label monsterHpLabel;
    @FXML private TextArea combatLog;
    @FXML private Button attackButton;
    @FXML private Button runButton;

    private GameService gameService;
    private CombatService combatService;

    /**
     * Called by SceneManager after loading the FXML.
     */
    public void initCombat(GameService gameService,
                           CombatService combatService,
                           Monster monster) {
        this.gameService = gameService;
        this.combatService = combatService;
        combatService.startCombat(gameService.getHero(), monster);
        updateUI();
        log("Un " + monster.getName() + " ti attacca! Cosa fai?");
    }

    @FXML
    public void onAttack() {
        // Hero's Turn
        int heroDmg = combatService.heroAttacks();
        log("Attacchi il " + combatService.getCurrentMonster().getName()
                + " per " + heroDmg + " danni!");

        if (combatService.isHeroVictorious()) {
            handleVictory();
            return;
        }

        // Monster's Turn
        int monsterDmg = combatService.monsterAttacks();
        log(combatService.getCurrentMonster().getName()
                + " ti attacca per " + monsterDmg + " danni!");

        updateUI();

        if (!gameService.getHero().isAlive()) {
            handleDefeat();
        }
    }

    @FXML
    public void onRun() {
        boolean escaped = combatService.heroRuns();
        if (escaped) {
            log("Sei riuscito a fuggire!");
            endCombat();
        } else {
            log("Non sei riuscito a fuggire!");
            int monsterDmg = combatService.monsterAttacks();
            log(combatService.getCurrentMonster().getName()
                    + " ti attacca per " + monsterDmg + " danni!");
            updateUI();
            if (!gameService.getHero().isAlive()) handleDefeat();
        }
    }

    private void handleVictory() {
        Monster monster = combatService.getCurrentMonster();
        gameService.getHero().gainExperience(monster.getExperienceReward());
        gameService.getHero().gainGold(monster.getGoldReward());
        log("Hai sconfitto il " + monster.getName() + "!");
        log("+ " + monster.getExperienceReward() + " EXP  + "
                + monster.getGoldReward() + " Gold");
        updateUI();
        disableButtons();
        javafx.animation.PauseTransition pause =
                new javafx.animation.PauseTransition(
                        javafx.util.Duration.seconds(2));
        pause.setOnFinished(e -> endCombat());
        pause.play();
    }

    private void handleDefeat() {
        log("Sei stato sconfitto... Game Over!");
        disableButtons();

        // Wait 2 seconds then return to the main menu
        javafx.animation.PauseTransition pause =
                new javafx.animation.PauseTransition(
                        javafx.util.Duration.seconds(2));
        pause.setOnFinished(e -> SceneManager.getInstance().showMainMenu());
        pause.play();
    }

    private void endCombat() {
        SceneManager.getInstance().showGameView(gameService);
    }

    private void updateUI() {
        heroNameLabel.setText(gameService.getHero().getName());
        heroHpLabel.setText("HP: " + gameService.getHero().getStats().getCurrentHp()
                + "/" + gameService.getHero().getStats().getMaxHp());
        monsterNameLabel.setText(combatService.getCurrentMonster().getName());
        monsterHpLabel.setText("HP: " + combatService.getCurrentMonster()
                .getStats().getCurrentHp());
    }

    private void log(String message) {
        combatLog.appendText(message + "\n");
    }

    private void disableButtons() {
        attackButton.setDisable(true);
        runButton.setDisable(true);
    }
}
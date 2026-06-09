package it.unicam.cs.mpgc.rpg118716.model.combat;

import it.unicam.cs.mpgc.rpg118716.model.character.Combatant;

/**
 * Represents a single action performed during combat.
 * New actions can be added implementing this interface without
 * modifying existing code.
 */
public interface CombatAction {

    /**
     * Returns the type of this combat action.
     *
     * @return the CombatActionType
     */
    CombatActionType getType();

    /**
     * Executes this action, applying its effect from the source to the target.
     *
     * @param source the combatant performing the action
     * @param target the combatant receiving the action
     */
    void execute(Combatant source, Combatant target);

    /**
     * Returns a human-readable description of this action.
     *
     * @return description string
     */
    String getDescription();
}

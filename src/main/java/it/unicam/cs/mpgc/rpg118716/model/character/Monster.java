package it.unicam.cs.mpgc.rpg118716.model.character;

/**
 * Represents an enemy encountered during the game.
 * Implements Combatant: has stats and can participate in combat.
 */
public class Monster implements Combatant {

    private final String name;
    private final Stats stats;
    private final int experienceReward;
    private final int goldReward;

    public Monster(String name, Stats stats, int experienceReward, int goldReward) {
        this.name = name;
        this.stats = stats;
        this.experienceReward = experienceReward;
        this.goldReward = goldReward;
    }

    @Override
    public String getName() { return name; }

    @Override
    public Stats getStats() { return stats; }

    @Override
    public boolean isAlive() { return !stats.isDepleted(); }

    @Override
    public void takeDamage(int amount) { stats.reduceHp(amount); }

    public int getExperienceReward() { return experienceReward; }

    public int getGoldReward() { return goldReward; }
}

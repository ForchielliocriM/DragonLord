package it.unicam.cs.mpgc.rpg118716.model.character;

/**
 * Represents the player's character.
 * Implements Combatant and adds progression mechanics (level, XP, gold).
 */
public class Hero implements Combatant {

    private final String name;
    private Stats stats;
    private int level;
    private int experience;
    private int gold;

    public Hero(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
        this.level = 1;
        this.experience = 0;
        this.gold = 0;
    }

    @Override
    public String getName()  { return name; }

    @Override
    public Stats getStats()  { return stats; }

    @Override
    public boolean isAlive() { return !stats.isDepleted(); }

    @Override
    public void takeDamage(int amount) { stats.reduceHp(amount); }

    public void setLevel(int level) { this.level = level; }
    public void setExperience(int experience) { this.experience = experience; }
    public void setGold(int gold) { this.gold = gold; }

    public int getLevel()      { return level; }
    public int getExperience() { return experience; }
    public int getGold()       { return gold; }

    /**
     * Adds experience points and triggers level up if threshold is reached.
     *
     * @param amount XP gained (>= 0)
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public void gainExperience(int amount) {
        if (amount < 0) throw new IllegalArgumentException("XP cannot be negative");
        this.experience += amount;
        checkLevelUp();
    }

    /**
     * Adds the specified amount of gold to the player's current gold balance.
     *
     * @param amount the amount of gold to add; must be non-negative
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public void gainGold(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Gold cannot be negative");
        this.gold += amount;
    }

    /**
     * Levels up if enough XP has been accumulated.
     */
    private void checkLevelUp() {
        int threshold = level * 100;
        if (experience >= threshold) {
            level++;
            experience -= threshold;
            applyLevelUpBonus();
        }
    }

    /**
     * Increases stats on level up.
     * Open for extension: could be delegated to a LevelUpStrategy.
     */
    private void applyLevelUpBonus() {
        stats = new Stats(
                stats.getMaxHp() + 10,
                stats.getAttack() + 3,
                stats.getDefense() + 2,
                stats.getSpeed() + 1
        );
    }
}

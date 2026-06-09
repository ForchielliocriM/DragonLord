package it.unicam.cs.mpgc.rpg118716.model.character;

/**
 * Holds the numerical statistics of a combatant.
 */
public class Stats {

    private int maxHp;
    private int currentHp;
    private int attack;
    private int defense;
    private int speed;

    public Stats(int maxHp, int attack, int defense, int speed) {
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public int getMaxHp()      { return maxHp; }
    public int getCurrentHp()  { return currentHp; }
    public int getAttack()     { return attack; }
    public int getDefense()    { return defense; }
    public int getSpeed()      { return speed; }

    /**
     * Reduces current HP by the given amount.
     *
     * @param amount damage to apply (>= 0)
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public void reduceHp(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Damage cannot be negative");
        this.currentHp = Math.max(0, this.currentHp - amount);
    }

    /**
     * Restores current HP by the given amount.
     *
     * @param amount HP to restore (>= 0)
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public void restoreHp(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Heal amount cannot be negative");
        this.currentHp = Math.min(maxHp, this.currentHp + amount);
    }

    /**
     * Sets the current HP to the given value, clamped between 0 and maxHp.
     * Ensures that currentHp never exceeds maxHp or drops below zero.
     *
     * @param currentHp the HP value to set
     */
    public void setCurrentHp(int currentHp) {
        this.currentHp = Math.max(0, Math.min(currentHp, maxHp));
    }

    /**
     * Returns whether this entity has run out of HP.
     *
     * @return true if currentHp is zero or below, false otherwise
     */
    public boolean isDepleted() {
        return currentHp <= 0;
    }
}

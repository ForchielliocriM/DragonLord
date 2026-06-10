package it.unicam.cs.mpgc.rpg118716.service;

import it.unicam.cs.mpgc.rpg118716.model.character.Hero;
import it.unicam.cs.mpgc.rpg118716.model.character.Monster;

import java.util.Random;

/**
 * Implements turn-based combat logic.
 * Only resolves combat between Hero and Monster.
 */
public class CombatServiceImpl implements CombatService {

    private static final double RUN_SUCCESS_CHANCE = 0.5;

    private final Random random = new Random();

    private Hero hero;
    private Monster monster;
    private boolean heroFled;

    @Override
    public void startCombat(Hero hero, Monster monster) {
        this.hero = hero;
        this.monster = monster;
        this.heroFled = false;
    }

    @Override
    public int heroAttacks() {
        int damage = calculateDamage(
                hero.getStats().getAttack(),
                monster.getStats().getDefense()
        );
        monster.takeDamage(damage);
        return damage;
    }

    @Override
    public int monsterAttacks() {
        int damage = calculateDamage(
                monster.getStats().getAttack(),
                hero.getStats().getDefense()
        );
        hero.takeDamage(damage);
        return damage;
    }

    @Override
    public boolean heroRuns() {
        heroFled = random.nextDouble() < RUN_SUCCESS_CHANCE;
        return heroFled;
    }

    @Override
    public boolean isCombatOngoing() {
        return hero.isAlive() && monster.isAlive() && !heroFled;
    }

    @Override
    public boolean isHeroVictorious() {
        return !monster.isAlive();
    }

    @Override
    public Monster getCurrentMonster() {
        return monster;
    }

    /**
     * Calculates damage dealt based on attacker's attack and defender's defense.
     * Formula: max(1, attack - defense/2) with a small random variance.
     *
     * @param attack  the attacker's attack stat
     * @param defense the defender's defense stat
     * @return the final damage amount (always at least 1)
     */
    private int calculateDamage(int attack, int defense) {
        int base = Math.max(1, attack - defense / 2);
        int variance = random.nextInt(3) - 1; // -1, 0 or +1
        return Math.max(1, base + variance);
    }
}
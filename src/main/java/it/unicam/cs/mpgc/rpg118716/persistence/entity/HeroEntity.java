package it.unicam.cs.mpgc.rpg118716.persistence.entity;

import jakarta.persistence.*;

/**
 * JPA entity representing a saved Hero state in the database.
 * Separate from the domain Hero class.
 * This class is only responsible for persistence mapping.
 */
@Entity
@Table(name = "heroes")
public class HeroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int experience;

    @Column(nullable = false)
    private int gold;

    @Column(nullable = false)
    private int maxHp;

    @Column(nullable = false)
    private int currentHp;

    @Column(nullable = false)
    private int attack;

    @Column(nullable = false)
    private int defense;

    @Column(nullable = false)
    private int speed;

    @Column(nullable = false)
    private int positionX;

    @Column(nullable = false)
    private int positionY;

    // Empty constructor required by JPA
    public HeroEntity() {}

    public HeroEntity(String name, int level, int experience, int gold,
                      int maxHp, int currentHp, int attack, int defense,
                      int speed, int positionX, int positionY) {
        this.name = name;
        this.level = level;
        this.experience = experience;
        this.gold = gold;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    // Getters
    public Long getId()         { return id; }
    public String getName()     { return name; }
    public int getLevel()       { return level; }
    public int getExperience()  { return experience; }
    public int getGold()        { return gold; }
    public int getMaxHp()       { return maxHp; }
    public int getCurrentHp()   { return currentHp; }
    public int getAttack()      { return attack; }
    public int getDefense()     { return defense; }
    public int getSpeed()       { return speed; }
    public int getPositionX()   { return positionX; }
    public int getPositionY()   { return positionY; }

    // Setters
    public void setId(Long id)              { this.id = id; }
    public void setName(String name)        { this.name = name; }
    public void setLevel(int level)         { this.level = level; }
    public void setExperience(int exp)      { this.experience = exp; }
    public void setGold(int gold)           { this.gold = gold; }
    public void setMaxHp(int maxHp)         { this.maxHp = maxHp; }
    public void setCurrentHp(int currentHp) { this.currentHp = currentHp; }
    public void setAttack(int attack)       { this.attack = attack; }
    public void setDefense(int defense)     { this.defense = defense; }
    public void setSpeed(int speed)         { this.speed = speed; }
    public void setPositionX(int x)         { this.positionX = x; }
    public void setPositionY(int y)         { this.positionY = y; }
}

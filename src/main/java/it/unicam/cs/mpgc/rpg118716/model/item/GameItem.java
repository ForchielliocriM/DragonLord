package it.unicam.cs.mpgc.rpg118716.model.item;

/**
 * Concrete implementation of a game item.
 */
public class GameItem implements Item {

    private final String name;
    private final String description;
    private final ItemType type;
    private final int value;

    public GameItem(String name, String description, ItemType type, int value) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    @Override
    public String getName()        { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public ItemType getType()      { return type; }

    public int getValue()          { return value; }
}

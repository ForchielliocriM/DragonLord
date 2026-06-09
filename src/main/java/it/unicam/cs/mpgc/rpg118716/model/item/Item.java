package it.unicam.cs.mpgc.rpg118716.model.item;

/**
 * Represents a generic game item.
 */
public interface Item {
    String getName();
    String getDescription();
    ItemType getType();
}

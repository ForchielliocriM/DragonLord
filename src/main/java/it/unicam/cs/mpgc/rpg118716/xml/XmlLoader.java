package it.unicam.cs.mpgc.rpg118716.xml;

import java.util.List;

/**
 * Generic interface for loading game data from XML files.
 * New loaders can be added without modifying existing ones.
 *
 * @param <T> the type of object this loader produces
 */
public interface XmlLoader<T> {

    /**
     * Loads and returns a list of objects parsed from an XML file.
     *
     * @param resourcePath path to the XML file (relative to resources)
     * @return list of parsed objects
     * @throws XmlLoadException if the file cannot be read or parsed
     */
    List<T> load(String resourcePath) throws XmlLoadException;
}

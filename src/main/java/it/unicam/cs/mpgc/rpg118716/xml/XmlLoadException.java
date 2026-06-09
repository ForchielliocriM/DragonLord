package it.unicam.cs.mpgc.rpg118716.xml;

/**
 * Thrown when an XML file cannot be loaded or parsed correctly.
 */
public class XmlLoadException extends Exception {

    public XmlLoadException(String message) {
        super(message);
    }

    public XmlLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}

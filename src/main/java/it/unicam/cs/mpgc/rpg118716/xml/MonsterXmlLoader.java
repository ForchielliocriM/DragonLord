package it.unicam.cs.mpgc.rpg118716.xml;

import it.unicam.cs.mpgc.rpg118716.model.character.Monster;
import it.unicam.cs.mpgc.rpg118716.model.character.Stats;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads Monster objects from an XML file validated against monsters.dtd.
 * Uses XPath to extract data from the parsed document.
 */
public class MonsterXmlLoader implements XmlLoader<Monster> {

    @Override
    public List<Monster> load(String resourcePath) throws XmlLoadException {
        List<Monster> monsters = new ArrayList<>();

        try {
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream(resourcePath);

            if (inputStream == null)
                throw new XmlLoadException("Resource not found: " + resourcePath);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Resolve DTD from resources instead of filesystem
            builder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId, String systemId) {
                    InputStream dtdStream = getClass()
                            .getClassLoader()
                            .getResourceAsStream("data/monsters.dtd");
                    if (dtdStream == null) return null;
                    return new InputSource(dtdStream);
                }
            });

            builder.setErrorHandler(new org.xml.sax.helpers.DefaultHandler());

            Document document = builder.parse(inputStream);

            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList monsterNodes = (NodeList) xpath.evaluate(
                    "/monsters/monster",
                    document,
                    XPathConstants.NODESET
            );

            for (int i = 0; i < monsterNodes.getLength(); i++) {
                Element monsterEl = (Element) monsterNodes.item(i);
                monsters.add(parseMonster(monsterEl, xpath));
            }

        } catch (XmlLoadException e) {
            throw e;
        } catch (Exception e) {
            throw new XmlLoadException("Failed to load monsters from: " + resourcePath, e);
        }

        return monsters;
    }

    private Monster parseMonster(Element monsterEl, XPath xpath) throws Exception {
        String name  = xpath.evaluate("name", monsterEl);
        int maxHp    = parseInt(xpath.evaluate("stats/@maxHp",        monsterEl));
        int attack   = parseInt(xpath.evaluate("stats/@attack",       monsterEl));
        int defense  = parseInt(xpath.evaluate("stats/@defense",      monsterEl));
        int speed    = parseInt(xpath.evaluate("stats/@speed",        monsterEl));
        int xp       = parseInt(xpath.evaluate("rewards/@experience", monsterEl));
        int gold     = parseInt(xpath.evaluate("rewards/@gold",       monsterEl));

        Stats stats = new Stats(maxHp, attack, defense, speed);
        return new Monster(name, stats, xp, gold);
    }

    private int parseInt(String value) {
        return Integer.parseInt(value.trim());
    }
}

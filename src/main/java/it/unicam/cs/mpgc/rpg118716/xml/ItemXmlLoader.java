package it.unicam.cs.mpgc.rpg118716.xml;

import it.unicam.cs.mpgc.rpg118716.model.item.GameItem;
import it.unicam.cs.mpgc.rpg118716.model.item.ItemType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
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
 * Loads GameItem objects from an XML file validated against items.dtd.
 * Uses XPath to extract item data from the parsed document.
 */
public class ItemXmlLoader implements XmlLoader<GameItem> {

    @Override
    public List<GameItem> load(String resourcePath) throws XmlLoadException {
        List<GameItem> items = new ArrayList<>();

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
            builder.setEntityResolver((publicId, systemId) -> {
                InputStream dtdStream = getClass()
                        .getClassLoader()
                        .getResourceAsStream("data/items.dtd");
                if (dtdStream == null) return null;
                return new InputSource(dtdStream);
            });

            builder.setErrorHandler(new org.xml.sax.helpers.DefaultHandler());

            Document document = builder.parse(inputStream);

            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList itemNodes = (NodeList) xpath.evaluate(
                    "/items/item",
                    document,
                    XPathConstants.NODESET
            );

            for (int i = 0; i < itemNodes.getLength(); i++) {
                Element itemEl = (Element) itemNodes.item(i);
                items.add(parseItem(itemEl, xpath));
            }

        } catch (XmlLoadException e) {
            throw e;
        } catch (Exception e) {
            throw new XmlLoadException("Failed to load items from: " + resourcePath, e);
        }

        return items;
    }

    /**
     * Parses a single {@code <item>} element into a GameItem object.
     */
    private GameItem parseItem(Element itemEl, XPath xpath) throws Exception {
        String name        = xpath.evaluate("name", itemEl);
        String description = xpath.evaluate("description", itemEl);
        String typeStr     = itemEl.getAttribute("type");
        int value          = Integer.parseInt(itemEl.getAttribute("value").trim());

        ItemType type = ItemType.valueOf(typeStr);
        return new GameItem(name, description, type, value);
    }
}
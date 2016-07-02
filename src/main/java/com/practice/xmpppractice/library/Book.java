package com.practice.xmpppractice.library;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.util.XmlStringBuilder;

/**
 * Created by vaibhavr on 22/05/16.
 */
public class Book implements ExtensionElement{

    public static final String NAMESPACE = "urn:xmpp:ravenapp:book";

    public static final String ELEMENT_NAME = "book";

    private String title;

    private String author;

    private String type;

    private long created;

    public Book(String title, String author, String isbn, long created) {
        this.title = title;
        this.author = author;
        this.type = isbn;
        this.created = created;
    }

    @Override
    public String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public String getElementName() {
        return ELEMENT_NAME;
    }

    @Override
    public CharSequence toXML() {
        XmlStringBuilder xml = new XmlStringBuilder(this);
        xml.rightAngleBracket();
        if(title != null) {
            xml.optElement("title", title.toString());
        }
        xml.optElement("author", author);
        xml.optElement("type", type);
        xml.optElement("created", Long.valueOf(created).toString());
        xml.closeElement(this);
        return xml;
    }
}

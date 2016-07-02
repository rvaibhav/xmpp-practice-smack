package com.practice.xmpppractice.handshake;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.util.XmlStringBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vaibhavr on 19/12/15.
 */
public class Handshake implements ExtensionElement {

    public static final String NAMESPACE = "urn:xmpp:ravenapp:handshake";
    public static final String ELEMENT = "handshake";

    private HandshakeType type;

    private Date created;

    public HandshakeType getType() {
        return type;
    }

    public void setType(HandshakeType type) {
        this.type = type;
    }

    public static SimpleDateFormat formatter;

    static{
        formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss a");
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Handshake(){

    }

    public Handshake(HandshakeType type, Date created){
        this.type = type;
        this.created = created;
    }

    public static Builder build(){
        return new Builder();
    }

    public CharSequence toXML() {
        XmlStringBuilder xml = new XmlStringBuilder(this);
        /*xml.attribute("type",type);*/
        /*xml.attribute("xmlns", getNamespace());*/
        xml.rightAngleBracket();
        if(type != null) {
            xml.optElement("type", type);
        }
        if(created != null) {
            xml.optElement("created", formatter.format(created));
        }
        xml.closeElement(this);
        return xml;
    }

    @Override
    public String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public String getElementName() {
        return ELEMENT;
    }

    public static class Builder{

        private HandshakeType type;

        private Date created;

        public HandshakeType getType() {
            return type;
        }

        public void setType(HandshakeType type) {
            this.type = type;
        }

        public Date getCreated() {
            return created;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

        public Handshake build(){
            return new Handshake(type, created);
        }
    }
}

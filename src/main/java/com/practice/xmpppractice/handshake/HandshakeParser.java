package com.practice.xmpppractice.handshake;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;

import static com.practice.xmpppractice.handshake.Handshake.formatter;

/**
 * Created by vaibhavr on 19/12/15.
 */
public class HandshakeParser {

    public static Handshake parse(InputStream io) {

        Handshake.Builder builder = Handshake.build();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(io, "UTF-8");
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = xpp.getName();
                        switch(tagName){
                            case "type":
                                builder.setType(HandshakeType.valueOf(xpp.nextText()));
                                break;
                            /*case "node":
                                builder.setNode(xpp.nextText());
                                break;*/
                            case "created":
                                String createdTs = xpp.nextText();
                                builder.setCreated(formatter.parse(createdTs));
                            default:
                                break;
                        }
                    default:
                        break;
                }
                eventType = xpp.next();
            }
        }catch(XmlPullParserException xmlPPE){

        }catch(IOException ioEx){

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return builder.build();
    }
}

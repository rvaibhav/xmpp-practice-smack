package com.practice.xmpppractice.client;

import com.practice.xmpppractice.helper.smack.XmppHelper;
import org.jivesoftware.smack.SmackConfiguration;

/**
 * Created by vaibhavr on 22/06/16.
 */
public class DemoClient2 {

    private static final String USERNAME = "+919935377851";

    private static final String OTHER_USER = "+919876543211";

    public static void main(String[] args){
        XmppHelper obj = new XmppHelper(USERNAME, OTHER_USER);
        SmackConfiguration.DEBUG = true;
        String password = "12341234";
        obj.login(USERNAME, password);
        obj.sendResponseMessage();
        obj.publishRoute();
    }
}

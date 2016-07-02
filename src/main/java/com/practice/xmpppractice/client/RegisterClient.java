package com.practice.xmpppractice.client;

import com.practice.xmpppractice.helper.smack.XmppHelper;
import org.jivesoftware.smack.SmackConfiguration;

/**
 * Created by vaibhavr on 22/06/16.
 */
public class RegisterClient {

    private static final String USERNAME = "+919935377851";


    public static void main(String[] args){
        XmppHelper obj = new XmppHelper(USERNAME, null);
        SmackConfiguration.DEBUG = true;
        String password = "12341234";
        obj.registerAccount(USERNAME, password);
    }
}

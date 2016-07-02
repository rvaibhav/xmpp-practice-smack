package com.practice.xmpppractice.client;

import com.practice.xmpppractice.helper.smack.XmppHelper;

/**
 * Created by vaibhavr on 16/11/15.
 */
public class DemoClient {

    private static final String USERNAME = "+919935377851";

    private static final String OTHER_USER = "+919876543210";

    public static void main(String[] args){
        /*DemoClient obj = new DemoClient();*/
        XmppHelper obj = new XmppHelper(USERNAME, OTHER_USER);
        /*String username = "+919936284009";*/
        String password = "12341234";
        obj.registerAccount(USERNAME, password);
        obj.login(USERNAME, password);
        /*obj.sendSubscribedResponse(connection, Presence.Type.subscribed);
        obj.sendSubscribedResponse(connection, Presence.Type.subscribe);*/
        /*String multicastAddress = obj.getMultipleRecipienServiceAddress(connection);
        System.out.println("Multicast address is " + multicastAddress);*/
        /*obj.deleteNode("testNode", connection);*/
        /*LeafNode node = obj.createNode(connection);*/
        /*obj.sendRequestMessage();*/
        /*obj.sendTestMessage();*/
        /*obj.sendBookMessage();*/
        /*obj.sendResponseMessage();*/
        /*obj.publishLatLon(73.79258, 18.483290000000004);*/
        /*obj.publishLatLon(0.0,0.0,node);*/
        /*obj.publishRoute();*/
        obj.sendMultipleMessage();
        /*obj.sendDeclinedMessage();*/
        /*while(true){

        }*/
    }

}

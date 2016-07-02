package com.practice.xmpppractice.helper.smack;

import com.practice.xmpppractice.GeoLocation;
import com.practice.xmpppractice.handshake.Handshake;
import com.practice.xmpppractice.handshake.HandshakeType;
import com.practice.xmpppractice.library.Book;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.bosh.BOSHConfiguration;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smackx.address.packet.MultipleAddresses;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.pubsub.*;
import org.jivesoftware.smackx.xdata.packet.DataForm;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vaibhavr on 22/06/16.
 */
public class XmppHelper {

    private String xmppHost = "<Your-Host>";

    private int xmppPort = 5280;

    private String anotherUser = null;

    private String xmppDomain = "<Your-domain>";

    private String username = null;

    private AbstractXMPPConnection connection = null;

    public XmppHelper(String username, String anotherUser){
        this.connection = createConnectionObject();
        this.username = username;
        this.anotherUser = anotherUser;
    }

    private AbstractXMPPConnection createConnectionObject(){
        AbstractXMPPConnection conn = null;
        try {
            BOSHConfiguration.Builder builderConfig = BOSHConfiguration.builder();
            /*SmackConfiguration.DEBUG = true;*/
            builderConfig.setHost(xmppHost);
            builderConfig.setPort(xmppPort);
            builderConfig.setServiceName(xmppDomain);
            builderConfig.setUseHttps(Boolean.FALSE);
            builderConfig.setFile("/http-bind");
            builderConfig.setResource("javacode" + System.currentTimeMillis());
            builderConfig.setDebuggerEnabled(true);
            conn = new XMPPBOSHConnection(builderConfig.build());
            conn.setPacketReplyTimeout(100000);
            SmackConfiguration.DEBUG = true;
            conn.connect();
        }catch(SmackException se){
            System.out.println("Smack Exception thrown");
        }catch(IOException io){
            System.out.println("IO Exception thrown");
        }catch(XMPPException xmppEx){
            System.out.println("XMPP Exception thrown");
        }
        return conn;
    }

    public void registerAccount(String username, String password){
        AccountManager accountManager = AccountManager.getInstance(connection);
        accountManager.sensitiveOperationOverInsecureConnection(Boolean.TRUE);
        Map<String, String> map = new HashMap<String, String>();
        try {
            if (accountManager.supportsAccountCreation()) {
                /*Localpart localPart = Localpart.from(username);*/
                accountManager.createAccount(username, password, map);
            }
        }catch(XMPPException.XMPPErrorException xmppErrEx) {
            xmppErrEx.printStackTrace();
        }catch (SmackException.NotConnectedException nce) {

        }catch (SmackException.NoResponseException nre) {

        }
    }

    public void login(String username, String password){
        try{
            /*Roster roster = Roster.getInstanceFor(connection);
            roster.addRosterListener(new TestRosterListener());
            roster.setRosterLoadedAtLogin(true);*/
            if(!connection.isAuthenticated()) {
                connection.login(username, password);
            }
            Roster roster = loadRoster();
            /*roster.createEntry("+919876543240@localhost","Vaibhav3240",null);*/
        }catch(XMPPException.XMPPErrorException xmppErrEx) {
            xmppErrEx.printStackTrace();
        }catch (SmackException.NotConnectedException nce) {

        } catch (XMPPException xmppex) {

        } catch (SmackException.NoResponseException nre) {

        } catch(IOException ioEx){

        } catch(SmackException smEx){

        }
    }

    public Roster loadRoster() {
        Roster roster = Roster.getInstanceFor(connection);
        roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
        if (!roster.isLoaded()) {
            try {
                roster.reloadAndWait();
            }catch(SmackException.NotLoggedInException e){
                e.printStackTrace();
            }catch(SmackException.NotConnectedException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return roster;
    }

    public void sendSubscribedResponse(Presence.Type type){
        Presence presence = new Presence(type);
        presence.setTo(anotherUser);
        try {
            connection.sendStanza(presence);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public LeafNode createNode(){
        PubSubManager mgr = new PubSubManager(connection);
        LeafNode node = null;
        try {
            node = (LeafNode) mgr.createNode();
            ConfigureForm form = new ConfigureForm(DataForm.Type.submit);
            form.setAccessModel(AccessModel.open);
            form.setDeliverPayloads(false);
            form.setNotifyRetract(true);
            form.setPersistentItems(true);
            form.setPublishModel(PublishModel.open);
            form.setNodeType(NodeType.leaf);
            /*node.sendConfigurationForm(form);*/
            /*LeafNode node = (LeafNode) mgr.createNode("testNode", form);*/
            node.sendConfigurationForm(form);
            // Publish an Item, let service set the id
            /*node.send(new PayloadItem("test" + System.currentTimeMillis(),
                    new SimplePayload("book", "pubsub:test:book", "Two Towers")));*/
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        return node;
    }

    public void deleteNode(String nodeId){
        PubSubManager mgr = new PubSubManager(connection);
        try {
            mgr.deleteNode(nodeId);
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void sendRequestMessage(){
        Handshake.Builder builder = Handshake.build();
        builder.setType(HandshakeType.REQUEST_LOCALTION);
        Handshake handshake = builder.build();
        Message message = new Message();
        message.setType(Message.Type.normal);
        message.addExtension(handshake);
        message.setTo(anotherUser + "@" + xmppDomain);
        try {
            this.connection.sendStanza(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void sendTestMessage(){
        Message message = new Message();
        message.setTo(anotherUser);
        message.setBody("Howdy!");
        try {
            connection.sendStanza(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void sendResponseMessage(){
        Handshake.Builder builder = Handshake.build();
        builder.setType(HandshakeType.LOCATION_SHARED);
        builder.setCreated(Calendar.getInstance().getTime());
        Handshake handshake = builder.build();
        Message message = new Message();
        /*message.setType(Message.Type.normal);*/
        message.setTo(anotherUser + "@" + xmppDomain);
        message.addExtension(handshake);
        try {
            this.connection.sendStanza(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        /*ChatManager chatManager = ChatManager.getInstanceFor(connection);
        chatManager.createChat(anotherUser);*/
    }

    public void sendDeclinedMessage(){
        Handshake.Builder builder = Handshake.build();
        builder.setType(HandshakeType.DECLINE_REQUEST);
        builder.setCreated(Calendar.getInstance().getTime());
        Handshake handshake = builder.build();
        Message message = new Message();
        /*message.setType(Message.Type.normal);*/
        message.setTo(anotherUser + "@" + xmppDomain);
        message.addExtension(handshake);
        try {
            this.connection.sendStanza(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        /*ChatManager chatManager = ChatManager.getInstanceFor(connection);
        chatManager.createChat(anotherUser);*/
    }

    public void publishLatLon(double lat, double lon){
        GeoLocation.Builder builder = GeoLocation.builder();
        builder.setLat(lat);
        builder.setLon(lon);
        GeoLocation geolocation = builder.build();
        try {
            Message message = new Message();
            /*message.setType(Message.Type.normal);*/
            message.addExtension(geolocation);
            message.setTo(anotherUser + "@" + xmppDomain);
            this.connection.sendStanza(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void sendBookMessage(){
        Book book = new Book("The Tempest", "Shakespeare", "ISBN-575765758", Calendar.getInstance().getTimeInMillis());
        Message message = new Message();
        message.setTo(anotherUser);
        message.addExtension(book);
        try {
            connection.sendStanza(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public  String getMultipleRecipienServiceAddress(XMPPConnection connection){
        ServiceDiscoveryManager sdm = ServiceDiscoveryManager.getInstanceFor(connection);
        java.util.List<String> services = null;
        try {
            services = sdm.findServices(MultipleAddresses.NAMESPACE, true, true);
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        if (services.size() > 0) {
            return services.get(0);
        }
        return null;
    }

    public void publishRoute(){
        /*this.publishLatLon(18.483290000000004, 73.79258);
        this.publishLatLon(18.48321, 73.79294);
        this.publishLatLon(18.48321, 73.79257);
        this.publishLatLon(18.48321, 73.793);
        this.publishLatLon(18.48324, 73.79307);
        this.publishLatLon(18.48391, 73.79402);
        this.publishLatLon(18.48401, 73.7941);
        this.publishLatLon(18.48453, 73.79477);
        this.publishLatLon(18.48467, 73.79493);
        this.publishLatLon(18.48514, 73.79554);*/
        this.publishLatLon(18.55438, 73.82569);
        this.publishLatLon(18.55451, 73.82529);
        this.publishLatLon(18.55498, 73.8257);
        this.publishLatLon(18.55511, 73.82579);

        this.publishLatLon(18.55523, 73.82588);
        this.publishLatLon(18.55533, 73.82594);
        this.publishLatLon(18.55543, 73.82599);
        this.publishLatLon(18.55551, 73.82601);
        this.publishLatLon(18.55559, 73.82603);
        this.publishLatLon(18.5557, 73.82605);
    }

    public void sendMultipleMessage(){
        try {
            for(int i=0;i<100;i++) {
                Message message = new Message();
                message.setBody("Howdy! " + System.currentTimeMillis());
                message.setTo(anotherUser + "@" + xmppDomain);
                this.connection.sendStanza(message);
            }
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }
}

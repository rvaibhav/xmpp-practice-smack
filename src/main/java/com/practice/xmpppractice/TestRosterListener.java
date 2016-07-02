package com.practice.xmpppractice;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterListener;
import org.jxmpp.jid.Jid;

import java.util.Collection;

/**
 * Created by vaibhavr on 25/11/15.
 */
public class TestRosterListener implements RosterListener {
    public void entriesAdded(Collection<String> collection) {
        System.out.println("Entries added");
    }

    public void entriesUpdated(Collection<String> collection) {
        System.out.println("Entries updated");
    }

    public void entriesDeleted(Collection<String> collection) {
        System.out.println("Entries deleted");
    }

    public void presenceChanged(Presence presence) {
        System.out.println("Presence changed: " + presence.getFrom() + " " + presence);
    }
}

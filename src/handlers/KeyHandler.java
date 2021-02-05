package handlers;

import java.util.HashSet;

public class KeyHandler {

    private static HashSet<String> currentlyActiveKeys = new HashSet<String>(4);

    public KeyHandler() {
        //Don't know what to put here yet
    }

    public static HashSet<String> getCurrentlyActiveKeys() {
        return currentlyActiveKeys;
    }

}

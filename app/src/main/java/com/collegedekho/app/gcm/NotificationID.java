package com.collegedekho.app.gcm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Bashiruddin on 27/10/2015.
 */
public class NotificationID {
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }
}
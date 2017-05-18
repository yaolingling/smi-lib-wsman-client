/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Trigger.
 *
 * @author Dan_Phelps
 *  
 *  Usage would be as follows: 
 * 
 *  Trigger t = new Trigger(INTERVAL, DURATION); 
 *  
 *  try{ 
 *      while( t.block() ) { 
 *         work to be done on interval 
 *      } 
 *   } finally { 
 *      t.cancel() 
 *   } 
 */
public class Trigger implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Trigger.class);

    private long interval;
    private long duration;
    private long startTime = 0;
    private long endTime;
    private long actualDuration = 0;
    private long maxWaitTime;
    private boolean expired = false;
    private boolean cancelled = false;

    private Thread t = null;


    /**
     * Creates a trigger object that can provide timer like notifications based on interval and duration.
     *
     * @param interval - how often to notify
     * @param duration - how long until trigger should expire
     */
    public Trigger(long interval, long duration) {
        this.interval = interval;
        this.duration = duration;
        this.maxWaitTime = interval * 2;
    }


    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        this.startTime = System.currentTimeMillis();
        this.endTime = startTime + duration;
        long nextNotifyTime = startTime + interval;

        long sleepTime;
        long currentTime;

        logger.debug("Starting Trigger notification thread.");

        synchronized (this) {
            while (true) {
                try {
                    currentTime = System.currentTimeMillis();

                    if (this.cancelled) {
                        logger.debug("Trigger notification thread has been cancelled!");
                        actualDuration = currentTime - startTime;
                        this.notify();
                        break;
                    }
                    if (currentTime >= endTime) {
                        actualDuration = currentTime - startTime;
                        this.expired = true;
                        logger.debug("Trigger notification thread has expired!");
                        this.notify();
                        break;
                    }
                    sleepTime = nextNotifyTime - currentTime;
                    nextNotifyTime += interval;
                    if (nextNotifyTime > endTime) {
                        nextNotifyTime = endTime; // last notification
                    }
                    if (sleepTime > 0) {
                        this.wait(sleepTime);
                    }
                    this.notify();

                } catch (InterruptedException ie) {
                }
            }
        }
    }


    /**
     * Method will cause calling thread to block for 'interval' millis. <br>
     * "interval" is set at construction time. <br>
     * Always call cancel() when done with the trigger object.
     * 
     * @return - true until expired or cancelled, then false
     */
    public boolean block() {
        try {
            synchronized (this) {
                if (expired) {
                    logger.debug("Trigger has expired.");
                    return false;
                }
                if (cancelled) {
                    logger.debug("Trigger has been cancelled.");
                    return false;
                }
                if (t == null) {
                    if (interval < 1000) {
                        logger.debug("Spawning notification thread.  Trigger every " + interval + " mS for duration of " + duration + " mS.");
                    } else {
                        logger.debug("Spawning notification thread.  Trigger every " + interval / 1000 + " seconds for duration of " + duration / 1000 + " seconds.");
                    }
                    t = new Thread(this);
                    t.start();
                }
                this.wait(maxWaitTime);
            }
        } catch (InterruptedException e) {
        }
        return true;
    }


    /**
     * Checks if is expired.
     *
     * @return true, if is expired
     */
    public boolean isExpired() {
        return this.expired;
    }


    /**
     * Returns the amount of time from trigger first use to current time or trigger termination.
     * 
     * @return trigger run time in millis
     */
    public long getDurationInMillis() {
        if (actualDuration > 0) { // only possible if cancelled or expired
            return (actualDuration);
        }
        if (startTime > 0) { // only possible if notification thread has been started
            return (System.currentTimeMillis() - startTime);
        }
        return 0;
    }


    /**
     * Terminates the notifying thread.<br>
     * Should always be called at end of trigger use.
     */
    public synchronized void cancel() {
        this.cancelled = true;
        this.notify(); // notify the notification thread of its imminent demise
    }


    /**
     * Resets the trigger to initial values.
     */
    public synchronized void reset() {
        logger.debug("Resetting trigger");
        this.cancelled = true;
        while (t.isAlive()) {
            try {
                this.notify();
                this.wait(500);
            } catch (InterruptedException e) {
            }
        }
        t = null;
        this.cancelled = false;
        this.expired = false;
    }


    /**
     * Reset trigger with new values.
     *
     * @param interval the interval
     * @param duration the duration
     */
    public void reset(long interval, long duration) {
        this.reset();
        logger.debug("New trigger values (in seconds): Interval- " + interval / 1000 + "  Duration- " + duration / 1000);
        this.interval = interval;
        this.duration = duration;
        this.maxWaitTime = interval * 2;
        this.startTime = 0;
        this.actualDuration = 0;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    protected void finalize() {
        this.cancelled = true;
    }

}

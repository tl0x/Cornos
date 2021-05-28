/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Notification
# Created by constantin at 12:53, Apr 05 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.etc.render;

import me.zeroX150.cornos.Cornos;

public class Notification {
    public final String title;
    public final String[] description;
    public final long creationTime;
    public long duration;
    public double animationProgress = 0;
    public double animationProgress2 = 0;
    public boolean isAnimationComplete = false;
    public boolean markedForDeletion = false;

    public Notification(String title, String[] description, long duration) {
        this.title = title;
        this.description = description;
        this.creationTime = System.currentTimeMillis();
        this.duration = duration;
    }

    public static Notification create(String t, String[] d, long d1) {
        Notification r = new Notification(t, d, d1);
        Cornos.notifMan.add(r);
        return r;
    }
}

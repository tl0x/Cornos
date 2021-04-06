/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: NotificationManager
# Created by constantin at 12:58, Apr 05 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.etc;

import com.google.common.collect.Lists;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.module.ext.Hud;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private List<Notification> notifs = new ArrayList<>();
    long lastCurr = System.currentTimeMillis();

    public void add(Notification notif) {
        notifs.add(notif);
    }

    public void tick() {
        List<Notification> newN = new ArrayList<>();
        for (Notification notif : notifs) {
            if (!notif.markedForDeletion) newN.add(notif);
        }
        notifs = newN;
    }

    public void render(MatrixStack matrices) {
        // DO NOT CHANGE A SINGLE THING THIS TOOK SO LONG TO MAKE HOLY SHIT
        long curr = System.currentTimeMillis();
        assert Cornos.minecraft.player != null;
        int offset = Cornos.minecraft.player.getStatusEffects().size() * 10 + 2;
        int width = Cornos.minecraft.getWindow().getScaledWidth();
        int height = Cornos.minecraft.getWindow().getScaledHeight();
        for (Notification notification : Lists.reverse(notifs)) {
            if (notification.animationProgress2 < 6.0 && !notification.isAnimationComplete) {
                notification.animationProgress2 += (((double)curr)-((double)lastCurr))/100.0;
                notification.animationProgress2 = MathHelper.clamp(notification.animationProgress2,0,6.1);
                notification.animationProgress = ((Math.log(notification.animationProgress2) + 2) / 2.778);
            } else notification.isAnimationComplete = true;
            double xOff = 150.0 * notification.animationProgress;
            if (notification.duration + notification.creationTime < curr && notification.isAnimationComplete) {
                if (notification.animationProgress2 > 0.01) {
                    notification.animationProgress2 -= (((double)curr)-((double)lastCurr))/100.0;
                    notification.animationProgress2 = MathHelper.clamp(notification.animationProgress2,0,6.1);
                    notification.animationProgress = ((Math.log(Math.max(notification.animationProgress2, 0.01)) + 2) / 2.778);
                } else notification.markedForDeletion = true;

            }
            int dheight = (notification.description.length) * 9;
            dheight = dheight == 0 ? 10 : dheight + 12;
            Color rc = Hud.themeColor.getColor();
            DrawableHelper.fill(matrices, (int) (width - xOff - 1), height - offset - dheight - 1, width, height - offset + 1, rc.getRGB());
            DrawableHelper.fill(matrices, (int) (width - xOff), height - offset - dheight, width - 3, height - offset, new Color(30, 30, 30).getRGB());
            Cornos.minecraft.textRenderer.draw(matrices, notification.title, (float) ((width - xOff + 1)), (float) ((height - offset - dheight + 1)), 0xFFFFFFFF);
            int off1 = 12;
            for (String s : notification.description) {
                Cornos.minecraft.textRenderer.draw(matrices, s, (float) ((width - xOff + 1)), (float) ((height - offset - dheight + off1)), 0xFFFFFFFF);
                off1 += 9;
            }
            offset += dheight + 3;
        }
        lastCurr = curr;
    }
}

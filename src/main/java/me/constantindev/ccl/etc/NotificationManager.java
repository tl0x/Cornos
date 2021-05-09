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
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    long lastCurr = System.currentTimeMillis();
    private List<Notification> notifs = new ArrayList<>();

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
        double val = 0.0;
        if (curr - lastCurr > 3) {
            val = ((double) (curr - lastCurr)) / 400;
            lastCurr = curr;
        }
        for (Notification notification : Lists.reverse(notifs)) {
            if (notification.animationProgress2 < 1.0 && !notification.isAnimationComplete) {
                notification.animationProgress2 += val;
                notification.animationProgress2 = MathHelper.clamp(notification.animationProgress2, 0, 1.0);
                notification.animationProgress = easeOutCubic(notification.animationProgress2);
            } else notification.isAnimationComplete = true;
            if (notification.duration + notification.creationTime < curr && notification.isAnimationComplete) {
                if (notification.animationProgress2 > 0.01) {
                    notification.animationProgress2 -= val;
                    notification.animationProgress2 = MathHelper.clamp(notification.animationProgress2, 0, 1.0);
                    notification.animationProgress = easeOutCubic(notification.animationProgress2);
                } else notification.markedForDeletion = true;
            }
            double xOff = 150.0 * notification.animationProgress;
            int dheight = (notification.description.length) * 9;
            dheight = dheight == 0 ? 10 : dheight + 12;
            Color rc = Hud.themeColor.getColor();
            DrawableHelper.fill(matrices, (int) (width - xOff - 1), height - offset - dheight - 1, width, height - offset + 1, rc.getRGB());
            DrawableHelper.fill(matrices, (int) (width - xOff), height - offset - dheight, width - 3, height - offset, new Color(30, 30, 30).getRGB());
            String t = Cornos.minecraft.textRenderer.trimToWidth(notification.title, (int) Math.floor(xOff));
            Cornos.minecraft.textRenderer.draw(matrices, t, (float) ((width - xOff + 1)), (float) ((height - offset - dheight + 1)), 0xFFFFFFFF);
            int off1 = 12;
            for (String s : notification.description) {
                StringVisitable td = Cornos.minecraft.textRenderer.trimToWidth(Text.of(s), (int) Math.floor(xOff));
                Cornos.minecraft.textRenderer.draw(matrices, td.getString(), (float) ((width - xOff + 1)), (float) ((height - offset - dheight + off1)), 0xFFFFFFFF);
                off1 += 9;
            }
            offset += dheight + 3;
        }
    }

    double easeOutCubic(double x) {
        return 1 - Math.pow(1 - x, 3);
    }
}

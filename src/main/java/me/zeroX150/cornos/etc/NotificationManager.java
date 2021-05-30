/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: NotificationManager
# Created by constantin at 12:58, Apr 05 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.etc;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.Colors;
import me.zeroX150.cornos.etc.helper.Renderer;
import me.zeroX150.cornos.etc.render.Notification;
import me.zeroX150.cornos.features.module.impl.external.Hud;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class NotificationManager {
	long lastCurr = System.currentTimeMillis();
	private List<Notification> notifs = new ArrayList<>();

	public void add(Notification notif) {
		notifs.add(notif);
	}

	public void tick() {
		List<Notification> newN = new ArrayList<>();
		for (Notification notif : notifs) {
			if (!notif.markedForDeletion)
				newN.add(notif);
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
			double remaining = (System.currentTimeMillis() - notification.creationTime)
					/ (double) notification.duration;
			remaining = MathHelper.clamp(remaining, 0, 1);
			if (notification.animationProgress2 < 1.0 && !notification.isAnimationComplete) {
				notification.animationProgress2 += val;
				notification.animationProgress2 = MathHelper.clamp(notification.animationProgress2, 0, 1.0);
				notification.animationProgress = easeOutCubic(notification.animationProgress2);
			} else
				notification.isAnimationComplete = true;
			if (notification.duration + notification.creationTime < curr && notification.isAnimationComplete) {
				if (notification.animationProgress2 > 0.01) {
					notification.animationProgress2 -= val;
					notification.animationProgress2 = MathHelper.clamp(notification.animationProgress2, 0, 1.0);
					notification.animationProgress = easeOutCubic(notification.animationProgress2);
				} else
					notification.markedForDeletion = true;
			}
			double xOff = 150.0 * notification.animationProgress;
			int dheight = (notification.description.length) * 9;
			dheight = dheight == 0 ? 10 : dheight + 12;
			double x1Progress = MathHelper.clamp(remaining * 4, 0, 1);
			double y1Progress = MathHelper.clamp(remaining * 4 - 1, 0, 1);
			double x2Progress = -MathHelper.clamp(remaining * 4 - 2, 0, 1);
			double y2Progress = -MathHelper.clamp(remaining * 4 - 3, 0, 1);
			Color rc = Hud.themeColor.getColor();
			String t = Cornos.minecraft.textRenderer.trimToWidth(notification.title, (int) Math.floor(xOff));
			DrawableHelper.fill(matrices, (int) (width - xOff - 3), height - offset - dheight, width - 3,
					height - offset, Colors.NOTIFICATION.get().getRGB());
			Renderer.renderLineScreen(width - xOff - 3, height - offset - dheight,
					MathHelper.clamp(width + (x1Progress * 150) - xOff - 3, 0, width - 3), height - offset - dheight,
					rc, 2);
			Renderer.renderLineScreen(width - 3, height - offset - dheight, width - 3,
					height - offset - dheight + (y1Progress * dheight), rc, 2);
			Renderer.renderLineScreen(width - 3, height - offset,
					MathHelper.clamp(width + (x2Progress * 150) - 3, width - xOff - 3, width - 3), height - offset, rc,
					2);
			Renderer.renderLineScreen(width - xOff - 3, height - offset, width - xOff - 3,
					height - offset + (y2Progress * dheight), rc, 2);
			Cornos.minecraft.textRenderer.draw(matrices, t, (float) (width - xOff),
					(float) ((height - offset - dheight + 2)), Colors.TEXT.get().getRGB());
			int off1 = 12;
			for (String s : notification.description) {
				StringVisitable td = Cornos.minecraft.textRenderer.trimToWidth(Text.of(s), (int) Math.floor(xOff));
				Cornos.minecraft.textRenderer.draw(matrices, td.getString(), (float) (width - xOff),
						(float) (height - offset - dheight + off1), Colors.TEXT.get().getRGB());
				off1 += 9;
			}
			offset += dheight + 3;
		}
	}

	double easeOutCubic(double x) {
		return 1 - Math.pow(1 - x, 3);
	}
}

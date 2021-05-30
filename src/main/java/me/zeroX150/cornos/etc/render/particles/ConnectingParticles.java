package me.zeroX150.cornos.etc.render.particles;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.Renderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.Window;

import java.awt.*;

public class ConnectingParticles extends Particles {
    double maxDist;

    public ConnectingParticles(int amount) {
        super(amount);
    }

    @Override
    public void tick() {
        if (!Cornos.config.particles.isEnabled())
            return;
        Window w = MinecraftClient.getInstance().getWindow();
        maxDist = Math.sqrt(w.getScaledWidth() * w.getScaledWidth() + w.getScaledHeight() * w.getScaledHeight()) / 9;
        Mouse m = MinecraftClient.getInstance().mouse;
        for (Particle particle : particles) {
            double sin = Math.sin(particle.rotation);
            double cos = Math.cos(particle.rotation);
            particle.posX += sin * particle.speed;
            particle.posY += cos * particle.speed;

            double mDist = Math
                    .sqrt(Math.pow(particle.posX - m.getX() / 2, 2) + Math.pow(particle.posY - m.getY() / 2, 2));
            if (mDist < maxDist / 2) {
                double ang = -Math.atan2(m.getX() / 2 - particle.posX, particle.posY - m.getY() / 2);
                ang = ang * (180 / Math.PI);
                particle.rotation = Math.toRadians(ang);
            }

            boolean bounced = false;
            boolean useState2 = false;
            if (particle.posX < 0) {
                particle.posX = 0;
                bounced = true;
                useState2 = true;
            }
            if (particle.posX > w.getScaledWidth()) {
                particle.posX = w.getScaledWidth();
                bounced = true;
                useState2 = true;
            }
            if (particle.posY < 0) {
                particle.posY = 0;
                bounced = true;
            }
            if (particle.posY > w.getScaledHeight()) {
                particle.posY = w.getScaledHeight();
                bounced = true;
            }
            if (bounced) {
                double rot = (useState2 ? 360 : 180) - Math.toDegrees(particle.rotation);
                particle.rotation = Math.toRadians(rot);
            }
        }
    }

    @Override
    public void render() {
        if (!Cornos.config.particles.isEnabled())
            return;
        for (Particle particle : particles) {
            for (Particle particle1 : particles) {
                double dist = Math.sqrt(
                        Math.pow(particle.posX - particle1.posX, 2) + Math.pow(particle.posY - particle1.posY, 2));
                if (dist < maxDist) {
                    int red = (int) (dist / maxDist * 255);
                    int green = Math.abs(red - 255);
                    Color c1 = Color.getHSBColor((float) (((double) (System.currentTimeMillis() % 10000)) / 10000),
                            (float) green / 255, 1);
                    Color c = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), green);
                    Renderer.renderLineScreen(particle.posX, particle.posY, particle1.posX, particle1.posY, c, 1);
                }
            }
            Mouse m = MinecraftClient.getInstance().mouse;
            double mDist = Math
                    .sqrt(Math.pow(particle.posX - m.getX() / 2, 2) + Math.pow(particle.posY - m.getY() / 2, 2));
            if (mDist < maxDist * 2) {
                int red = (int) (mDist / (maxDist * 2) * 255);
                int green = Math.abs(red - 255);
                Color c1 = Color.getHSBColor((float) (((double) (System.currentTimeMillis() % 10000)) / 10000),
                        (float) green / 255, 1);
                Color c = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), green);
                Renderer.renderLineScreen(particle.posX, particle.posY, m.getX() / 2, m.getY() / 2, c, 1);
            }
        }
    }

}

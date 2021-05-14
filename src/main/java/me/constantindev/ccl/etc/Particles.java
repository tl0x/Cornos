package me.constantindev.ccl.etc;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.Renderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// this is surprisingly simple rbuh
public class Particles {
    List<Particle> particles = new ArrayList<>();
    double maxDist;

    public Particles(int amount) {
        Window w = Cornos.minecraft.getWindow();
        for (int i = 0; i < amount; i++) {
            Particle c = new Particle();
            c.posX = (double) w.getScaledWidth() / 2;
            c.posY = (double) w.getScaledHeight() / 2;
            c.rotation = (Math.random() - 0.5) * Math.PI * 2;
            particles.add(c);
        }
    }

    public void tick() {
        Window w = Cornos.minecraft.getWindow();
        maxDist = Math.sqrt(w.getScaledWidth() * w.getScaledWidth() + w.getScaledHeight() * w.getScaledHeight()) / 9;
        for (Particle particle : particles) {
            double sin = Math.sin(particle.rotation);
            double cos = Math.cos(particle.rotation);
            particle.posX += sin * particle.speed;
            particle.posY += cos * particle.speed;
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
                double rot = (useState2?360:180)-Math.toDegrees(particle.rotation);
                particle.rotation = Math.toRadians(rot);
            }
        }
    }

    public void render() {
        for (Particle particle : particles) {
            double sin = Math.sin(particle.rotation);
            double cos = Math.cos(particle.rotation);
            Renderer.renderLineScreen(new Vec3d(particle.posX, particle.posY, 0), new Vec3d(particle.posX + sin, particle.posY + cos, 0), Color.WHITE, 2);
            // /*debug*/ Cornos.minecraft.textRenderer.draw(new MatrixStack(),""+particle.rotation,(int)particle.posX,(int)particle.posY,0xFFFFFF);
            for (Particle particle1 : particles) {
                double dist = Math.sqrt(Math.pow(particle.posX - particle1.posX, 2) + Math.pow(particle.posY - particle1.posY, 2));
                if (dist < maxDist) {
                    int red = (int) (dist / maxDist * 255);
                    int green = Math.abs(red - 255);
                    Color c1 = Color.getHSBColor((float) (((double) (System.currentTimeMillis() % 10000)) / 10000), (float) green / 255, 1);
                    Color c = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), green);
                    Renderer.renderLineScreen(new Vec3d(particle.posX, particle.posY, 0), new Vec3d(particle1.posX, particle1.posY, 0), c, 1);
                }
            }
        }
    }
}

class Particle {
    public double posX;
    public double posY;
    public double rotation;
    public double speed;

    public Particle() {
        posX = 10;
        posY = 20;
        rotation = 0;
        speed = Math.random() * 4 + 1;
    }
}

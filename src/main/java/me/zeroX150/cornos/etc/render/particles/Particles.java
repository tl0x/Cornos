package me.zeroX150.cornos.etc.render.particles;

import me.zeroX150.cornos.Cornos;
import net.minecraft.client.util.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Particles {
    List<Particle> particles = new ArrayList<>();

    public Particles(int amount) {
        Window w = Cornos.minecraft.getWindow();
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            Particle c = new Particle();
            c.posX = r.nextInt(w.getScaledWidth());
            c.posY = r.nextInt(w.getScaledHeight());
            c.rotation = (Math.random() - 0.5) * Math.PI * 2;
            particles.add(c);
        }
    }

    public void tick() {
        if (!Cornos.config.particles.isEnabled())
            return;
        Window w = Cornos.minecraft.getWindow();
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
                double rot = (useState2 ? 360 : 180) - Math.toDegrees(particle.rotation);
                particle.rotation = Math.toRadians(rot);
            }
        }
    }

    public void render() {
    }
}

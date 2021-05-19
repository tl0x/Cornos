package me.constantindev.ccl.etc.render.particles;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Particle {
    public double posX;
    public double posY;
    public double rotation;
    public double speed;
    public Color color;
    public List<Double> cache = new ArrayList<>();
    public boolean dead = false;

    public Particle() {
        posX = 0;
        posY = 0;
        rotation = 0;
        speed = Math.random() * 2 + 3;
        color = Color.WHITE;
    }
}

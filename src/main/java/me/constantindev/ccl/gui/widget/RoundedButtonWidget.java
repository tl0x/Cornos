package me.constantindev.ccl.gui.widget;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.Renderer;
import net.minecraft.client.gui.widget.AbstractPressableButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class RoundedButtonWidget extends AbstractPressableButtonWidget {
    public Color unselectedColor = new Color(30, 30, 30, 100);
    public Color selectedColor = new Color(12, 12, 12, 100);
    protected int r;
    long lastCache = 0;
    double timer = 0;
    Runnable onPressed;

    public RoundedButtonWidget(int x, int y, int width, int height, Text message, Runnable onPress) {
        super(x, y, width, height, message);
        this.r = 5;
        this.onPressed = onPress;
    }

    public RoundedButtonWidget(int x, int y, int width, int height, int roundness, Text message, Runnable onPress) {
        super(x, y, width, height, message);
        this.onPressed = onPress;
        this.r = roundness;
    }

    @Override
    public void onPress() {
        onPressed.run();
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.isHovered()) {
            long current = System.currentTimeMillis();
            if(current-lastCache>3) {
                lastCache = System.currentTimeMillis();
                timer+=0.02;
            }
        } else {
            long current = System.currentTimeMillis();
            if(current-lastCache>3) {
                lastCache = System.currentTimeMillis();
                timer-=0.02;
            }
        }
        timer = MathHelper.clamp(timer,0,1);
        double a = easeInOutQuart(timer);
        if ((a*width) > r) Renderer.renderRoundedQuad(x,y,x+(a*width),y+height,r-1,selectedColor);
        Renderer.renderRoundedQuad(x, y, x + width, y + height, r, unselectedColor);
        drawCenteredText(matrices, Cornos.minecraft.textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, Color.white.getRGB());
    }

    double easeInOutQuart(double x) {
        return x < 0.5 ? 4 * x * x * x : 1 - Math.pow(-2 * x + 2, 3) / 2;
    }
}

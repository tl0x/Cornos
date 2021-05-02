package me.constantindev.ccl.gui;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.gui.widget.RoundedButtonWidget;
import me.constantindev.ccl.module.ClientProgression;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainScreen extends Screen {
    Identifier bg = new Identifier("ccl", "bg.jpg");
    DateFormat line1 = new SimpleDateFormat("h:mm aa");
    DateFormat line2 = new SimpleDateFormat("E, d. M");
    boolean showSecrets = false;

    public MainScreen() {
        super(Text.of("bruh"));
    }

    @Override
    protected void init() {
        if (!ClientProgression.hasFinishedTut.isEnabled()) {
            assert this.client != null;
            this.client.openScreen(new TutorialScreen());
            return;
        }
        this.addButton(new RoundedButtonWidget(width - 125, height - 175, 120, 20, Text.of("Alts"), () -> {
            assert this.client != null;
            this.client.openScreen(new AltManagerScreen());
        }));
        this.addButton(new RoundedButtonWidget(width - 125, height - 150, 120, 20, Text.of("Click GUI"), () -> ModuleRegistry.getByName("clickgui").onEnable()));
        this.addButton(new RoundedButtonWidget(width - 125, height - 125, 120, 20, Text.of("Settings"), () -> Cornos.minecraft.openScreen(new OptionsScreen(this, Cornos.minecraft.options))));
        this.addButton(new RoundedButtonWidget(width - 125, height - 100, 120, 20, Text.of("Singleplayer"), () -> Cornos.minecraft.openScreen(new SelectWorldScreen(this))));
        this.addButton(new RoundedButtonWidget(width - 125, height - 75, 120, 20, Text.of("Multiplayer"), () -> Cornos.minecraft.openScreen(new MultiplayerScreen(this))));
        RoundedButtonWidget btnw = new RoundedButtonWidget(width - 125, height - 50, 120, 20, Text.of("Realms"), () -> Cornos.minecraft.openScreen(new RealmsMainScreen(this)));
        this.addButton(new RoundedButtonWidget(width - 125, height - 25, 120, 20, Text.of("Vanilla homescreen"), () -> {
            Cornos.config.mconf.getByName("homescreen").setValue("vanilla");
            Cornos.minecraft.openScreen(new TitleScreen());
        }));
        this.addButton(btnw);
        this.addButton(new TexturedButtonWidget(width - 20, 0, 20, 20, 0, 0, 0, new Identifier("ccl", "transparent.png"), (b) -> {
            btnw.setMessage(Text.of("Roleplay"));
            showSecrets = true;
        }));
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Color c = Color.getHSBColor((float) (((double) (System.currentTimeMillis() % 10000)) / 10000), 1, 1);
        Cornos.minecraft.getTextureManager().bindTexture(bg);
        DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, 0, width, height, height, width);
        RenderHelper.drawImage(matrices, new Identifier("ccl", "hscreenlogo.png"), -11, 1, 960 / 4, 233 / 4, (float) c.getRed() / 255, (float) c.getGreen() / 255, (float) c.getBlue() / 255);
        int diff = 32;
        RenderHelper.renderRoundedQuad(new Vec3d(5, height - 5 - diff, 0), new Vec3d(211, height - 5, 0), 4, new Color(0, 0, 0, 20));
        int centerX = 107;
        int centerY = (height - 5) / 2 + (height - 5 - diff) / 2 + 5;
        Date d = new Date();
        String line1F = line1.format(d);
        String line2F = line2.format(d);
        GL11.glScaled(2, 2, 1);
        DrawableHelper.drawCenteredString(matrices, textRenderer, line1F, centerX / 2, (centerY - 18) / 2, 0xFFFFFF);
        GL11.glScaled(.5, .5, 1);
        DrawableHelper.drawCenteredString(matrices, textRenderer, line2F, centerX, centerY + 1, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
}

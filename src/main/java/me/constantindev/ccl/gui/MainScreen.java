package me.constantindev.ccl.gui;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MainScreen extends Screen {
    Identifier bg = new Identifier("ccl", "bg.png");

    public MainScreen() {
        super(Text.of("bruh"));
    }

    @Override
    protected void init() {
        this.addButton(new ButtonWidget(width - 125, height - 175, 120, 20, Text.of("Alts"), button -> {
            this.client.openScreen(new AltManagerScreen());
        }));
        this.addButton(new ButtonWidget(width - 125, height - 150, 120, 20, Text.of("Click GUI"), button -> {
            ModuleRegistry.getByName("clickgui").onEnable();
        }));
        this.addButton(new ButtonWidget(width - 125, height - 125, 120, 20, Text.of("Settings"), (b) -> Cornos.minecraft.openScreen(new OptionsScreen(this, Cornos.minecraft.options))));
        this.addButton(new ButtonWidget(width - 125, height - 100, 120, 20, Text.of("Singleplayer"), (b) -> Cornos.minecraft.openScreen(new SelectWorldScreen(this))));
        this.addButton(new ButtonWidget(width - 125, height - 75, 120, 20, Text.of("Multiplayer"), (b) -> Cornos.minecraft.openScreen(new MultiplayerScreen(this))));
        ButtonWidget btnw = new ButtonWidget(width - 125, height - 50, 120, 20, Text.of("Realms"), (b) -> Cornos.minecraft.openScreen(new RealmsMainScreen(this)));
        this.addButton(new ButtonWidget(width - 125, height - 25, 120, 20, Text.of("Vanilla homescreen"), (b) -> {
            ModuleRegistry.getByName("ClientConfig").mconf.getByName("homescreen").setValue("vanilla");
            Cornos.minecraft.openScreen(new TitleScreen());
        }));
        this.addButton(btnw);
        this.addButton(new TexturedButtonWidget(width - 20, 0, 20, 20, 0, 0, 0, new Identifier("ccl", "transparent.png"), (b) -> btnw.setMessage(Text.of("Roleplay"))));
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Cornos.minecraft.getTextureManager().bindTexture(bg);
        DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, 0, width, height, height, width);
        RenderHelper.drawImage(matrices, new Identifier("ccl","logo.png"),-25,1,960/4,233/4);
        super.render(matrices, mouseX, mouseY, delta);
    }
}

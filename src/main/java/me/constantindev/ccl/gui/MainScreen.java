package me.constantindev.ccl.gui;

import me.constantindev.ccl.etc.config.ClientConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MainScreen extends Screen {
    public MainScreen() {
        super(Text.of("bruh"));
    }

    @Override
    protected void init() {
        AbstractButtonWidget btnw = new ButtonWidget(width / 2 + 5, height / 2 + 30, 120, 20, Text.of("Who plays realms"), (b) -> MinecraftClient.getInstance().openScreen(new RealmsMainScreen(this)));
        this.addButton(new ButtonWidget(width / 2 - 125, height / 2, 120, 20, Text.of("Singleplayer"), (b) -> MinecraftClient.getInstance().openScreen(new SelectWorldScreen(this))));
        this.addButton(new ButtonWidget(width / 2 + 5, height / 2, 120, 20, Text.of("Multiplayer"), (b) -> MinecraftClient.getInstance().openScreen(new MultiplayerScreen(this))));
        this.addButton(new ButtonWidget(width / 2 - 125, height / 2 + 30, 120, 20, Text.of("Settings"), (b) -> MinecraftClient.getInstance().openScreen(new OptionsScreen(this, MinecraftClient.getInstance().options))));
        this.addButton(btnw);
        this.addButton(new ButtonWidget(width - 121, height - 21, 120, 20, Text.of("Return to pogn't menu"), (b) -> {
            ClientConfig.blockNextMainScreenCall = false;
            MinecraftClient.getInstance().openScreen(new TitleScreen());
        }));
        this.addButton(new TexturedButtonWidget(width - 20, 0, 20, 20, 0, 0, 0, new Identifier("ccl", "transparent.png"), (b) -> btnw.setMessage(Text.of("Roleplay"))));
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("ccl", "bg.png"));
        DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, 0, width, height, height, width);
        MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("ccl", "logo.png"));
        DrawableHelper.drawTexture(matrices, width / 2 - (960 / 2 / 4), height / 6, 0, 0, 0, 960 / 4, 233 / 4, 233 / 4, 960 / 4);
        super.render(matrices, mouseX, mouseY, delta);
    }
}

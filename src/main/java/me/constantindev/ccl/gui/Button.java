package me.constantindev.ccl.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class Button extends ButtonWidget {
    public Button(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        //System.out.println(this.isHovered());
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        minecraftClient.getTextureManager().bindTexture(new Identifier("ccl", "btntexture.png"));
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = 0;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        DrawableHelper.drawTexture(matrices, this.x, this.y, 0, 0, 0, this.width, this.height, this.height, this.width);
        this.renderBg(matrices, minecraftClient, mouseX, mouseY);
        int j = this.active ? 16777215 : 10526880;
        drawCenteredText(matrices, textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }
}

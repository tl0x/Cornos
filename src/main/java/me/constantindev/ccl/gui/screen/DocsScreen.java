package me.constantindev.ccl.gui.screen;

import me.constantindev.ccl.etc.config.Colors;
import me.constantindev.ccl.etc.helper.Renderer;
import me.constantindev.ccl.features.command.Command;
import me.constantindev.ccl.features.command.CommandRegistry;
import me.constantindev.ccl.features.module.impl.external.Hud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class DocsScreen extends Screen {
    boolean isCommands;
    public DocsScreen(boolean isCommands) {
        super(Text.of("DocsScreen"));
        this.isCommands = isCommands;

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        String label = isCommands?"Command":"Module";
        String desc = "Description";
        int theight = textRenderer.fontHeight;
        int twidth = textRenderer.getWidth(label);

        fill(matrices, 0, 0, width, height, Colors.GUIBACKGROUND.get().getRGB());

        int maxWidth = twidth;
        int maxWidthDesc = twidth;

        if (isCommands) {
            int offset = 2+3+theight+3+1;
            for (Command command : CommandRegistry.getAll()) {
                int currentWidth = textRenderer.getWidth(command.displayName);
                maxWidth = Math.max(maxWidth,currentWidth);
                textRenderer.draw(matrices,command.displayName,2+3,offset,0xFFFFFF);
                offset += theight+3;
            }
            offset = 2+3+theight+3+1;
            for (Command command : CommandRegistry.getAll()) {
                int currentWidth = textRenderer.getWidth(command.description);
                maxWidthDesc = Math.max(currentWidth,maxWidthDesc);
                textRenderer.draw(matrices,command.description,2+3+maxWidth+3+4,offset,0xFFFFFF);
                offset += theight+3;
            }
            offset = 2+3+theight+3+1;
            for (Command command : CommandRegistry.getAll()) {
                Renderer.renderLineScreen(new Vec3d(2,offset+theight+1,0), new Vec3d(2+3+maxWidth+3+4+maxWidthDesc+3,offset+theight+1,0),Hud.themeColor.getColor().darker().darker(),2);
                offset += theight+3;
            }
        }
        int w = 2+3+maxWidth+3+4+maxWidthDesc+3;
        int h = CommandRegistry.getAll().size()*(theight+3)+2+3+theight+2;
        Renderer.renderLineScreen(new Vec3d(2,2,0),new Vec3d(w,2,0), Hud.themeColor.getColor(),2);
        Renderer.renderLineScreen(new Vec3d(2,2,0),new Vec3d(2,h,0), Hud.themeColor.getColor(),2);
        Renderer.renderLineScreen(new Vec3d(w,2,0),new Vec3d(w,h,0), Hud.themeColor.getColor(),2);
        Renderer.renderLineScreen(new Vec3d(2,h,0),new Vec3d(w,h,0), Hud.themeColor.getColor(),2);
        int xOffset = maxWidth+3;
        textRenderer.draw(matrices,label,2+3,2+3,0xFFFFFF);
        Renderer.renderLineScreen(new Vec3d(2+3+xOffset,2,0),new Vec3d(4+xOffset,h,0),Hud.themeColor.getColor(),2);
        textRenderer.draw(matrices,desc,2+3+xOffset+4,2+3,0xFFFFFF);
        Renderer.renderLineScreen(new Vec3d(2,2+3+theight+1,0), new Vec3d(w,2+3+theight+1,0),Hud.themeColor.getColor(),2);

        super.render(matrices, mouseX, mouseY, delta);
    }
}

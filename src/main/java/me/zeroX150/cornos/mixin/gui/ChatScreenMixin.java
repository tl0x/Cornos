package me.zeroX150.cornos.mixin.gui;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.CConf;
import me.zeroX150.cornos.etc.render.particles.ConnectingParticles;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Shadow
    protected TextFieldWidget chatField;

    @Inject(at = {@At("HEAD")}, method = "render")
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (CConf.chatScreenParticles == null) CConf.chatScreenParticles = new ConnectingParticles(100);
        CConf.chatScreenParticles.render();
        if (chatField.getText().toCharArray().length > 0) {
            if (chatField.getText().toCharArray()[0] == Cornos.config.mconf.getByName("prefix").value.toCharArray()[0]) {
                chatField.setEditableColor(new Color(CConf.latestRGBVal, true).brighter().getRGB());
            } else {
                chatField.setEditableColor(14737632);
            }
        } else {
            chatField.setEditableColor(14737632);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (CConf.chatScreenParticles != null) CConf.chatScreenParticles.tick();
    }
}

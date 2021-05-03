package me.constantindev.ccl.mixin.gui;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.CConf;
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
}

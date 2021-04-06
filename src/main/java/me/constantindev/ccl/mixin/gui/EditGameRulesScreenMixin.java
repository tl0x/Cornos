/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: EditGameRulesScreenMixin
# Created by constantin at 11:18, Mär 22 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.mixin.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.EditGameRulesScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EditGameRulesScreen.class)
public class EditGameRulesScreenMixin extends Screen {

    protected EditGameRulesScreenMixin() {
        super(Text.of(""));
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void renderBG(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.renderBackground(matrices);
    }
}

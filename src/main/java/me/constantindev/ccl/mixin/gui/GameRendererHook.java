/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: GameRendererHook
# Created by constantin at 12:16, Mär 29 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.mixin.gui;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.features.module.ModuleRegistry;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererHook {

    private boolean vb;

    @Inject(at = {@At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = Opcodes.GETFIELD, ordinal = 0)}, method = "renderWorld")
    private void onRenderWorld(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
        if (vb) {
            Cornos.minecraft.options.bobView = true;
            vb = false;
        }
        ModuleRegistry.getAll().forEach(m -> {
            if (m.isEnabled()) m.onRender(matrix, tickDelta);
        });

    }

    @Inject(at = {@At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;bobView:Z", opcode = Opcodes.GETFIELD, ordinal = 0)}, method = "renderWorld")
    private void fixTracerBobbing(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
        if (Cornos.minecraft.options.bobView) {
            vb = true;
            Cornos.minecraft.options.bobView = false;
        }
    }
}
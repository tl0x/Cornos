/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: GameRendererHook
# Created by constantin at 12:16, Mär 29 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.mixin.gui;

import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererHook {

    @Inject(at = {@At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = Opcodes.GETFIELD, ordinal = 0)}, method = "renderWorld")
    private void onRenderWorld(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
        RenderHelper.BPQueue.forEach(renderableBlock -> RenderHelper.renderBlockOutline(renderableBlock.bp, renderableBlock.dimensions, renderableBlock.r, renderableBlock.g, renderableBlock.b, renderableBlock.a));
        RenderHelper.B1B2LQueue.forEach(renderableLine -> RenderHelper.renderLine(renderableLine.bp1, renderableLine.bp2, renderableLine.c, renderableLine.width));
        RenderHelper.BPQueue.clear();
        RenderHelper.B1B2LQueue.clear();
        RenderHelper.B1S1TQueue.clear();
        ModuleRegistry.getAll().forEach(m -> {
            if (m.isOn.isOn()) m.onRender(matrix, tickDelta);
        });

    }

}
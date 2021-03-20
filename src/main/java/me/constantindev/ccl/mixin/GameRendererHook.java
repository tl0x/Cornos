package me.constantindev.ccl.mixin;

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
        RenderHelper.BPQueue.clear();
        RenderHelper.B1B2LQueue.clear();
        RenderHelper.B1S1TQueue.clear();
        ModuleRegistry.getAll().forEach(m -> {
            if (m.isOn.isOn()) m.onRender(matrix, tickDelta);
        });

    }
    @Inject(at = {@At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;bobView:Z", opcode = Opcodes.GETFIELD, ordinal = 0)}, method = "renderWorld")
    private void onPreRenderWorld(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
        ModuleRegistry.getAll().forEach(m -> {
            if (m.isOn.isOn()) m.onPreRender(matrix, tickDelta);
        });

    }
}

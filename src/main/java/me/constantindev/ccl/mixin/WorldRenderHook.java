package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRenderHook {
    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
        RenderHelper.queue.forEach(renderableBlock -> RenderHelper.renderBlockOutline(renderableBlock.bp, renderableBlock.r, renderableBlock.g, renderableBlock.b, renderableBlock.a, matrices, camera));

        if (!ModuleRegistry.getByName("blockhighlighter").isOn.isOn()) return;
        String[] coordpair = ModuleRegistry.getByName("blockhighlighter").mconf.getByName("pos").value.split(" "); // forgive me god
        if (coordpair.length != 3) {
            ModuleRegistry.getByName("blockhighlighter").mconf.getByName("pos").setValue("0 0 0");
            return;
        }
        boolean isBad = false;
        for (String s : coordpair) {
            try {
                Integer.parseInt(s);
            } catch (Exception ignored) {
                isBad = true;
            }
        }
        if (isBad) {
            ModuleRegistry.getByName("blockhighlighter").mconf.getByName("pos").setValue("0 0 0");
            ClientHelper.sendChat("Can you please provide 3 valid coordinates for BlockHighlighter?");
            return;
        }
        int x = Integer.parseInt(coordpair[0]);
        int y = Integer.parseInt(coordpair[1]);
        int z = Integer.parseInt(coordpair[2]);
        RenderHelper.renderBlockOutline(new BlockPos(x, y, z), 255, 255, 255, 255, matrices, camera);
    }
}

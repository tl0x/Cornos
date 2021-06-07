package me.zeroX150.cornos.mixin.gui;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.PlayerSkinTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlayerSkinTexture.class)
public class PlayerSkinTextureMixin {
    /**
     * @author 0x150
     * @reason da anti poof
     */
    @Overwrite
    private static void stripAlpha(NativeImage image, int x1, int y1, int x2, int y2) {
        for (int i = x1; i < x2; ++i) {
            for (int j = y1; j < y2; ++j) {
                try {
                    image.setPixelColor(i, j, image.getPixelColor(i, j) | -16777216);
                } catch (Exception ignored) {
                }
            }
        }

    }
}

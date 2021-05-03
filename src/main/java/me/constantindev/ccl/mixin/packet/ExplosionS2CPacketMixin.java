package me.constantindev.ccl.mixin.packet;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExplosionS2CPacket.class)
public class ExplosionS2CPacketMixin {
    @Shadow
    private double x;

    @Shadow
    private double y;

    @Shadow
    private double z;

    @Inject(at = @At("HEAD"), method = "apply")
    private void playSound(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo ci) {
        if (ModuleRegistry.search("memesfx").isEnabled()) {
            assert Cornos.minecraft.world != null;
            Cornos.minecraft.world.playSound(x, y, z, Cornos.VINEBOOM_SOUND, SoundCategory.BLOCKS, 4f, 1f, false);
        }
    }
}

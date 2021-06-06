package me.zeroX150.cornos.mixin;

import com.mojang.authlib.GameProfile;
import me.zeroX150.cornos.Cornos;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerListEntry.class)
public class PlayerListEntryMixin {
    @Shadow
    @Final
    private GameProfile profile;

    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    public void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
        GameProfile context = this.profile;
        boolean hasCape = false;
        String keyFound = null;
        for (String cape : Cornos.capes.keySet()) {
            if (UUID.fromString(cape).equals(context.getId())) {
                hasCape = true;
                keyFound = cape;
                break;
            }
        }
        if (hasCape) {
            cir.setReturnValue(new Identifier("ccl", "capes/" + keyFound + ".png"));
        }
    }
}

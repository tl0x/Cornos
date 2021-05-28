package me.zeroX150.cornos.mixin;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.CConf;
import me.zeroX150.cornos.etc.event.EventHelper;
import me.zeroX150.cornos.etc.event.EventType;
import me.zeroX150.cornos.etc.event.arg.Event;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.etc.manager.ConfigManager;
import me.zeroX150.cornos.etc.manager.KeybindManager;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleRegistry;
import me.zeroX150.cornos.features.module.impl.render.Freecam;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(at = @At("INVOKE"), method = "pushOutOfBlocks", cancellable = true)
    private void pushOutOfBlocks(double x, double d, CallbackInfo ci) {
        if (ModuleRegistry.search(Freecam.class).isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(at = {@At("HEAD")}, method = "isSubmergedInWater", cancellable = true)
    private void isSubmergedInWater(CallbackInfoReturnable<Boolean> cir) {
        if (ModuleRegistry.search(Freecam.class).isEnabled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (!CConf.checkedForUpdates) {
            STL.update();
            CConf.checkedForUpdates = true;
        }
        if (!ConfigManager.enabledMods) ConfigManager.enableModsToBeEnabled();
        for (Module m : ModuleRegistry.getAll()) {
            if (Cornos.minecraft.player == null) return; // so this mf will stop complaining
            try {
                if (m.isEnabled()) m.onExecuteIntern();
            } catch (Exception ignored) {
            }
        }
        KeybindManager.tick();
        Cornos.notifMan.tick();
        EventHelper.BUS.invokeEventCall(EventType.ONTICK, new Event());
    }

}
